package cpup.mc.oldenMagic.api.oldenLanguage

import net.minecraft.world.{World, WorldSavedData}
import net.minecraft.nbt.{NBTTagList, NBTTagCompound}
import cpup.mc.lib.util.WorldSavedDataUtil
import com.google.common.collect.HashMultimap
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.Spell
import net.minecraftforge.common.util.Constants
import cpup.mc.oldenMagic.api.oldenLanguage.runes.TRuneType
import scala.collection.immutable.HashSet
import cpup.mc.oldenMagic.OldenMagicMod
import cpup.mc.oldenMagic.api.oldenLanguage.casting.{TAction, CastingContext, TCaster}

object PassiveSpells {
	def get(world: World) = {
		WorldSavedDataUtil.get(world, classOf[PassiveSpellsData], "passive(ender)")
	}
	def getOrCreate(world: World) = {
		WorldSavedDataUtil.getOrCreate(world, classOf[PassiveSpellsData], "passive(ender)")
	}

	def get(world: World, cx: Int, cz: Int) = {
		WorldSavedDataUtil.get(world, classOf[PassiveSpellsData], s"$cx,$cz")
	}
	def getOrCreate(world: World, cx: Int, cz: Int) = {
		WorldSavedDataUtil.getOrCreate(world, classOf[PassiveSpellsData], s"$cx,$cz")
	}
}

class PassiveSpellsContext(player: String, caster: TCaster, val trigger: Spell, val action: TAction) extends CastingContext(player, caster)
object PassiveSpellsContext {
	def unapply(ctx: PassiveSpellsContext) = Some((ctx.player, ctx.caster, ctx.trigger, ctx.action))
}

class PassiveSpellsData(name: String) extends WorldSavedData(name) {
	def mod = OldenMagicMod

	protected val casterToSpell = HashMultimap.create[TCaster, (String, TCaster, Spell, Spell)]
	protected val actionToSpell = HashMultimap.create[TRuneType, (String, TCaster, Spell, Spell)]
	protected var _spells = new HashSet[(String, TCaster, Spell, Spell)]

	def spells = _spells
	def casterSpells(caster: TCaster) = casterToSpell.get(caster).toArray.asInstanceOf[Array[Spell]]
	def actionSpells(action: TRuneType) = actionToSpell.get(action).toArray

	def registerSpell(master: String, minion: TCaster, trigger: Spell, response: Spell) {
		val spell = (master, minion, trigger, response)
		_spells += spell
		casterToSpell.put(minion, spell)
		actionToSpell.put(trigger.action.runeType, spell)
		markDirty
	}

	def readFromNBT(nbt: NBTTagCompound) {
		val spellsNBT = nbt.getTagList("spells", Constants.NBT.TAG_COMPOUND)

		for(i <- 0 until spellsNBT.tagCount) {
			try {
				val spellNBT = spellsNBT.getCompoundTagAt(i)
				registerSpell(
					master   = spellNBT.getString("player"),
					minion   = OldenLanguageRegistry.readTargetFromNBT(spellNBT.getCompoundTag("entity")).asInstanceOf[TCaster],
					trigger  = Spell.readFromNBT(spellNBT.getCompoundTag("trigger")),
					response = Spell.readFromNBT(spellNBT.getCompoundTag("response"))
				)
			} catch {
				case e: Exception =>
					mod.logger.error(e.toString)
					mod.logger.error(e.getStackTraceString)
			}
		}
	}

	def writeToNBT(nbt: NBTTagCompound) {
		return // TODO: just for debug

		val spellsNBT = new NBTTagList

		for(spell <- _spells) {
			try {
				val spellNBT = new NBTTagCompound
				spell._2.writeToNBT(spellNBT)
				spellsNBT.appendTag(spellNBT)
			} catch {
				case e: Exception =>
					mod.logger.error(e.toString)
					mod.logger.error(e.getStackTraceString)
			}
		}

		nbt.setTag("spells", spellsNBT)
	}
}