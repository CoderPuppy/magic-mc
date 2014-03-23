package cpup.mc.magic.content

import net.minecraft.item.ItemStack
import net.minecraft.world.World
import net.minecraft.entity.player.EntityPlayer

class ItemWand extends TItemBase {
	override def onItemRightClick(stack: ItemStack, world: World, player: EntityPlayer) = {
		mod.proxy.activateSpellCasting(player)
		stack
	}
}