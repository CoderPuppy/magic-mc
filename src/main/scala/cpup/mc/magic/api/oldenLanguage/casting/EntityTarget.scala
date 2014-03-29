package cpup.mc.magic.api.oldenLanguage.casting

import net.minecraft.entity.Entity
import cpup.mc.magic.api.oldenLanguage.runeParsing.TTypeNoun
import net.minecraft.block.Block

case class EntityTarget(entity: Entity) extends TTarget {
	def world = entity.worldObj
	def ownedTargets(typeNoun: TTypeNoun[_ <: Entity, _ <: Block]) = List()
}