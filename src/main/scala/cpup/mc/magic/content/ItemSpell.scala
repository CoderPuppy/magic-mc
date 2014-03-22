package cpup.mc.magic.content

import net.minecraft.item.ItemStack
import net.minecraft.entity.player.EntityPlayer
import java.util
import cpup.lib.Util
import cpup.mc.lib.util.ItemUtil
import net.minecraft.world.World
import net.minecraft.client.renderer.texture.IIconRegister
import cpw.mods.fml.relauncher.{Side, SideOnly}
import cpup.mc.magic.api.Parser
import cpup.mc.magic.api.oldenLanguage.{InvalidTransformException, OldenLanguageRegistry, Parser, ContextFactory}

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

	@SideOnly(Side.CLIENT)
	override def registerIcons(register: IIconRegister) {
		super.registerIcons(register)

		for(runeType <- OldenLanguageRegistry.runeTypes) {
			runeType.registerIcons((iconStr: String) => register.registerIcon(iconStr))
		}
	}

	override def onItemRightClick(stack: ItemStack, world: World, player: EntityPlayer) = {
		mod.proxy.activateSpellCasting(player, stack)
		stack
	}
}