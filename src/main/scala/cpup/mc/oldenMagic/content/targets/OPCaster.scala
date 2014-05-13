package cpup.mc.oldenMagic.content.targets

import cpup.mc.lib.targeting.PlayerTarget

class OPCaster(player: PlayerTarget) extends PlayerCaster(player) {
	override def naturalPower = Int.MaxValue
	override def maxSafePower = Int.MaxValue
	override def power = Int.MaxValue
	override def usePower(amt: Int) = amt
}