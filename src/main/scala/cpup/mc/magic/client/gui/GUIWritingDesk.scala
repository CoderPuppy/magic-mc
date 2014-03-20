package cpup.mc.magic.client.gui.writingDesk

import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.inventory.{Slot, Container}
import cpup.mc.magic.content.TEWritingDesk
import cpup.mc.magic.client.gui.GUIBase
import net.minecraft.entity.player.EntityPlayer
import cpup.mc.lib.util.pos.BlockPos
import org.lwjgl.opengl.GL11
import net.minecraft.util.ResourceLocation
import net.minecraft.client.resources.I18n
import net.minecraft.item.ItemStack

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
	}
}

class InvContainer(val player: EntityPlayer, val te: TEWritingDesk) extends Container {
	addSlotToContainer(new Slot(te.inv, 0, 8, 18))
	addSlotToContainer(new Slot(te.inv, 1, 8, 36))
	addSlotToContainer(new Slot(te.inv, 2, 151, 27))

	for {
		x <- 0 to 8
		y <- 0 to 2
	} {
		addSlotToContainer(new Slot(player.inventory, x + y * 9 + 9, 8 + x * 18, WritingDeskGUI.playerOffset + y * 18))
	}

	for(x <- 0 to 8) {
		addSlotToContainer(new Slot(player.inventory, x, 8 + x * 18, WritingDeskGUI.playerOffset + 4 + (18 * 3)))
	}

	@Override
	def canInteractWith(player: EntityPlayer) = true

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