package cpup.mc.magic.content

import net.minecraft.block.Block
import cpup.mc.lib.content.CPupBlockContainer
import cpup.mc.magic.TMagicMod
import net.minecraft.world.World
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.ItemStack
import cpup.mc.lib.util.Direction
import cpup.mc.lib.util.pos.BlockPos
import net.minecraft.block.material.Material
import net.minecraft.init.Blocks
import net.minecraft.entity.player.EntityPlayer

class BlockWritingDesk extends Block(Material.wood) with TBlockBase with CPupBlockContainer[TMagicMod] {
	setHardness(1)
	setResistance(2.5f)

	override def breakBlock(world: World, x: Int, y: Int, z: Int, block: Block, meta: Int): Unit = {
		super.breakBlock(world, x, y, z, block, meta)

		val pos = BlockPos(world, x, y, z)
		val dir = Direction.fromFacing(meta >> 1)
		val isSlave = (meta & 1) == 0

		if(isSlave) {
			val masterPos = pos.offset(dir.rotated(Direction.Up))
			if(masterPos.block == this) {
				masterPos.setBlock(Blocks.air)
			}
		} else {
			val slavePos = pos.offset(dir.unrotated(Direction.Up))
			if(slavePos.block == this) {
				slavePos.setBlock(Blocks.air)
			}
		}
	}

	override def onBlockPlacedBy(world: World, x: Int, y: Int, z: Int, placer: EntityLivingBase, stack: ItemStack) {
		val dir = Direction.fromYaw(placer.rotationYaw).opposite

		val pos = BlockPos(world, x, y, z)
			.setMetadata((dir.facing << 1) | 1, 2)
		val slavePos = pos.offset(dir.unrotated(Direction.Up))
		if(slavePos.isReplaceable) {
			slavePos
				.setBlock(this)
				.setMetadata(dir.facing << 1, 2)
		} else {
			pos.setBlock(Blocks.air)
			if(placer.isInstanceOf[EntityPlayer]) {
				val player = placer.asInstanceOf[EntityPlayer]
				player.inventory.addItemStackToInventory(stack)
			} else {
				// This might be buggy...
				placer.entityDropItem(stack, 0.4f)
			}
		}
	}
}