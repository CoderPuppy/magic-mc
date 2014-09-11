package cpup.mc.oldenMagic.content

import cpup.mc.lib.content.CPupTE
import cpup.mc.oldenMagic.{OldenMagicMod, TOldenMagicMod}

trait BaseTE extends CPupTE[TOldenMagicMod] {
	def mod = OldenMagicMod
}