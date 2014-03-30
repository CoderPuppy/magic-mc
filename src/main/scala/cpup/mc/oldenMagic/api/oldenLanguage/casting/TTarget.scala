package cpup.mc.oldenMagic.api.oldenLanguage.casting

import net.minecraft.world.World
import net.minecraft.entity.Entity
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.TTypeNounRune
import net.minecraft.block.Block

trait TTarget {
	def world: World
	def ownedTargets(typeNoun: TTypeNounRune[_ <: Entity, _ <: Block]): List[TTarget]
}