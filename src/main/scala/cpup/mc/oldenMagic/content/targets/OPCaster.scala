package cpup.mc.oldenMagic.content.targets

class OPCaster(player: String) extends PlayerCaster(player) {
	override def naturalPower = Int.MaxValue
	override def maxSafePower = Int.MaxValue
	override def power = Int.MaxValue
	override def usePower(amt: Int) = amt
}