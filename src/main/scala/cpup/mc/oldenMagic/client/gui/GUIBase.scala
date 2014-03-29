package cpup.mc.oldenMagic.client.gui

import cpup.mc.lib.client.CPupGUI
import cpup.mc.oldenMagic.{MagicMod, TMagicMod}
import net.minecraft.client.gui.GuiScreen
import net.minecraft.inventory.Container

trait GUIBase[GUI <: GuiScreen, CONT <: Container] extends CPupGUI[TMagicMod, GUI, CONT] {
	def mod = MagicMod
}