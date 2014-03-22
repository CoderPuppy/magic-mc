package cpup.mc.magic.client

import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.{TickEvent, InputEvent}
import org.lwjgl.input.Keyboard
import net.minecraft.client.settings.KeyBinding
import cpup.mc.magic.MagicMod
import cpw.mods.fml.common.gameevent.TickEvent.Phase
import net.minecraft.client.Minecraft
import net.minecraftforge.client.event.RenderGameOverlayEvent
import cpup.mc.magic.client.runeSelection.RuneOption
import scala.collection.mutable

class ClientEvents(val proxy: ClientProxy) {
	val mc = Minecraft.getMinecraft

	def mod = MagicMod

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
	def handleKeyboardInput(e: InputEvent.KeyInputEvent) {
		if(proxy.selector != null && mc.theWorld != null) {
			val key = Keyboard.getEventKey
			println(Keyboard.getKeyName(key))

			if(key == Keyboard.KEY_F) {
				println("casting: " + proxy.spell.mkString(" "))
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
				//				println(mc.thePlayer.inventory.getCurrentItem, proxy.castingItem)
				proxy.stopSpellCasting(mc.thePlayer)
			}
		}
	}

	@SubscribeEvent
	def resetKeys(e: TickEvent.ClientTickEvent) {
		if(e.phase == Phase.END && mc.theWorld != null) {
			firstEvent = true
			keysDown.clear
		}
	}

	@SubscribeEvent
	def renderOptions(e: RenderGameOverlayEvent) {
		if(proxy.selector != null && mc.theWorld != null && !e.isCancelable && e.`type` == RenderGameOverlayEvent.ElementType.HOTBAR) {
			proxy.selector.render
		}
	}
}