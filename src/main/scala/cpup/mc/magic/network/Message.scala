package cpup.mc.magic.network

import cpup.mc.lib.network.CPupMessage
import cpup.mc.magic.{MagicMod, TMagicMod}

trait Message extends CPupMessage[TMagicMod] {
	def mod = MagicMod
}