package cpup.mc.magic.content

import net.minecraft.item.ItemStack
import net.minecraft.entity.player.EntityPlayer
import java.util
import cpup.mc.magic.api.impl.{InvalidTransformException, ContextFactory, Parser}
import cpup.lib.Util
import cpup.mc.lib.util.ItemUtil
import net.minecraft.world.World

class ItemSpell extends ItemBase {
	override def addInformation(stack: ItemStack, player: EntityPlayer, _lore: util.List[_], par4: Boolean) {
		val lore = _lore.asInstanceOf[util.List[String]]

		val spell = Util.checkNull(ItemUtil.compound(stack).getString("spell"), "")

		lore.add(spell)

		try {
			for(rune <- Parser.parse(ContextFactory.create, spell)) {
				lore.add(rune.toString)
			}
		} catch {
			case e: InvalidTransformException => {
				lore.add(e.getMessage)
				println(e.getMessage)
			}
		}
	}

	override def onItemRightClick(stack: ItemStack, world: World, player: EntityPlayer) = {
		mod.proxy.activateSpellCasting(player, stack)
		stack
	}
}