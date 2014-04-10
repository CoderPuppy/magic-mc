package cpup.mc.oldenMagic.api.oldenLanguage.runeParsing

import net.minecraft.entity.Entity
import net.minecraft.block.Block
import cpup.mc.oldenMagic.api.oldenLanguage.casting._
import cpup.mc.lib.util.pos.BlockPos
import cpup.mc.lib.util.pos.BlockPos

trait TTypeNounRune[ENT <: Entity, BLK <: Block] extends TNounRune {
	def entityClass: Class[ENT]
	def filterEntity(context: CastingContext, entity: ENT): Boolean

	def blockClass: Class[BLK]
	def filterBlock(context: CastingContext, pos: BlockPos): Boolean

	def filter(context: CastingContext, target: TTarget) = target.obj match {
		case Left(entity) => entityClass.isInstance(entity) && filterEntity(context, entity.asInstanceOf[ENT])
		case Right(pos) => blockClass.isInstance(pos.block) && filterBlock(context, pos)
		case _ => false
	}

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
		filter(context, target) && prev.head.filter(context, prev.tail, target.owner)
	} else {
		_specification.filter(context, prev, target)
	}
}