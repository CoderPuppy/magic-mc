package cpup.mc.magic.network

import cpup.mc.lib.network.CPupNetwork
import cpup.mc.magic.{MagicMod, TMagicMod}
import cpup.mc.magic.content.WritingDeskMessage
import io.netty.channel.ChannelHandler

@ChannelHandler.Sharable
object Network extends CPupNetwork[TMagicMod] {
	def mod = MagicMod

	register(classOf[WritingDeskMessage])
}