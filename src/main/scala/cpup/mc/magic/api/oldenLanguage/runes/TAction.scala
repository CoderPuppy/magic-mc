package cpup.mc.magic.api.oldenLanguage.runes

import net.minecraft.entity.Entity
import cpup.mc.lib.util.pos.BlockPos

trait TAction extends TRune {
	def actUponEntity(entity: Entity)
	def actUponBlock(pos: BlockPos)
}