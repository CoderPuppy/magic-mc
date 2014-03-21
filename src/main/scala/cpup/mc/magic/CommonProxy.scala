package cpup.mc.magic

import cpw.mods.fml.common.FMLCommonHandler
import net.minecraftforge.common.MinecraftForge
import cpup.mc.lib.CPupCommonProxy
import net.minecraft.item.ItemStack
import net.minecraft.entity.player.EntityPlayer

class CommonProxy extends CPupCommonProxy[TMagicMod] {
	def mod = MagicMod
	val commonEvents = new CommonEvents

	def registerEvents {
		FMLCommonHandler.instance.bus.register(commonEvents)
		MinecraftForge.EVENT_BUS.register(commonEvents)
	}

	def activateSpellCasting(player: EntityPlayer, stack: ItemStack) {}
	def stopSpellCasting(player: EntityPlayer) {}
}