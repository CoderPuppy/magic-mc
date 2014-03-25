package cpup.mc.magic.api.oldenLanguage.casters

import net.minecraft.util.MovingObjectPosition
import net.minecraft.world.World

trait TCaster {
	def world: World
	def mop: MovingObjectPosition
}