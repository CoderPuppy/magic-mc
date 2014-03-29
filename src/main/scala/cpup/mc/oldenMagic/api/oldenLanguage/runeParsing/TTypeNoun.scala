package cpup.mc.oldenMagic.api.oldenLanguage.runeParsing

import net.minecraft.entity.Entity
import net.minecraft.block.Block
import cpup.mc.oldenMagic.api.oldenLanguage.casting.{BlockTarget, EntityTarget, TTarget, TCaster}
import cpup.mc.lib.util.pos.BlockPos

trait TTypeNoun[ENT <: Entity, BLK <: Block] extends TNoun {
	def entityClass: Class[ENT]
	def filterEntity(caster: TCaster, entity: ENT): Boolean

	def blockClass: Class[BLK]
	def filterBlock(caster: TCaster, pos: BlockPos): Boolean

	def filter(caster: TCaster, target: TTarget) = target match {
		case EntityTarget(entity) => entityClass.isInstance(entity) && filterEntity(caster, entity.asInstanceOf[ENT])
		case BlockTarget(pos) => entityClass.isInstance(pos.block) && filterBlock(caster, pos)
		case _ => false
	}

	protected var _specification: TNoun = null

	def specify(specification: TNoun) {
		_specification = specification
	}

	override def getTargets(caster: TCaster, existing: List[TTarget]) = if(_specification == null) {
		existing.flatMap(_.ownedTargets(this))
	} else {
		_specification.getTargets(caster, existing).filter(filter(caster, _))
	}
}