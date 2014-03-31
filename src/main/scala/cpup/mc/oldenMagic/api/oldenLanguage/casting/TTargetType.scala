package cpup.mc.oldenMagic.api.oldenLanguage.casting

import net.minecraft.nbt.NBTTagCompound

trait TTargetType {
	def name: String
	def targetClass: Class[_ <: TTarget]
	def readFromNBT(nbt: NBTTagCompound): TTarget
}