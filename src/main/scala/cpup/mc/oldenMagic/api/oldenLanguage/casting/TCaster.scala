package cpup.mc.oldenMagic.api.oldenLanguage.casting

import net.minecraft.util.MovingObjectPosition
import cpup.mc.lib.targeting.TTarget

trait TCaster extends TTarget {
	def mop: Option[MovingObjectPosition]
	def naturalPower: Int
	def maxSafePower: Int
	def power: Int
	def usePower(amt: Int): Int
}