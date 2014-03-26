package cpup.mc.magic.content

import net.minecraft.item.{EnumAction, ItemStack}
import net.minecraft.world.World
import net.minecraft.entity.player.EntityPlayer
import cpup.mc.lib.util.{VectorUtil, EntityUtil}

class ItemBend extends TItemBase {
	override def getItemUseAction(stack: ItemStack) = EnumAction.bow
	override def getMaxItemUseDuration(stack: ItemStack) = 72000

	override def onItemRightClick(stack: ItemStack, world: World, player: EntityPlayer) = {
		// TODO: check for magical energy / focus
		player.setItemInUse(stack, getMaxItemUseDuration(stack))
		stack
	}

	override def onPlayerStoppedUsing(stack: ItemStack, world: World, player: EntityPlayer, oppDur: Int) {
		val farLook = VectorUtil.getFarLook(EntityUtil.getPos(player), EntityUtil.getLook(player), 10)
		player.setPosition(farLook.xCoord, farLook.yCoord, farLook.zCoord)
	}
}