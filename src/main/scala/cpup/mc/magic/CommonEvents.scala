package cpup.mc.magic

import cpw.mods.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraft.item.ItemStack

class CommonEvents {
	def mod = MagicMod

	@SubscribeEvent
	def blink(e: PlayerInteractEvent) {
//		if(e.entityPlayer.inventory.getCurrentItem == null && (e.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR || e.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)) {
//			e.entityPlayer.inventory.setInventorySlotContents(e.entityPlayer.inventory.currentItem, new ItemStack(mod.content.items("bend")))
//		}
	}
}