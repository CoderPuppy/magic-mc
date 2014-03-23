package cpup.mc.magic.content

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.world.World
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.init.Blocks
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.ItemStack
import net.minecraft.util.MathHelper
import net.minecraft.entity.player.EntityPlayer

class BlockDeedOffice extends Block(Material.wood) with TBlockBase {
	setStepSound(Block.soundTypeWood)
	setHardness(2)
	setResistance(5)

	override def onBlockPlacedBy(world: World, x: Int, y: Int, z: Int, placer: EntityLivingBase, stack: ItemStack) {
		val dir = MathHelper.floor_double((placer.rotationYaw / 90D) + 0.5) & 3
		world.setBlockMetadataWithNotify(x, y, z, dir << 1, 2)
	}

	@SideOnly(Side.CLIENT)
	override def getIcon(side: Int, meta: Int) = {
		if(meta == 10) {
			Blocks.crafting_table.getIcon(side, 0)
		} else if((meta & 1) == 1 && (meta >> 1 match {
			case 0 => 2 // Z POS
			case 2 => 3 // Z NEG
			case 3 => 4 // X POS
			case 1 => 5 // X NEG
			case _ => 0
		}) == side) {
			Blocks.bookshelf.getIcon(side, 0)
		} else {
			Blocks.planks.getIcon(side, 0)
		}
	}

	def findShelves(world: World, tableX: Int, tableY: Int, tableZ: Int) {
		for {
			x <- tableX - 5 to tableX + 5
			y <- tableY to tableY + 3
			z <- tableZ - 5 to tableZ + 5
		} {
			val meta = world.getBlockMetadata(x, y, z)
			if(world.getBlock(x, y, z) == this && meta != 10 && (meta & 1) == 0) {
				world.setBlockMetadataWithNotify(x, y, z, meta | 1, 2)
			}
		}
	}

	override def onBlockActivated(world: World, x: Int, y: Int, z: Int, player: EntityPlayer, side: Int, hitX: Float, hitY: Float, hitZ: Float) = {
		if(player.getCurrentEquippedItem == null && (world.getBlockMetadata(x, y, z) & 1) == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 10, 2)
			findShelves(world, x, y, z)

			true
		} else if(world.getBlockMetadata(x, y, z) == 10) {
			findShelves(world, x, y, z)

			true
		} else {
			false
		}
	}
}