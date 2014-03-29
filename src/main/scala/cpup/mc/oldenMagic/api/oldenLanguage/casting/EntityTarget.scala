package cpup.mc.oldenMagic.api.oldenLanguage.casting

import net.minecraft.entity.Entity
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.TTypeNoun
import net.minecraft.block.Block

case class EntityTarget(entity: Entity) extends TTarget {
	def world = entity.worldObj
	def ownedTargets(typeNoun: TTypeNoun[_ <: Entity, _ <: Block]) = List()
}