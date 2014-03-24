package cpup.mc.magic.content.runes

import cpup.mc.magic.api.oldenLanguage.runes._
import cpw.mods.fml.relauncher.{SideOnly, Side}
import net.minecraft.util.IIcon
import cpup.mc.magic.MagicMod
import net.minecraft.nbt.NBTTagCompound
import cpup.mc.magic.api.oldenLanguage.OldenLanguageRegistry

object OfRune extends SingletonRune with TNounPostPositionRune {
	def mod = MagicMod

	def name = "of-postpos"

	def createNounModifier(noun: TNounRune) = OfModifierRune(noun)

	@SideOnly(Side.CLIENT)
	var icon: IIcon = null

	@SideOnly(Side.CLIENT)
	def icons = List(icon)

	@SideOnly(Side.CLIENT)
	def registerIcons(registerIcon: (String) => IIcon) {
		icon = registerIcon(mod.ref.modID + ":runes/or.png")
	}
}

case class OfModifierRune(noun: TNounRune) extends InternalRune with TNounModifierRune {
	def runeType = OfModifierRune

	def modify(rune: TNounRune) = rune match {
		case _ => null
	}

	def writeToNBT(nbt: NBTTagCompound) {
		val nounCompound = new NBTTagCompound
		OldenLanguageRegistry.writeRuneToNBT(noun, nounCompound)
		nbt.setTag("noun", nounCompound)
	}
}

object OfModifierRune extends InternalRuneType {
	def name = "of-modifier"

	def runeClass = classOf[OfModifierRune]

	def readFromNBT(nbt: NBTTagCompound) = OfModifierRune(OldenLanguageRegistry.readRuneFromNBT(nbt.getCompoundTag("noun")).asInstanceOf[TNounRune])
}