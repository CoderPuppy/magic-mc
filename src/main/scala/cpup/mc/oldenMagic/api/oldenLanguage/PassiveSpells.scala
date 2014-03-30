package cpup.mc.oldenMagic.api.oldenLanguage

import net.minecraft.world.{World, WorldSavedData}
import net.minecraft.nbt.NBTTagCompound
import cpup.mc.lib.util.WorldSavedDataUtil
import com.google.common.collect.HashMultimap
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.Spell
import scala.collection.mutable.ListBuffer
import net.minecraftforge.common.util.Constants

object PassiveSpells {
	def apply(world: World) = {
		WorldSavedDataUtil.get(world, classOf[PassiveSpellsData], "passive(ender)")
	}
	def apply(world: World, cx: Int, cz: Int) = {
		WorldSavedDataUtil.get(world, classOf[PassiveSpellsData], s"$cx,$cz")
	}
}

class PassiveSpellsData(name: String) extends WorldSavedData(name) {
	protected val entityToSpell = HashMultimap.create[Int, Spell]
	protected val spells = new ListBuffer[(Int, Spell)]

	def readFromNBT(nbt: NBTTagCompound) {
		val spellsNBT = nbt.getTagList("spells", Constants.NBT.TAG_COMPOUND)

		for(i <- 0 until spellsNBT.tagCount) {
			val spellNBT = spellsNBT.getCompoundTagAt(i)
			val spell = (spellNBT.getInteger("entity"), Spell.readFromNBT(spellNBT.getCompoundTag("spell")))
		}
	}

	def writeToNBT(nbt: NBTTagCompound) {

	}
}