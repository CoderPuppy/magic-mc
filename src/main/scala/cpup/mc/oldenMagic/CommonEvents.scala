package cpup.mc.oldenMagic

import cpw.mods.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraft.item.ItemStack
import net.minecraftforge.event.entity.living.LivingHurtEvent
import cpup.mc.lib.util.WorldSavedDataUtil
import cpup.mc.oldenMagic.api.oldenLanguage.{PassiveSpellsContext, PassiveSpells, PassiveSpellsData}
import cpup.mc.oldenMagic.content.runes.DamageAction

class CommonEvents {
	def mod = OldenMagicMod

	@SubscribeEvent
	def blink(e: PlayerInteractEvent) {
//		if(e.entityPlayer.inventory.getCurrentItem == null && (e.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR || e.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)) {
//			e.entityPlayer.inventory.setInventorySlotContents(e.entityPlayer.inventory.currentItem, new ItemStack(mod.content.items("bend")))
//		}
	}

	// TODO: LivingAttackEvent

	@SubscribeEvent
	def passiveDamage(e: LivingHurtEvent) {
		var passiveSpellDatas = List(PassiveSpells.get(e.entity.worldObj))

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

			passiveSpellDatas ++= List(PassiveSpells.get(e.entity.worldObj, x, z))
		}

		passiveSpellDatas = passiveSpellDatas.filter(_ != null)

		val action = new DamageAction(e)
		val spells = passiveSpellDatas
			.flatMap(_.actionSpells(action.runeType))
			.map((spell) => {
				(spell, new PassiveSpellsContext(spell._1, spell._2, spell._3, action))
			})
			.filter((spell) => {
				val prev = spell._1._3.targetPath.reverse
				prev.head.filter(spell._2, prev.tail, action.affectedTarget)
			})

		println(passiveSpellDatas, spells)

		for(spell <- spells) {
			spell._2.cast(spell._1._4)
		}
	}
}