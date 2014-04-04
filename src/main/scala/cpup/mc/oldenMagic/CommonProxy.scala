package cpup.mc.oldenMagic

import cpw.mods.fml.common.FMLCommonHandler
import net.minecraftforge.common.MinecraftForge
import cpup.mc.lib.CPupCommonProxy
import net.minecraft.entity.player.EntityPlayer

class CommonProxy extends CPupCommonProxy[TOldenMagicMod] {
	def mod = OldenMagicMod
	val commonEvents = new CommonEvents

	def registerEvents {
		FMLCommonHandler.instance.bus.register(commonEvents)
		MinecraftForge.EVENT_BUS.register(commonEvents)
	}

	def activateSpellCasting(player: EntityPlayer) {}
	def stopSpellCasting(player: EntityPlayer) {}
}