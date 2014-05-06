package cpup.mc.oldenMagic.content

import net.minecraft.item.{EnumAction, ItemStack}
import net.minecraft.world.World
import net.minecraft.entity.player.EntityPlayer
import cpup.mc.lib.util.{VectorUtil, EntityUtil}

class ItemBend extends TItemBase {
	val stepBackDist = mod.config.get(
		"bend",
		"stepBackDist",
		0.5,
		"How many blocks bend should go back by each step. Higher = more lag, more accurate. Lower = less lag, less accurate. Setting this higher than 2 would probably make bend derp."
	)

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

		val pos = EntityUtil.getPos(player).addVector(-0.5, -0.5, -0.5)
		val look = EntityUtil.getLook(player)
		val oppLook = VectorUtil.negate(look)
		var dest = VectorUtil.offset(pos, look, dur * 1.5 + 1)

		while(EntityUtil.wouldSuffocate(player, dest.xCoord, dest.yCoord, dest.zCoord)) {
			dest = VectorUtil.offset(dest, oppLook, stepBackDist)
		}

		dest
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