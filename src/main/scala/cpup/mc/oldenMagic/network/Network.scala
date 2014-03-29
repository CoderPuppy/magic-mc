package cpup.mc.oldenMagic.network

import cpup.mc.lib.network.CPupNetwork
import cpup.mc.oldenMagic.{MagicMod, TMagicMod}
import cpup.mc.oldenMagic.content.WritingDeskMessage
import io.netty.channel.ChannelHandler

@ChannelHandler.Sharable
object Network extends CPupNetwork[TMagicMod] {
	def mod = MagicMod

	register(classOf[WritingDeskMessage])
}