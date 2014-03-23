package cpup.mc.magic.api.oldenLanguage.runes

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.util.IIcon
import net.minecraft.nbt.NBTTagCompound

trait TRuneType {
	def runeClass: Class[_ <: TRune]

	@SideOnly(Side.CLIENT)
	def registerIcons(registerIcon: (String) => IIcon)
	def readFromNBT(nbt: NBTTagCompound): TRune
}
