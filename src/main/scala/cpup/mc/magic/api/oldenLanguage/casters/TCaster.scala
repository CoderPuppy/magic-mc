package cpup.mc.magic.api.oldenLanguage.casters

import net.minecraft.util.MovingObjectPosition
import net.minecraft.world.World
import net.minecraft.entity.Entity
import cpup.mc.lib.util.pos.BlockPos
import cpup.mc.magic.api.oldenLanguage.runeParsing.Spell
import net.minecraft.nbt.NBTTagCompound

trait TCaster {
	def world: World
	def mop: MovingObjectPosition

	def blocks: List[BlockPos]
	def entities: List[Entity]

	def cast(spell: Spell) {
		var blockTargets = blocks
		var entityTargets = entities

		for(noun <- spell.targetPath) {
			blockTargets = noun.getBlocks(this, blockTargets)
			entityTargets = noun.getEntities(this, entityTargets)
		}

		for(block <- blockTargets) {
			spell.action.actUponBlock(block)
		}

		for(entity <- entityTargets) {
			spell.action.actUponEntity(entity)
		}
	}
}