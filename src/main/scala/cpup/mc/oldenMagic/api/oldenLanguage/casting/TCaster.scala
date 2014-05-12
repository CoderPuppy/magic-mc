package cpup.mc.oldenMagic.api.oldenLanguage.casting

import cpup.mc.lib.targeting.TTarget

trait TCaster extends TTarget {
	def naturalPower: Int
	def maxSafePower: Int
	def power: Int
	def usePower(amt: Int): Int
}