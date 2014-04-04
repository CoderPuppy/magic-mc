package cpup.mc.oldenMagic.network

import cpup.mc.lib.network.CPupMessage
import cpup.mc.oldenMagic.{OldenMagicMod, TOldenMagicMod}

trait Message extends CPupMessage[TOldenMagicMod] {
	def mod = OldenMagicMod
}