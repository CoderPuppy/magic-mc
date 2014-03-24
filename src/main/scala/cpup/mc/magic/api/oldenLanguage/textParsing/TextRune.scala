package cpup.mc.magic.api.oldenLanguage.textParsing

import net.minecraft.util.IIcon
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.nbt.NBTTagCompound
import cpup.mc.magic.MagicMod
import cpup.mc.magic.api.oldenLanguage.runes.{TRune, TRuneType}

case class TextRune(text: String) extends TRune {
	@SideOnly(Side.CLIENT)
	def icons = List(TextRune.icon)

	def runeType = TextRune
	def writeToNBT(nbt: NBTTagCompound) {
		nbt.setString("txt", text)
	}
}

object TextRune extends TRuneType {
	def name = "text"

	def mod = MagicMod

	@SideOnly(Side.CLIENT)
	var icon: IIcon = null

	@SideOnly(Side.CLIENT)
	def registerIcons(registerIcon: (String) => IIcon) {
		icon = registerIcon(mod.ref.modID + ":runes/text")
	}

	def readFromNBT(nbt: NBTTagCompound) = TextRune(nbt.getString("txt"))
	def runeClass = classOf[TextRune]
}