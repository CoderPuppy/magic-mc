package cpup.mc.oldenMagic.api.oldenLanguage.runes

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.util.IIcon
import net.minecraft.nbt.NBTTagCompound

trait TRuneType {
	def name: String
	def runeClass: Class[_ <: TRune]
	def readFromNBT(nbt: NBTTagCompound): TRune

	@SideOnly(Side.CLIENT)
	def registerIcons(registerIcon: (String) => IIcon)
}
