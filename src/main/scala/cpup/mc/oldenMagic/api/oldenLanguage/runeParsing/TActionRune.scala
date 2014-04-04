package cpup.mc.oldenMagic.api.oldenLanguage.runeParsing

import net.minecraft.entity.Entity
import cpup.mc.lib.util.pos.BlockPos
import cpup.mc.oldenMagic.api.oldenLanguage.runes.TRune
import cpup.mc.oldenMagic.api.oldenLanguage.casting.{CastingContext, TCaster}

trait TActionRune extends TRune {
	def actUponEntity(context: CastingContext, entity: Entity)
	def actUponBlock(context: CastingContext, pos: BlockPos)
}