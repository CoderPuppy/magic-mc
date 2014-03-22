package cpup.mc.magic.api

import cpw.mods.fml.relauncher.{SideOnly, Side}
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.nbt.NBTTagCompound

trait TRuneType {
	def runeClass: Class[_ <: TRune]

	@SideOnly(Side.CLIENT)
	def registerIcons(register: IIconRegister)
	def readFromNBT(nbt: NBTTagCompound): TRune
}