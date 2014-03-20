package cpup.mc.magic.client.gui.writingDesk

import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.inventory.Container
import cpup.mc.magic.content.TEWritingDesk
import cpup.mc.magic.client.gui.GUIBase
import net.minecraft.entity.player.EntityPlayer
import cpup.mc.lib.util.pos.BlockPos
import org.lwjgl.opengl.GL11
import net.minecraft.util.ResourceLocation

object WritingDeskGUI extends GUIBase[ClientGUI, InvContainer] {
	def name = "writingDesk"
	def clientGUI(player: EntityPlayer, pos: BlockPos) = new ClientGUI(container(player, pos))
	def container(player: EntityPlayer, pos: BlockPos) = {
		val te = pos.tileEntity
		if(te.isInstanceOf[TEWritingDesk]) {
			new InvContainer(te.asInstanceOf[TEWritingDesk])
		} else { null }
	}

	final val background = new ResourceLocation(mod.ref.modID + ":textures/gui/writingDesk")
}

class ClientGUI(val container: InvContainer) extends GuiContainer(container) {
	@Override
	def drawGuiContainerBackgroundLayer(partialTicks: Float, width: Int, height: Int) {
		GL11.glColor4f(1, 1, 1, 1)
		mc.renderEngine.bindTexture(WritingDeskGUI.background)
		val x = (width - xSize) / 2
		val y = (height - ySize) / 2
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize)
	}
}

class InvContainer(val te: TEWritingDesk) extends Container {
	@Override
	def canInteractWith(player: EntityPlayer) = true
}