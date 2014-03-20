package cpup.mc.magic.client.gui.writingDesk

import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.inventory.Container
import cpup.mc.magic.content.TEWritingDesk
import cpup.mc.magic.client.gui.GUIBase
import net.minecraft.entity.player.EntityPlayer
import cpup.mc.lib.util.pos.BlockPos

object GUI extends GUIBase[ClientGUI, InvContainer] {
	def name = "writingDesk"
	def clientGUI(player: EntityPlayer, pos: BlockPos) = new ClientGUI(container(player, pos))
	def container(player: EntityPlayer, pos: BlockPos) = {
		val te = pos.tileEntity
		if(te.isInstanceOf[TEWritingDesk]) {
			new InvContainer(te.asInstanceOf[TEWritingDesk])
		} else { null }
	}
}

class ClientGUI(val container: InvContainer) extends GuiContainer(container) {
	@Override
	def drawGuiContainerBackgroundLayer(par1: Float, par2: Int, par3: Int) {

	}
}

class InvContainer(val te: TEWritingDesk) extends Container {
	@Override
	def canInteractWith(player: EntityPlayer) = true
}