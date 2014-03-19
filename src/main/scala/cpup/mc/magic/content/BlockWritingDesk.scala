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

class BlockWritingDesk extends Block(Material.wood) with TBlockBase with CPupBlockContainer[TMagicMod] {
	setHardness(1)
	setResistance(2.5f)

	override def onBlockPlacedBy(world: World, x: Int, y: Int, z: Int, placer: EntityLivingBase, stack: ItemStack) {
		val dir = Direction.fromYaw(placer.rotationYaw)
		BlockPos(world, x, y, z).setMetadata(dir.opposite.facing, 2)
	}
}