package cpup.mc.magic.api.oldenLanguage

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.util.IIcon
import net.minecraft.nbt.NBTTagCompound

/**
 * Created by cpup on 3/22/14.
 */
trait TRuneType {
	def runeClass: Class[_ <: TRune]

	@SideOnly(Side.CLIENT)
	def registerIcons(registerIcon: (String) => IIcon)
	def readFromNBT(nbt: NBTTagCompound): TRune
}
