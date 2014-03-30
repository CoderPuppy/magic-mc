package cpup.mc.oldenMagic.api.oldenLanguage.runeParsing

import net.minecraft.entity.Entity
import cpup.mc.lib.util.pos.BlockPos
import cpup.mc.oldenMagic.api.oldenLanguage.runes.TRune

trait TActionRune extends TRune {
	def actUponEntity(entity: Entity)
	def actUponBlock(pos: BlockPos)
}