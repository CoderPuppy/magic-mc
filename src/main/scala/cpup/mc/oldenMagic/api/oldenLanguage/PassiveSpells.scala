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

	protected val entityToSpell = HashMultimap.create[Int, Spell]
	protected val actionToSpell = HashMultimap.create[TRuneType, (Int, Spell)]
	protected var _spells = new HashSet[(Int, Spell)]

	def spells = _spells
	def entitySpells(ent: Int): Array[Spell] = entityToSpell.get(ent).toArray.asInstanceOf[Array[Spell]]
	def entitySpells(ent: Entity): Array[Spell] = entitySpells(ent.getEntityId)
	def actionSpells(action: TRuneType) = actionToSpell.get(action).toArray

	def readFromNBT(nbt: NBTTagCompound) {
		val spellsNBT = nbt.getTagList("spells", Constants.NBT.TAG_COMPOUND)

		for(i <- 0 until spellsNBT.tagCount) {
			try {
				val spellNBT = spellsNBT.getCompoundTagAt(i)
				val spell = (spellNBT.getInteger("entity"), Spell.readFromNBT(spellNBT.getCompoundTag("spell")))
				_spells += spell
				entityToSpell.put(spell._1, spell._2)
				actionToSpell.put(spell._2.action.runeType, spell)
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