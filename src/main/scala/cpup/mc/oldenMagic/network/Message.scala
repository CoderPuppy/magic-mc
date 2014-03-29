package cpup.mc.oldenMagic.network

import cpup.mc.lib.network.CPupMessage
import cpup.mc.oldenMagic.{MagicMod, TMagicMod}

trait Message extends CPupMessage[TMagicMod] {
	def mod = MagicMod
}