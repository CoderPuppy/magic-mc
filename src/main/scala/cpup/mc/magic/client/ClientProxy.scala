package cpup.mc.magic.client

import cpup.mc.magic.CommonProxy
import cpw.mods.fml.common.FMLCommonHandler
import net.minecraftforge.common.MinecraftForge
import cpup.mc.magic.client.runeSelection.{RootCategory, Category}
import net.minecraft.item.ItemStack
import net.minecraft.entity.player.EntityPlayer

class ClientProxy extends CommonProxy {
	val clientEvents = new ClientEvents(this)

	var category: Category = null
	var castingItem: ItemStack = null
	var spell: List[String] = null
	override def activateSpellCasting(player: EntityPlayer, stack: ItemStack) {
		super.activateSpellCasting(player, stack)
		category = RootCategory.create
		castingItem = stack
		spell = List[String]()
	}
	override def stopSpellCasting(player: EntityPlayer) {
		super.stopSpellCasting(player)
		category = null
		castingItem = null
		spell = null
	}

	override def registerEvents {
		super.registerEvents
		FMLCommonHandler.instance.bus.register(clientEvents)
		MinecraftForge.EVENT_BUS.register(clientEvents)
	}
}