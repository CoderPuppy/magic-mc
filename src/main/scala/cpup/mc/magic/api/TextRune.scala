package cpup.mc.magic.api

import net.minecraft.util.IIcon
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.client.renderer.texture.IIconRegister

case class TextRune(txt: String) extends TRune {
	@SideOnly(Side.CLIENT)
	def icons = List[IIcon]()
	def runeType = TextRune
	def writeToNBT(nbt: NBTTagCompound) {
		nbt.setString("txt", txt)
	}
}

object TextRune extends TRuneType {
	@SideOnly(Side.CLIENT)
	def registerIcons(register: IIconRegister) {}

	def readFromNBT(nbt: NBTTagCompound) = TextRune(nbt.getString("txt"))
	def runeClass = classOf[TextRune]
}