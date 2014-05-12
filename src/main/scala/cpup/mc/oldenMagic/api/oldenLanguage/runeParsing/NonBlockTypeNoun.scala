package cpup.mc.oldenMagic.api.oldenLanguage.runeParsing

import net.minecraft.block.Block
import cpup.mc.oldenMagic.api.oldenLanguage.casting.CastingContext
import net.minecraft.entity.Entity
import cpup.mc.lib.util.pos.BlockPos

trait NonBlockTypeNoun extends TTypeNounRune[Entity, Block] {
	override def blockClass = classOf[Block]
	override def filterBlock(pos: BlockPos) = false
}