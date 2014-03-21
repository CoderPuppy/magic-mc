package cpup.mc.magic.client.gui.writingDesk

import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.inventory.{Slot, Container}
import cpup.mc.magic.content.{WritingDeskMessage, TEWritingDesk}
import cpup.mc.magic.client.gui.GUIBase
import net.minecraft.entity.player.EntityPlayer
import cpup.mc.lib.util.pos.BlockPos
import org.lwjgl.opengl.GL11
import net.minecraft.util.ResourceLocation
import net.minecraft.client.resources.I18n
import net.minecraft.item.ItemStack
import cpup.mc.lib.client.BetterSlot
import scala.util.control.Breaks
import cpup.mc.magic.client.runeSelection.{RootCategory, RuneOption, Category}
import org.lwjgl.input.Keyboard
import cpup.mc.magic.MagicMod

object WritingDeskGUI extends GUIBase[ClientGUI, InvContainer] {
	def name = "writingDesk"
	def clientGUI(player: EntityPlayer, pos: BlockPos) = new ClientGUI(container(player, pos))
	def container(player: EntityPlayer, pos: BlockPos) = pos.tileEntity match {
		case desk: TEWritingDesk =>
			new InvContainer(player, desk)
		case _ => null
	}

	final val background = new ResourceLocation(mod.ref.modID + ":textures/gui/writingDesk.png")
	def playerOffset = 56
}

class ClientGUI(val container: InvContainer) extends GuiContainer(container) {
	def mod = MagicMod

	var category = RootCategory.create

	override def keyTyped(char: Char, key: Int): Unit = {
		super.keyTyped(char, key)

		if(key >= Keyboard.KEY_Q && key <= Keyboard.KEY_Y) {
			val index = key - Keyboard.KEY_Q
			category(index) match {
				case cat: Category => category = cat
				case runeOpt: RuneOption =>
					println("adding", runeOpt.text)
					mod.network.sendToServer(WritingDeskMessage(container.te.pos, runeOpt.text))
			}
		} else if(key == Keyboard.KEY_A) {
			category.scrollUp
		} else if(key == Keyboard.KEY_D) {
			category.scrollDown
		} else if(key == Keyboard.KEY_S) {
			if(category.parent != null) {
				category = category.parent
			}
		}
	}

	@Override
	def drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
		GL11.glColor4f(1, 1, 1, 1)
		mc.renderEngine.bindTexture(WritingDeskGUI.background)
		val x = (width - xSize) / 2
		val y = (height - ySize) / 2
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize)
	}

	override def drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
		fontRendererObj.drawString(I18n.format(container.te.inv.getInventoryName), 8, 6, 4210752)
		fontRendererObj.drawString(I18n.format(container.player.inventory.getInventoryName), 8, WritingDeskGUI.playerOffset, 4210752)

		for(i <- 0 to 5) {
			fontRendererObj.drawString(i.toString + ": " + (category(i) match {
				case runeOpt: RuneOption => runeOpt.text
				case cat: Category => cat.name
				case any: Any => "unknown: " + any.toString
				case null => "null"
			}), -200, i * 10, 4210752)
		}
	}
}

class InvContainer(val player: EntityPlayer, val te: TEWritingDesk) extends Container {
	addSlotToContainer(new BetterSlot(te.inv, 0, 8, 18))
	addSlotToContainer(new BetterSlot(te.inv, 1, 8, 36))
	addSlotToContainer(new BetterSlot(te.inv, 2, 151, 27))

	for {
		x <- 0 to 8
		y <- 0 to 2
	} {
		addSlotToContainer(new BetterSlot(player.inventory, x + y * 9 + 9, 8 + x * 18, WritingDeskGUI.playerOffset + 12 + y * 18))
	}

	for(x <- 0 to 8) {
		addSlotToContainer(new BetterSlot(player.inventory, x, 8 + x * 18, WritingDeskGUI.playerOffset + 16 + (18 * 3)))
	}

	@Override
	def canInteractWith(player: EntityPlayer) = true

	// mergeItemStack(stack, start, end, reverse)
	override def mergeItemStack(stack: ItemStack, min: Int, max: Int, reverse: Boolean): Boolean = {
		var flag1 = false
		var i = min

		if(reverse) {
			i = max - 1
		}

		var slot: Slot = null
		var stack1: ItemStack = null

		if(stack.isStackable) {
			while(stack.stackSize > 0 && (!reverse && i < max || reverse && i >= min)) {
				slot = this.inventorySlots.get(i).asInstanceOf[Slot]
				stack1 = slot.getStack
				if(stack1 != null && stack1.getItem == stack.getItem && (!stack.getHasSubtypes || stack.getItemDamage == stack1.getItemDamage) && ItemStack.areItemStackTagsEqual(stack, stack1)) {
					val l: Int = stack1.stackSize + stack.stackSize
					if(l <= stack.getMaxStackSize) {
						stack.stackSize = 0
						stack1.stackSize = l
						slot.onSlotChanged
						flag1 = true
					}
					else if(stack1.stackSize < stack.getMaxStackSize) {
						stack.stackSize -= stack.getMaxStackSize - stack1.stackSize
						stack1.stackSize = stack.getMaxStackSize
						slot.onSlotChanged
						flag1 = true
					}
				}
				if(reverse) {
					i -= 1
				}
				else {
					i += 1
				}
			}
		}

		if(stack.stackSize > 0) {
			if(reverse) {
				i = max - 1
			} else {
				i = min
			}
			Breaks.breakable { while(!reverse && i < max || reverse && i >= min) {
				slot = this.inventorySlots.get(i).asInstanceOf[Slot]
				stack1 = slot.getStack
				if(stack1 == null && slot.isItemValid(stack)) {
					slot.putStack(stack.copy)
					slot.onSlotChanged
					stack.stackSize = 0
					flag1 = true
					Breaks.break
				} else {
					if(reverse) {
						i -= 1
					} else {
						i += 1
					}
				}
			} }
		}

		return flag1
	}

	override def transferStackInSlot(player: EntityPlayer, slotID: Int): ItemStack = {
		var stack: ItemStack = null
		val slot = inventorySlots.get(slotID).asInstanceOf[Slot]
		if(slot != null && slot.getHasStack) {
			val stack1 = slot.getStack
			stack = stack1.copy
			if(slotID < te.inv.getSizeInventory) {
				if(!mergeItemStack(stack1, te.inv.getSizeInventory, inventorySlots.size, true)) {
					return null
				}
			} else if(!mergeItemStack(stack1, 0, te.inv.getSizeInventory, false)) {
				return null
			}
			if(stack1.stackSize == 0) {
				slot.putStack(null)
			}
			else {
				slot.onSlotChanged
			}
		}
		return stack
	}
}