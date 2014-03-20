package cpup.mc.magic

import cpup.mc.lib.CPupMod
import cpw.mods.fml.common.Mod
import cpup.mc.magic.content.Content
import cpup.mc.lib.network.CPupMessage
import cpup.mc.lib.client.CPupGUIManager
import cpup.mc.magic.client.gui.GUIBase
import net.minecraft.client.gui.GuiScreen
import net.minecraft.inventory.Container
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent}
import cpw.mods.fml.common.Mod.EventHandler
import cpup.mc.magic.client.gui.writingDesk.WritingDeskGUI

trait TMagicMod extends CPupMod[TRef, CPupMessage] {
	def ref = Ref
	override def content = Content
	final val guis = new CPupGUIManager[TMagicMod, GUIBase[_ <: GuiScreen, _ <: Container]](this)
	guis.register(WritingDeskGUI)

	@EventHandler
	override def init(e: FMLInitializationEvent) {
		super.init(e)
		guis.register
	}

	@EventHandler
	override def postInit(e: FMLPostInitializationEvent) {
		super.postInit(e)
		guis.finish
	}
}

@Mod(modid = Ref.modID, modLanguage = "scala")
object MagicMod extends TMagicMod