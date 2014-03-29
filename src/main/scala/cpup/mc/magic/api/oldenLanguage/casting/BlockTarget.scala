package cpup.mc.magic.api.oldenLanguage.casting

import cpup.mc.lib.util.pos.BlockPos
import cpup.mc.magic.api.oldenLanguage.runeParsing.TTypeNoun
import net.minecraft.entity.Entity
import net.minecraft.block.Block

case class BlockTarget(pos: BlockPos) extends TTarget {
	def world = pos.world
	def ownedTargets(typeNoun: TTypeNoun[_ <: Entity, _ <: Block]) = List()
}