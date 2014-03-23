package cpup.mc.magic.api.oldenLanguage.runeParsing

import cpup.mc.magic.api.oldenLanguage.TRune
import net.minecraft.entity.Entity
import cpup.mc.lib.util.pos.BlockPos

trait ActionRune extends TRune {
	def actUponEntity(entity: Entity)
	def actUponBlock(pos: BlockPos)
}