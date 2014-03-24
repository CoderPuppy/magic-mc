package cpup.mc.magic.api.oldenLanguage.runes

import net.minecraft.util.IIcon
import net.minecraft.nbt.NBTTagCompound
import cpup.mc.lib.util.GUIUtil

trait TRune {
	def runeType: TRuneType
	def icons: List[IIcon]
//	def writeToNBT(nbt: NBTTagCompound)

	def render(x: Int, y: Int, width: Int, height: Int) {
		for((icon, iconIndex) <- icons.zipWithIndex) {
			GUIUtil.drawItemIconAt(icon, x, y, iconIndex, 32, 32)
		}
	}
}
