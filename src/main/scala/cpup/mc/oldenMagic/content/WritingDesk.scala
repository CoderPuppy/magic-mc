package cpup.mc.oldenMagic.content

import net.minecraft.block.Block
import cpup.mc.lib.content.{CPupTE, CPupBlockContainer}
import cpup.mc.oldenMagic.{OldenMagicMod, TOldenMagicMod}
import net.minecraft.world.World
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.ItemStack
import cpup.mc.lib.util.Direction
import cpup.mc.lib.util.pos.BlockPos
import net.minecraft.block.material.Material
import net.minecraft.init.Blocks
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import cpup.mc.oldenMagic.client.gui.writingDesk.WritingDeskGUI
import net.minecraft.inventory.IInventory
import net.minecraft.nbt.{NBTTagList, NBTTagCompound}
import cpup.mc.lib.network.BlockMessage
import cpup.mc.oldenMagic.network.Message
import io.netty.channel.ChannelHandlerContext
import io.netty.buffer.ByteBuf
import cpw.mods.fml.common.network.ByteBufUtils
import cpup.mc.oldenMagic.api.oldenLanguage.{TWritableItem, WritingType}
import cpup.mc.oldenMagic.api.oldenLanguage.textParsing.ParsedRune
import net.minecraftforge.common.util.Constants

class BlockWritingDesk extends Block(Material.wood) with TBlockBase with CPupBlockContainer[TOldenMagicMod] {
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
			placer match {
				case player: EntityPlayer =>
					val player = placer.asInstanceOf[EntityPlayer]
					val newStack = stack.copy
					newStack.stackSize = 1
					player.inventory.addItemStackToInventory(newStack)
				case _ =>
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

	override def handleMessage(rawMsg: BlockMessage[TOldenMagicMod]) {
		rawMsg match {
			case WritingDeskMessage(pos: BlockPos, rune: String) => {
				pos.tileEntity match {
					case te: TEWritingDesk =>
						val quill = te.inv.getStackInSlot(0)
						val ink = te.inv.getStackInSlot(1)

						if(quill != null && quill.getItem == mod.content.items("quill")) {
							if(ink != null && ink.getItem == mod.content.items("inkWell") && ink.getItemDamage < ink.getMaxDamage) {
								te.inv.getStackInSlot(2) match {
									case stack: ItemStack =>
										stack.getItem match {
											case item: TWritableItem =>
												quill.setItemDamage(quill.getItemDamage + 1)
												if(quill.getItemDamage > quill.getMaxDamage) {
													quill.stackSize -= 1

													if(quill.stackSize == 0) {
														te.inv.setInventorySlotContents(0, null)
													}
												}

												ink.setItemDamage(ink.getItemDamage + 1)
												if(ink.getItemDamage > ink.getMaxDamage) {
													ink.setItemDamage(ink.getMaxDamage)
												}

												item.writeRunes(stack, item.readRunes(stack) ++ Array(rune))

												te.markDirty
											case _ =>
										}
									case _ =>
								}
							}
						}
					case _ =>
				}
			}
			case _ => super.handleMessage(rawMsg)
		}
	}
}

class TEWritingDesk extends TileEntity with BaseTE {
	val inv = new WritingDeskInventory(this)

	override def writeToNBT(nbt: NBTTagCompound) {
		super.writeToNBT(nbt)
		nbt.setTag("Items", inv.serialize)
	}

	override def readFromNBT(nbt: NBTTagCompound) {
		super.readFromNBT(nbt)
		inv.unserialize(nbt.getTagList("Items", Constants.NBT.TAG_COMPOUND))
	}
}

class WritingDeskInventory(te: TileEntity) extends IInventory {
	def mod = OldenMagicMod

	protected var inv = Array.fill[ItemStack](3) {null}

	def serialize = {
		val items = new NBTTagList

		for((stack, i) <- inv.zipWithIndex) {
			if(stack != null) {
				val nbt = new NBTTagCompound
				nbt.setByte("Slot", i.toByte)
				stack.writeToNBT(nbt)
				items.appendTag(nbt)
			}
		}

		items
	}

	def unserialize(items: NBTTagList) {
		for(i <- 0 to items.tagCount - 1) {
			val nbt = items.getCompoundTagAt(i)
			val slot = nbt.getByte("Slot")
			if(slot >= 0 && slot < inv.length) {
				inv(slot) = ItemStack.loadItemStackFromNBT(nbt)
			}
		}
	}

	def getInventoryName = s"container.${mod.ref.modID}:writingDesk.name"
	def getInventoryStackLimit = 64
	def getSizeInventory = inv.size
	def hasCustomInventoryName = false
	def isUseableByPlayer(player: EntityPlayer) = true

	def markDirty {
		te.markDirty
	}

	def isItemValidForSlot(slot: Int, stack: ItemStack) = if(stack == null) { true } else slot match {
		case 0 => stack.getItem == mod.content.items("quill")
		case 1 => stack.getItem == mod.content.items("inkWell")
		case 2 => stack.getItem.isInstanceOf[TWritableItem] && stack.getItem.asInstanceOf[TWritableItem].writingType == WritingType.Ink
		case _ => false
	}

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

case class WritingDeskMessage(val pos: BlockPos, val rune: String) extends Message with BlockMessage[TOldenMagicMod] {
	def this(pos: BlockPos, parsedRune: ParsedRune) {
		this(pos, parsedRune.serialize)
	}

	val x = pos.x
	val y = pos.y
	val z = pos.z

	def this(ctx: ChannelHandlerContext, buf: ByteBuf, player: EntityPlayer) {
		this(BlockPos(
			player.worldObj,
			buf.readInt,
			buf.readInt,
			buf.readInt
		), ByteBufUtils.readUTF8String(buf))
	}

	def writeTo(ctx: ChannelHandlerContext, buf: ByteBuf) {
		buf.writeInt(x)
		buf.writeInt(y)
		buf.writeInt(z)
		ByteBufUtils.writeUTF8String(buf, rune)
	}
}