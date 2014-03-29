package cpup.mc.oldenMagic.api.oldenLanguage.runeParsing

import net.minecraft.block.Block
import cpup.mc.oldenMagic.api.oldenLanguage.casting.TCaster
import net.minecraft.entity.Entity
import cpup.mc.lib.util.pos.BlockPos

trait NonBlockTypeNoun extends TTypeNoun[Entity, Block] {
	override def blockClass = classOf[Block]
	override def filterBlock(caster: TCaster, pos: BlockPos) = false
}