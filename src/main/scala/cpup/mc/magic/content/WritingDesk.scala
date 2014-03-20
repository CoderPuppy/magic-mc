package cpup.mc.magic.content

import net.minecraft.block.Block
import cpup.mc.lib.content.{CPupTE, CPupBlockContainer}
import cpup.mc.magic.{MagicMod, TMagicMod}
import net.minecraft.world.World
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.ItemStack
import cpup.mc.lib.util.Direction
import cpup.mc.lib.util.pos.BlockPos
import net.minecraft.block.material.Material
import net.minecraft.init.Blocks
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import cpup.mc.magic.client.gui.writingDesk.WritingDeskGUI
import net.minecraft.inventory.IInventory

class BlockWritingDesk extends Block(Material.wood) with TBlockBase with CPupBlockContainer[TMagicMod] {
	setHardness(1)
	setResistance(2.5f)

	def getMaster(pos: BlockPos) = if((pos.metadata & 1) == 1) {
		pos
	} else {
		pos.offset(Direction.fromFacing(pos.metadata >> 1).rotated(Direction.Up))
	}

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
//			pos.tileEntity = new TEWritingDesk

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

	@Override
	def createNewTileEntity(world: World, meta: Int) = new TEWritingDesk

	override def onBlockActivated(world: World, x: Int, y: Int, z: Int, player: EntityPlayer, side: Int, hitX : Float, hitY: Float, hitZ: Float) = {
		super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ)
		val pos = BlockPos(world, x, y, z)
		val masterPos = getMaster(pos)

		if(player.isSneaking) { false }
		else {
			val rawTE = masterPos.tileEntity
			if(rawTE != null && rawTE.isInstanceOf[TEWritingDesk]) {
				mod.guis.open(player, masterPos, WritingDeskGUI)
			}

			true
		}
	}
}

class TEWritingDesk extends TileEntity with CPupTE {
	val inv = new WritingDeskInventory(this)
}

class WritingDeskInventory(te: TileEntity) extends IInventory {
	def mod = MagicMod

	protected var inv = Array.fill[ItemStack](3) {null}

	def getInventoryName = "container." + mod.ref.modID + ":writingDesk.name"
	def getInventoryStackLimit = 64
	def getSizeInventory = inv.size
	def hasCustomInventoryName = false
	def isUseableByPlayer(player: EntityPlayer) = true

	def markDirty {
		te.markDirty
	}

	def isItemValidForSlot(slot: Int, stack: ItemStack) = true // TODO: limit slots

	def setInventorySlotContents(slot: Int, stack: ItemStack) {
		inv(slot) = stack

		if(stack != null && stack.stackSize > getInventoryStackLimit) {
			stack.stackSize = getInventoryStackLimit
		}

		markDirty
	}
	def getStackInSlot(slot: Int) = inv(slot)
	def getStackInSlotOnClosing(slot: Int) = {
		if(inv(slot) == null) { null }
		else {
			val stack = inv(slot)
			inv(slot) = null
			stack
		}
	}

	def decrStackSize(slot: Int, amt: Int) = {
		if(inv(slot) != null) {
			var stack: ItemStack = null
			if(inv(slot).stackSize <= amt) {
				stack = inv(slot)
				inv(slot) = null
				markDirty
				stack
			}
			else {
				stack = inv(slot).splitStack(amt)
				if(inv(slot).stackSize == 0) {
					inv(slot) = null
				}
				markDirty
				stack
			}
		} else {
			null
		}

	}

	def openInventory {}
	def closeInventory {}
}