package cpup.mc.magic.api

import net.minecraft.item.ItemStack

trait TWritableItem {
	def writeRunes(stack: ItemStack, runes: Seq[String])
	def readRunes(stack: ItemStack): Seq[String]
	def writingType: WritingType
}