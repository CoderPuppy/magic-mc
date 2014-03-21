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

class ClientEvents(val proxy: ClientProxy) {
	val mc = Minecraft.getMinecraft

	def mod = MagicMod

	@SubscribeEvent
	def handleKeyboardInput(e: InputEvent.KeyInputEvent) {
		if(proxy.category != null) {
			val key = Keyboard.getEventKey
			println(key)
			if(
				if(key >= Keyboard.KEY_Q && key <= Keyboard.KEY_Y) {
					true
				} else if(key == Keyboard.KEY_A) {
					proxy.category.scrollUp
					true
				} else if(key == Keyboard.KEY_D) {
					proxy.category.scrollDown
					true
				} else if(key == Keyboard.KEY_S) {
					if(proxy.category.parent != null) {
						proxy.category = proxy.category.parent
					}
					true
				} else { false }
			) {
				KeyBinding.setKeyBindState(key, false)
			}
		}
	}

	@SubscribeEvent
	def checkCastingItem(e: TickEvent.ClientTickEvent) {
		if(e.phase == Phase.END && proxy.category != null && proxy.castingItem != null) {
			if(mc.thePlayer.inventory.getCurrentItem != proxy.castingItem) {
				proxy.stopSpellCasting
			}
		}
	}

	@SubscribeEvent
	def renderOptions(e: RenderGameOverlayEvent) {
		if(proxy.category != null && !e.isCancelable && e.`type` == RenderGameOverlayEvent.ElementType.HOTBAR) {
			GL11.glColor4f(1, 1, 1, 1)
			GL11.glDisable(GL11.GL_LIGHTING)
			for(i <- 0 to 5) {
				val option = proxy.category(i)
				mc.fontRenderer.drawString(option match {
					case runeOpt: RuneOption => runeOpt.text
					case cat: Category => cat.name
					case any: Any => any.toString
					case null => "null"
				}, 20, 20 + i * 10, 4210752)
			}
		}
	}
}