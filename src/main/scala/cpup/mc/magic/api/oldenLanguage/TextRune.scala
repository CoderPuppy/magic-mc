package cpup.mc.magic.api.oldenLanguage

import net.minecraft.util.IIcon
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.nbt.NBTTagCompound
import cpup.mc.magic.MagicMod

case class TextRune(txt: String) extends TRune {
	@SideOnly(Side.CLIENT)
	def icons = List(TextRune.icon)

	def runeType = TextRune
	def writeToNBT(nbt: NBTTagCompound) {
		nbt.setString("txt", txt)
	}
}

object TextRune extends TRuneType {
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