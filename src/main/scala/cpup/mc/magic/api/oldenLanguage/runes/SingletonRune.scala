package cpup.mc.magic.api.oldenLanguage.runes

import net.minecraft.nbt.NBTTagCompound
import cpup.mc.magic.api.oldenLanguage.textParsing.{TextRune, TContext, TTransform}

trait SingletonRune extends TRune with TRuneType with TTransform {
	def runeType = this
	def runeClass = getClass

//	def writeToNBT(nbt: NBTTagCompound){}
//	def readFromNBT(nbt: NBTTagCompound) = this

	def transform(context: TContext, rune: TextRune) = this
}