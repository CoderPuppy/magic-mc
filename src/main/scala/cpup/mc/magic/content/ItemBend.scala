package cpup.mc.magic.content

import net.minecraft.item.{EnumAction, ItemStack}
import net.minecraft.world.World
import net.minecraft.entity.player.EntityPlayer
import cpup.mc.lib.util.{Direction, VectorUtil, EntityUtil}
import net.minecraft.util.MathHelper

class ItemBend extends TItemBase {
	override def getItemUseAction(stack: ItemStack) = EnumAction.bow
	override def getMaxItemUseDuration(stack: ItemStack) = 72000

	override def onItemRightClick(stack: ItemStack, world: World, player: EntityPlayer) = {
		// TODO: check for magical energy / focus
		player.setItemInUse(stack, getMaxItemUseDuration(stack))
		stack
	}

	override def onEaten(stack: ItemStack, world: World, player: EntityPlayer) = {
		onPlayerStoppedUsing(stack, world, player, 0)
		stack
	}

	def getFarLook(stack: ItemStack, player: EntityPlayer, oppDur: Int) = {
		val dur = getMaxItemUseDuration(stack) - oppDur

		val farLook = VectorUtil.getFarLook(
			EntityUtil.getPos(player).addVector(-0.5, -0.5, -0.5),
			EntityUtil.getLook(player),
			Math.pow(2, dur) + 2
		)

		farLook
	}

	override def onPlayerStoppedUsing(stack: ItemStack, world: World, player: EntityPlayer, oppDur: Int) {
		val farLook = getFarLook(stack, player, oppDur)

		if(player.ridingEntity != null) {
			player.mountEntity(null)
		}
		player.setPositionAndUpdate(farLook.xCoord, farLook.yCoord, farLook.zCoord)
//		player.inventory.setInventorySlotContents(player.inventory.currentItem, null)
	}
}