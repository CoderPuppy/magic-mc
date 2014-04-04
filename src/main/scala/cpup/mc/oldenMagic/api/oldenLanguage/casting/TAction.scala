package cpup.mc.oldenMagic.api.oldenLanguage.casting

import net.minecraft.entity.Entity
import cpup.mc.lib.util.pos.BlockPos
import cpup.mc.oldenMagic.api.oldenLanguage.runes.TRuneType

trait TAction {
	def affectedEntities: List[Entity]
	def affectedBlocks: List[BlockPos]
	def runeType: TRuneType
}