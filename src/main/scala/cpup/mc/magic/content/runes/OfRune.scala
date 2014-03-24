package cpup.mc.magic.content.runes

import cpup.mc.magic.api.oldenLanguage.runes._
import cpw.mods.fml.relauncher.{SideOnly, Side}
import net.minecraft.util.IIcon
import cpup.mc.magic.MagicMod
import net.minecraft.nbt.NBTTagCompound
import cpup.mc.magic.api.oldenLanguage.OldenLanguageRegistry

object OfRune extends SingletonRune with TNounPreposition {
	def mod = MagicMod

	def name = "of-postpos"

	def createNounModifier(noun: TNoun) = OfModifierRune(noun)

	@SideOnly(Side.CLIENT)
	var icon: IIcon = null

	@SideOnly(Side.CLIENT)
	def icons = List(icon)

	@SideOnly(Side.CLIENT)
	def registerIcons(registerIcon: (String) => IIcon) {
		icon = registerIcon(mod.ref.modID + ":runes/or.png")
	}
}

case class OfModifierRune(noun: TNoun) extends InternalRune with TNounModifier {
	def runeType = OfModifierRune

	def modifyNoun(rune: TNoun) {
		println("of modify", rune)
	}

//	def writeToNBT(nbt: NBTTagCompound) {
//		val nounCompound = new NBTTagCompound
//		OldenLanguageRegistry.writeRuneToNBT(noun, nounCompound)
//		nbt.setTag("noun", nounCompound)
//	}
}

object OfModifierRune extends InternalRuneType {
	def name = "of-modifier"

	def runeClass = classOf[OfModifierRune]

//	def readFromNBT(nbt: NBTTagCompound) = {
//		OfModifierRune(OldenLanguageRegistry.readRuneFromNBT(nbt.getCompoundTag("noun")).asInstanceOf[TNoun])
//	}
}