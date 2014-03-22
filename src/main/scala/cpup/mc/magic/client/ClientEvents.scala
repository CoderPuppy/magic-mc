package cpup.mc.magic.client

import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.{TickEvent, InputEvent}
import org.lwjgl.input.Keyboard
import net.minecraft.client.settings.KeyBinding
import cpup.mc.magic.MagicMod
import cpw.mods.fml.common.gameevent.TickEvent.Phase
import net.minecraft.client.Minecraft
import net.minecraftforge.client.event.RenderGameOverlayEvent
import cpup.mc.magic.client.runeSelection.{Category, RuneOption}
import org.lwjgl.opengl.GL11
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
		if(proxy.category != null && mc.theWorld != null) {
			val key = Keyboard.getEventKey
			println(Keyboard.getKeyName(key))

			if(key >= Keyboard.KEY_1 && key <= Keyboard.KEY_6) {
				val index = key - Keyboard.KEY_1
				val option = proxy.category(index)
				option match {
					case cat: Category =>
						proxy.category = cat
					case runeOpt: RuneOption =>
						proxy.spell ++= List(runeOpt)
						println("adding", runeOpt.parsedRune)
					case _ => {
						println("unknown")
					}
				}
			} else if(key == Keyboard.KEY_Q) {
				proxy.category.scrollUp
			} else if(key == Keyboard.KEY_E) {
				proxy.category.scrollDown
			} else if(key == Keyboard.KEY_R) {
				if(proxy.category.parent != null) {
					proxy.category = proxy.category.parent
				}
			} else if(key == Keyboard.KEY_F) {
				println("casting: " + proxy.spell.mkString(" "))
				proxy.stopSpellCasting(mc.thePlayer)
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
		if(e.phase == Phase.END && mc.theWorld != null && proxy.category != null && proxy.castingItem != null) {
			if(mc.thePlayer.inventory.getCurrentItem != proxy.castingItem) {
				//				println(mc.thePlayer.inventory.getCurrentItem, proxy.castingItem)
				//				proxy.stopSpellCasting
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
		if(proxy.category != null && mc.theWorld != null && !e.isCancelable && e.`type` == RenderGameOverlayEvent.ElementType.HOTBAR) {
			GL11.glColor4f(1, 1, 1, 1)
			GL11.glDisable(GL11.GL_LIGHTING)
			for(i <- 0 to 5) {
				val option = proxy.category(i)
				mc.fontRenderer.drawString(option match {
					case runeOpt: RuneOption => runeOpt.parsedRune.toString
					case cat: Category => cat.name
					case any: Any => any.toString
					case null => "null"
				}, 20, 20 + i * 10, 0x909090)
			}
		}
	}
}