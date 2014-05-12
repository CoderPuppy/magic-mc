package cpup.mc.oldenMagic.api.oldenLanguage.runeParsing

import net.minecraft.block.Block
import cpup.mc.oldenMagic.api.oldenLanguage.casting.CastingContext
import net.minecraft.entity.Entity
import cpup.mc.lib.util.pos.BlockPos

trait NonEntityTypeNoun extends TTypeNounRune[Entity, Block] {
	override def entityClass = classOf[Entity]
	override def filterEntity(context: CastingContext, ent: Entity) = false
}