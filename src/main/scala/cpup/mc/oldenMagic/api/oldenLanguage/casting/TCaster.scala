package cpup.mc.oldenMagic.api.oldenLanguage.casting

import net.minecraft.util.MovingObjectPosition

trait TCaster extends TTarget {
	def mop: MovingObjectPosition
	def level: Int
	def power: Int
	def usePower(amt: Int): Boolean
}