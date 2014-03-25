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

		// TODO: I need a better way to do this
//		if(blockTargets.isEmpty) {
//			blockTargets = List(BlockPos(world, 0, 0, 0))
//		}
//
//		if(entityTargets.isEmpty) {
//			entityTargets = List(new BlankEntity(world))
//		}

		for(noun <- spell.targetPath) {
			blockTargets = blockTargets.flatMap(noun.getBlocks(this, _))
			entityTargets = entityTargets.flatMap(noun.getEntities(this, _))
		}

		for(block <- blockTargets) {
			spell.action.actUponBlock(block)
		}

		for(entity <- entityTargets) {
			spell.action.actUponEntity(entity)
		}
	}
}

private class BlankEntity(world: World) extends Entity(world) {
	override def readEntityFromNBT(var1: NBTTagCompound) {}
	override def writeEntityToNBT(var1: NBTTagCompound) {}
	override def entityInit() {}
}