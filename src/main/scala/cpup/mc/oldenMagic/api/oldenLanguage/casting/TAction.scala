package cpup.mc.oldenMagic.api.oldenLanguage.casting

import net.minecraft.entity.Entity
import cpup.mc.lib.util.pos.BlockPos

trait TAction {
	def affectedEntities: List[Entity]
	def affectedBlocks: List[BlockPos]
}