package cpup.mc.magic.api.oldenLanguage

import net.minecraft.util.IIcon
import net.minecraft.nbt.NBTTagCompound

/**
 * Created by cpup on 3/22/14.
 */
trait TRune {
	def runeType: TRuneType
	def icons: List[IIcon]
	def writeToNBT(nbt: NBTTagCompound)
}
