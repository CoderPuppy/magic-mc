package cpup.mc.oldenMagic.client.gui

import cpup.mc.lib.client.CPupGUI
import cpup.mc.oldenMagic.{OldenMagicMod, TOldenMagicMod}
import net.minecraft.client.gui.GuiScreen
import net.minecraft.inventory.Container

trait GUIBase[GUI <: GuiScreen, CONT <: Container] extends CPupGUI[TOldenMagicMod, GUI, CONT] {
	def mod = OldenMagicMod
}