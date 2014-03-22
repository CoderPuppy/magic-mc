package cpup.mc.magic.api

import net.minecraft.util.IIcon
import net.minecraft.nbt.NBTTagCompound

trait TRune {
	def runeType: TRuneType
	def icons: List[IIcon]
	def writeToNBT(nbt: NBTTagCompound)
}