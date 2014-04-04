package cpup.mc.oldenMagic

import cpw.mods.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraft.item.ItemStack
import net.minecraftforge.event.entity.living.LivingHurtEvent
import cpup.mc.lib.util.WorldSavedDataUtil
import cpup.mc.oldenMagic.api.oldenLanguage.PassiveSpellsData

class CommonEvents {
	def mod = MagicMod

	@SubscribeEvent
	def blink(e: PlayerInteractEvent) {
//		if(e.entityPlayer.inventory.getCurrentItem == null && (e.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR || e.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)) {
//			e.entityPlayer.inventory.setInventorySlotContents(e.entityPlayer.inventory.currentItem, new ItemStack(mod.content.items("bend")))
//		}
	}

	@SubscribeEvent
	def passiveDamage(e: LivingHurtEvent) {
		var passiveSpellDatas = List(WorldSavedDataUtil.get(e.entity.worldObj, classOf[PassiveSpellsData], "passive(ender)"))

		val baseX = e.entity.chunkCoordX
		val baseZ = e.entity.chunkCoordZ

		for {
			offsetX <- -1 to 1
			offsetZ <- -1 to 1
		} {
			val x = baseX + offsetX
			val z = baseZ + offsetZ

			println(baseX, baseZ)
			println(offsetX, offsetZ)
			println(x, z)

			passiveSpellDatas ++= List(WorldSavedDataUtil.get(e.entity.worldObj, classOf[PassiveSpellsData], s"$x,$z"))
		}

		passiveSpellDatas = passiveSpellDatas.filter(_ != null)

		println(passiveSpellDatas)
	}
}