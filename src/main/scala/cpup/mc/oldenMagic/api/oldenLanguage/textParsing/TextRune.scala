package cpup.mc.oldenMagic.api.oldenLanguage.textParsing

import net.minecraft.util.IIcon
import cpw.mods.fml.relauncher.{Side, SideOnly}
import cpup.mc.oldenMagic.OldenMagicMod
import cpup.mc.oldenMagic.api.oldenLanguage.runes.{TRune, TRuneType}
import net.minecraft.nbt.NBTTagCompound

case class TextRune(text: String) extends TRune {
	def runeType = TextRune
	def writeToNBT(nbt: NBTTagCompound) {
		nbt.setString("text", text)
	}

	@SideOnly(Side.CLIENT)
	def icons = List(TextRune.icon)
}

object TextRune extends TRuneType {
	def mod = OldenMagicMod

	def name = s"${mod.ref.modID}:text"
	def runeClass = classOf[TextRune]
	def readFromNBT(nbt: NBTTagCompound) = TextRune(nbt.getString("text"))

	@SideOnly(Side.CLIENT)
	var icon: IIcon = null

	@SideOnly(Side.CLIENT)
	def registerIcons(registerIcon: (String) => IIcon) {
		icon = registerIcon(mod.ref.modID + ":runes/text")
	}
}