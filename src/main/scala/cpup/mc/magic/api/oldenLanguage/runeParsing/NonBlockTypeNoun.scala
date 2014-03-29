package cpup.mc.magic.api.oldenLanguage.runeParsing

import net.minecraft.block.Block
import cpup.mc.magic.api.oldenLanguage.casting.TCaster
import net.minecraft.entity.Entity
import cpup.mc.lib.util.pos.BlockPos

trait NonBlockTypeNoun extends TTypeNoun[Entity, Block] {
	override def blockClass = classOf[Block]
	override def filterBlock(caster: TCaster, pos: BlockPos) = false
}