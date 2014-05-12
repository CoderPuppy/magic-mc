package cpup.mc.oldenMagic.api.oldenLanguage.runeParsing

import net.minecraft.entity.Entity
import net.minecraft.block.Block
import cpup.mc.oldenMagic.api.oldenLanguage.casting._
import cpup.mc.lib.util.pos.BlockPos
import cpup.mc.lib.targeting.{TTargetFilter, TTarget}

trait TTypeNounRune[ENT <: Entity, BLK <: Block] extends TNounRune with TTargetFilter[ENT, BLK] {
	def filter(context: CastingContext, target: TTarget): Boolean = filter(target)

	protected var _specification: TNounRune = null

	def specify(specification: TNounRune) {
		_specification = specification
	}

	override def getTargets(context: CastingContext, existing: List[TTarget]) = if(_specification == null) {
		existing.flatMap(_.ownedTargets(this))
	} else {
		_specification.getTargets(context, existing).filter(filter(context, _))
	}

	override def filter(context: CastingContext, prev: List[TNounRune], target: TTarget): Boolean = if(_specification == null) {
		filter(context, target) && (if(prev.isEmpty) { true } else {
			target.owner.exists(prev.head.filter(context, prev.tail, _))
		})
	} else {
		filter(context, target) && _specification.filter(context, prev, target)
	}
}