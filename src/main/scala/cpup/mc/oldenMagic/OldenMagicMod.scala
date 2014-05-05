package cpup.mc.oldenMagic

import cpup.mc.lib.CPupMod
import cpw.mods.fml.common.{SidedProxy, Mod}
import cpup.mc.oldenMagic.content.Content
import cpup.mc.lib.client.CPupGUIManager
import cpup.mc.oldenMagic.client.gui.GUIBase
import net.minecraft.client.gui.GuiScreen
import net.minecraft.inventory.Container
import cpw.mods.fml.common.event.{FMLServerStartingEvent, FMLPreInitializationEvent, FMLInitializationEvent, FMLPostInitializationEvent}
import cpw.mods.fml.common.Mod.EventHandler
import cpup.mc.oldenMagic.client.gui.writingDesk.WritingDeskGUI
import cpup.mc.oldenMagic.network.Network
import cpup.mc.oldenMagic.api.oldenLanguage.OldenLanguageRegistry
import cpup.mc.oldenMagic.content.testing._

trait TOldenMagicMod extends CPupMod[TRef] {
	def ref = Ref
	override def content = Content
	final val guis = new CPupGUIManager[TOldenMagicMod, GUIBase[_ <: GuiScreen, _ <: Container]](this)
	guis.register(WritingDeskGUI)
	def network = Network

	@SidedProxy(clientSide = "cpup.mc.oldenMagic.client.ClientProxy", serverSide = "cpup.mc.oldenMagic.CommonProxy")
	var proxy: CommonProxy = null

	@EventHandler
	override def preInit(e: FMLPreInitializationEvent) {
		super.preInit(e)
		proxy.registerEvents
	}

	@EventHandler
	override def init(e: FMLInitializationEvent) {
		super.init(e)
		guis.register
		network.register
	}

	@EventHandler
	override def postInit(e: FMLPostInitializationEvent) {
		super.postInit(e)
		guis.finish
		network.finish
		OldenLanguageRegistry.finish
	}

	@EventHandler
	override def serverStarting(e: FMLServerStartingEvent) {
		super.serverStarting(e)
		e.registerServerCommand(AntiDamageCommand)
		e.registerServerCommand(AntiSeeingCommand)
		e.registerServerCommand(CheckMagicDataCommand)
		e.registerServerCommand(ChangeMagicDataCommand)
	}
}

@Mod(modid = Ref.modID, modLanguage = "scala")
object OldenMagicMod extends TOldenMagicMod