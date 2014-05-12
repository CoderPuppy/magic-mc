package cpup.mc.oldenMagic.api.oldenLanguage.runes

import cpup.mc.oldenMagic.api.oldenLanguage.textParsing.{TextRune, TContext, TTransform}
import net.minecraft.nbt.NBTTagCompound

trait SingletonRune extends TRune with TRuneType with TTransform {
	def runeType = this
	def runeClass = getClass

	override def transform(context: TContext, content: String) = this

	def writeToNBT(nbt: NBTTagCompound) {}
	def readFromNBT(nbt: NBTTagCompound) = this
}