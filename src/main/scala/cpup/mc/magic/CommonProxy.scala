package cpup.mc.magic

import cpw.mods.fml.common.FMLCommonHandler
import net.minecraftforge.common.MinecraftForge
import cpup.mc.lib.CPupCommonProxy
import net.minecraft.item.ItemStack

class CommonProxy extends CPupCommonProxy[TMagicMod] {
	def mod = MagicMod
	val commonEvents = new CommonEvents

	def registerEvents {
		FMLCommonHandler.instance.bus.register(commonEvents)
		MinecraftForge.EVENT_BUS.register(commonEvents)
	}

	def activateSpellCasting(stack: ItemStack) {}
	def stopSpellCasting {}
}