package cpup.mc.magic.api

import net.minecraft.item.ItemStack

trait TWritableItem {
	def writeRunes(stack: ItemStack, runes: String)
	def readRunes(stack: ItemStack): String
	def writingType: WritingType
}