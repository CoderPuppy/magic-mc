package cpup.mc.oldenMagic.content

import net.minecraft.item.{ItemStack, Item}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import cpup.mc.lib.util.ItemUtil
import cpup.lib.Util
import cpup.mc.oldenMagic.api.oldenLanguage.{TWritableItem, WritingType}

class ItemDeed extends Item with TItemBase with TWritableItem {
	def readRunes(stack: ItemStack) = Util.checkNull(ItemUtil.compound(stack).getString("name"), "").split(' ')
	def writeRunes(stack: ItemStack, runes: Seq[String]) {
		ItemUtil.compound(stack).setString("name", runes.mkString(" "))
	}
	def writingType = WritingType.Ink

	override def getItemStackLimit(stack: ItemStack) = if(stack.getTagCompound == null) { 64 } else { 1 }

	override def onItemUseFirst(stack: ItemStack, player: EntityPlayer, world: World, x: Int, y: Int, z: Int, side: Int, hitX: Float, hitY: Float, hitZ: Float) = {
		val nbt = ItemUtil.compound(stack)

		if(player.isSneaking) {
			nbt.setIntArray("second", Array(x, y, z))
		} else {
			nbt.setIntArray("first", Array(x, y, z))
		}

		true
	}

	override def addInformation(stack: ItemStack, player: EntityPlayer, list: java.util.List[_], advanced: Boolean) {
		val info = list.asInstanceOf[java.util.List[String]]
		val nbt = ItemUtil.compound(stack)

		info.add("Name: " + Util.checkNull(nbt.getString("name"), ""))

		var first = nbt.getIntArray("first")

		if(first == null || first.length < 3) {
			first = Array(0, 0, 0)
		}

		info.add("First Position: " + first(0) + ", " + first(1) + ", " + first(2))

		var second = nbt.getIntArray("second")

		if(second == null || second.length < 3) {
			second = Array(0, 0, 0)
		}

		info.add("Second Position: " + second(0) + ", " + second(1) + ", " + second(2))
	}
}