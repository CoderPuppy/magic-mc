package cpup.mc.magic.api

import cpw.mods.fml.relauncher.{SideOnly, Side}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.IIcon

trait TRuneType {
	def runeClass: Class[_ <: TRune]

	@SideOnly(Side.CLIENT)
	def registerIcons(registerIcon: (String) => IIcon)
	def readFromNBT(nbt: NBTTagCompound): TRune
}