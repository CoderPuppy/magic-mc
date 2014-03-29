package cpup.mc.magic.api.oldenLanguage.runeParsing

import net.minecraft.entity.Entity
import cpup.mc.lib.util.pos.BlockPos
import cpup.mc.magic.api.oldenLanguage.runes.TRune

trait TAction extends TRune {
	def actUponEntity(entity: Entity)
	def actUponBlock(pos: BlockPos)
}