package cpup.mc.oldenMagic.api.oldenLanguage.runeParsing

import net.minecraft.entity.Entity
import cpup.mc.lib.util.pos.BlockPos
import cpup.mc.oldenMagic.api.oldenLanguage.runes.TRune
import cpup.mc.oldenMagic.api.oldenLanguage.casting.{TTarget, CastingContext, TCaster}

trait TVerbRune extends TRune {
	def act(context: CastingContext, targets: List[TTarget]) {
		targets.foreach(act(context, _))
	}
	def act(context: CastingContext, target: TTarget) {
		target.obj match {
			case Left(entity) =>
				act(context, entity)

			case Right(pos) =>
				act(context, pos)

			case _ =>
		}
	}
	def act(context: CastingContext, entity: Entity)
	def act(context: CastingContext, pos: BlockPos)
}