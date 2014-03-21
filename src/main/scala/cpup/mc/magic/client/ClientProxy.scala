package cpup.mc.magic.client

import cpup.mc.magic.CommonProxy
import cpw.mods.fml.common.FMLCommonHandler
import net.minecraftforge.common.MinecraftForge
import cpup.mc.magic.client.runeSelection.{RootCategory, Category}
import net.minecraft.item.ItemStack

class ClientProxy extends CommonProxy {
	val clientEvents = new ClientEvents(this)

	var category: Category = null
	var castingItem: ItemStack = null
	override def activateSpellCasting(stack: ItemStack) {
		category = RootCategory.create
		castingItem = stack
	}
	override def stopSpellCasting {
		category = null
		castingItem = null
	}

	override def registerEvents {
		super.registerEvents
		FMLCommonHandler.instance.bus.register(clientEvents)
		MinecraftForge.EVENT_BUS.register(clientEvents)
	}
}