package cpup.mc.oldenMagic.client

import cpup.mc.oldenMagic.CommonProxy
import cpw.mods.fml.common.FMLCommonHandler
import net.minecraftforge.common.MinecraftForge
import cpup.mc.oldenMagic.client.runeSelection.{RuneSelector, RuneOption}
import net.minecraft.entity.player.EntityPlayer

class ClientProxy extends CommonProxy {
	val clientEvents = new ClientEvents(this)

	var selector: RuneSelector = null
	var castingItem: Int = -1
	var spell: List[RuneOption] = null
	override def activateSpellCasting(player: EntityPlayer) {
		super.activateSpellCasting(player)
		selector = new RuneSelector(player, 20, 20, (runeOpt: RuneOption) => {
			spell ++= List(runeOpt)
		})
		castingItem = player.inventory.currentItem
		spell = List[RuneOption]()
	}
	override def stopSpellCasting(player: EntityPlayer) {
		super.stopSpellCasting(player)
		selector = null
		spell = null
		castingItem = -1
	}

	override def registerEvents {
		super.registerEvents
		FMLCommonHandler.instance.bus.register(clientEvents)
		MinecraftForge.EVENT_BUS.register(clientEvents)
	}
}