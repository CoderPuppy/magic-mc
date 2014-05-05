package cpup.mc.oldenMagic.api.oldenLanguage.casting

import net.minecraft.util.MovingObjectPosition

trait TCaster extends TTarget {
	def mop: MovingObjectPosition
	def naturalPower: Int
	def maxSafePower: Int
	def power: Int
	def usePower(amt: Int): Int
}