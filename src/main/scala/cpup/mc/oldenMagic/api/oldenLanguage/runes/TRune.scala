package cpup.mc.oldenMagic.api.oldenLanguage.runes

import net.minecraft.util.IIcon
import cpup.mc.lib.util.GUIUtil
import net.minecraft.nbt.NBTTagCompound
import cpw.mods.fml.relauncher.{Side, SideOnly}

trait TRune {
	def runeType: TRuneType
	override def toString = runeType.name

	@SideOnly(Side.CLIENT)
	def icons: List[IIcon]

	def render(x: Int, y: Int, width: Int, height: Int) {
		for((icon, iconIndex) <- icons.zipWithIndex) {
			GUIUtil.drawItemIconAt(icon, x, y, iconIndex, 32, 32)
		}
	}

	def writeToNBT(nbt: NBTTagCompound)
}
