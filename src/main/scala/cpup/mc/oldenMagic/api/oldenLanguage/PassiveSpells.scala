package cpup.mc.oldenMagic.api.oldenLanguage

import net.minecraft.world.{World, WorldSavedData}
import net.minecraft.nbt.{NBTTagList, NBTTagCompound}
import cpup.mc.lib.util.WorldSavedDataUtil
import com.google.common.collect.HashMultimap
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.Spell
import scala.collection.mutable.ListBuffer
import net.minecraftforge.common.util.Constants
import cpup.mc.oldenMagic.api.oldenLanguage.runes.TRuneType
import scala.collection.immutable.HashSet
import scala.collection.JavaConversions
import net.minecraft.entity.Entity
import cpup.mc.oldenMagic.MagicMod
import cpup.mc.oldenMagic.api.oldenLanguage.casting.{PlayerCaster, TCaster}

object PassiveSpells {
	def apply(world: World) = {
		WorldSavedDataUtil.get(world, classOf[PassiveSpellsData], "passive(ender)")
	}
	def apply(world: World, cx: Int, cz: Int) = {
		WorldSavedDataUtil.get(world, classOf[PassiveSpellsData], s"$cx,$cz")
	}
}

class PassiveSpellsData(name: String) extends WorldSavedData(name) {
	def mod = MagicMod

	protected val casterToSpell = HashMultimap.create[TCaster, (TCaster, TCaster, Spell)]
	protected val actionToSpell = HashMultimap.create[TRuneType, (TCaster, TCaster, Spell)]
	protected var _spells = new HashSet[(TCaster, TCaster, Spell)]

	def spells = _spells
	def casterSpells(caster: TCaster) = casterToSpell.get(caster).toArray.asInstanceOf[Array[Spell]]
	def actionSpells(action: TRuneType) = actionToSpell.get(action).toArray

	def readFromNBT(nbt: NBTTagCompound) {
		val spellsNBT = nbt.getTagList("spells", Constants.NBT.TAG_COMPOUND)

		for(i <- 0 until spellsNBT.tagCount) {
			try {
				val spellNBT = spellsNBT.getCompoundTagAt(i)
				val spell = (
					PlayerCaster(spellNBT.getString("player")),
					OldenLanguageRegistry.readTargetFromNBT(spellNBT.getCompoundTag("entity")).asInstanceOf[TCaster],
					Spell.readFromNBT(spellNBT.getCompoundTag("spell"))
				)
				_spells += spell
				casterToSpell.put(spell._1, spell)
				actionToSpell.put(spell._3.action.runeType, spell)
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