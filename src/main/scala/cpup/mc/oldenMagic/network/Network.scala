package cpup.mc.oldenMagic.network

import cpup.mc.lib.network.CPupNetwork
import cpup.mc.oldenMagic.{OldenMagicMod, TOldenMagicMod}
import cpup.mc.oldenMagic.content.WritingDeskMessage
import io.netty.channel.ChannelHandler

@ChannelHandler.Sharable
object Network extends CPupNetwork[TOldenMagicMod] {
	def mod = OldenMagicMod

	register(classOf[WritingDeskMessage])
}