package cpup.mc.magic.client

import cpw.mods.fml.common.eventhandler.{EventPriority, SubscribeEvent}
import cpw.mods.fml.common.gameevent.{TickEvent, InputEvent}
import org.lwjgl.input.Keyboard
import net.minecraft.client.settings.KeyBinding
import cpup.mc.magic.MagicMod
import cpw.mods.fml.common.gameevent.TickEvent.Phase
import net.minecraft.client.Minecraft
import net.minecraftforge.client.event.{RenderWorldLastEvent, RenderGameOverlayEvent}
import scala.collection.mutable
import net.minecraft.client.renderer.{RenderBlocks, Tessellator}
import org.lwjgl.opengl.GL11
import cpup.mc.magic.content.ItemBend
import net.minecraft.util.ResourceLocation
import net.minecraft.init.Blocks
import net.minecraft.client.renderer.texture.TextureMap
import cpup.mc.lib.util.{Direction, VectorUtil}

class ClientEvents(val proxy: ClientProxy) {
	val mc = Minecraft.getMinecraft
	val renderBlocks = new RenderBlocks

	def mod = MagicMod

	// -- Wand spell casting --
	val keysDown = new mutable.HashMap[KeyBinding, Boolean]()
	var firstEvent = true
	val allowedKeys = List(
		mc.gameSettings.keyBindForward,
		mc.gameSettings.keyBindLeft,
		mc.gameSettings.keyBindBack,
		mc.gameSettings.keyBindRight,
		mc.gameSettings.keyBindJump,
		mc.gameSettings.keyBindSneak
	)

	@SubscribeEvent
	def castingKeys(e: InputEvent.KeyInputEvent) {
		if(proxy.selector != null && mc.theWorld != null) {
			val key = Keyboard.getEventKey

			if(key == Keyboard.KEY_F) {
				mod.logger.info("casting: " + proxy.spell.mkString(" "))
				proxy.stopSpellCasting(mc.thePlayer)
			} else {
				proxy.selector.handleKey(key)
			}

			if(firstEvent) {
				firstEvent = false

				for(key <- allowedKeys) {
					keysDown(key) = key.getIsKeyPressed
				}
			}

			KeyBinding.unPressAllKeys

			for((key, isDown) <- keysDown) {
				KeyBinding.setKeyBindState(key.getKeyCode, isDown)
			}
		}
	}

	@SubscribeEvent
	def checkCastingItem(e: TickEvent.ClientTickEvent) {
		if(e.phase == Phase.END && mc.theWorld != null && proxy.selector != null) {
			if(mc.thePlayer.inventory.currentItem != proxy.castingItem) {
				proxy.stopSpellCasting(mc.thePlayer)
			}
		}
	}

	@SubscribeEvent
	def resetCastingKeys(e: TickEvent.ClientTickEvent) {
		if(e.phase == Phase.END && mc.theWorld != null) {
			firstEvent = true
			keysDown.clear
		}
	}

	@SubscribeEvent
	def renderCastingOptions(e: RenderGameOverlayEvent) {
		if(proxy.selector != null && mc.theWorld != null && !e.isCancelable && e.`type` == RenderGameOverlayEvent.ElementType.HOTBAR) {
			proxy.selector.render
		}
	}

	// -- Bend --
	val bendTexture = new ResourceLocation(mod.ref.modID, "textures/misc/bend.png")

	@SubscribeEvent
	def renderBend(e: RenderWorldLastEvent) {
		val tess = Tessellator.instance
		val renderEntity = mc.renderViewEntity
		val world = mc.theWorld
		val player = mc.thePlayer
		val textureManager = mc.getTextureManager

		if(player.isUsingItem && player.getItemInUse.getItem == mod.content.items("bend")) {
			val pos = mod.content.items("bend").asInstanceOf[ItemBend].getFarLook(player.getItemInUse, player, player.getItemInUseCount)

			GL11.glPushMatrix
			GL11.glTranslated(
				-(renderEntity.lastTickPosX + (renderEntity.posX - renderEntity.lastTickPosX) * e.partialTicks),
				-(renderEntity.lastTickPosY + (renderEntity.posY - renderEntity.lastTickPosY) * e.partialTicks),
				-(renderEntity.lastTickPosZ + (renderEntity.posZ - renderEntity.lastTickPosZ) * e.partialTicks)
			)

			{
				GL11.glPushMatrix
				GL11.glTranslated(pos.xCoord + 0.5, pos.yCoord, pos.zCoord + 0.5)
				GL11.glEnable(GL11.GL_LIGHTING)

				renderBlocks.setRenderBounds(0.05, 0.05, 0.05, 0.95, 0.95, 0.95)
				tess.startDrawingQuads
//				tess.setColorRGBA(255, 255, 255, 50)
//				tess.setBrightness(100)
				textureManager.bindTexture(TextureMap.locationBlocksTexture)
				val block = Blocks.wool
				val icon = block.getIcon(0, 0)
				renderBlocks.renderFaceXNeg(block, -0.5, 0, -0.5, icon)
				renderBlocks.renderFaceXPos(block, -0.5, 0, -0.5, icon)
				renderBlocks.renderFaceYNeg(block, -0.5, 0, -0.5, icon)
				renderBlocks.renderFaceYPos(block, -0.5, 0, -0.5, icon)
				renderBlocks.renderFaceZNeg(block, -0.5, 0, -0.5, icon)
				renderBlocks.renderFaceZPos(block, -0.5, 0, -0.5, icon)
				GL11.glColor4d(1, 1, 1, 0.5)
				tess.draw

				GL11.glDisable(GL11.GL_LIGHTING)
				GL11.glPopMatrix
			}

			{
				var blockPos = VectorUtil.toBlockPos(world, pos)

				if(blockPos.isAir) {
					var air = true
					while(air) {
						val newBlock = blockPos.offset(Direction.Down)

						if(newBlock.isAir) {
							blockPos = newBlock
						} else {
							air = false
						}
					}
				}

				GL11.glPushMatrix

				GL11.glTranslated(blockPos.x + 0.5, blockPos.y, blockPos.z + 0.5)

				tess.startDrawingQuads
				textureManager.bindTexture(bendTexture)
				val block = Blocks.stone
				val icon = block.getBlockTextureFromSide(0)
				renderBlocks.renderFaceYPos(block, -0.5, 0, -0.5, icon)
				GL11.glColor4d(1, 1, 1, 0.5)
				tess.draw

				GL11.glPopMatrix
			}

			GL11.glPopMatrix
		}
	}
}