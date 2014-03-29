package cpup.mc.magic.api.oldenLanguage.casting

import net.minecraft.world.World
import net.minecraft.entity.Entity
import cpup.mc.magic.api.oldenLanguage.runeParsing.TTypeNoun
import net.minecraft.block.Block

trait TTarget {
	def world: World
	def ownedTargets(typeNoun: TTypeNoun[_ <: Entity, _ <: Block]): List[TTarget]
}