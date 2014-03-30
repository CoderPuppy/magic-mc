package cpup.mc.oldenMagic.api.oldenLanguage.casting

import cpup.mc.lib.util.pos.BlockPos
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.TTypeNounRune
import net.minecraft.entity.Entity
import net.minecraft.block.Block

case class BlockTarget(pos: BlockPos) extends TTarget {
	def world = pos.world
	def ownedTargets(typeNoun: TTypeNounRune[_ <: Entity, _ <: Block]) = List()
}