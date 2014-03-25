package cpup.mc.magic.api.oldenLanguage.runes

import cpup.mc.lib.util.pos.BlockPos
import net.minecraft.entity.Entity
import cpup.mc.magic.api.oldenLanguage.casters.TCaster

trait TNoun {
	def getBlocks(caster: TCaster, origin: BlockPos): List[BlockPos]
	def getEntities(caster: TCaster, origin: Entity): List[Entity]
}