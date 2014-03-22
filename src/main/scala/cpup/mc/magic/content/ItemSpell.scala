package cpup.mc.magic.content

import net.minecraft.item.ItemStack
import net.minecraft.entity.player.EntityPlayer
import java.util
import cpup.lib.Util
import cpup.mc.lib.util.ItemUtil
import net.minecraft.world.World
import net.minecraft.client.renderer.texture.IIconRegister
import cpw.mods.fml.relauncher.{Side, SideOnly}
import cpup.mc.magic.api.oldenLanguage.{InvalidTransformException, OldenLanguageRegistry, Parser, RootContext}

class ItemSpell extends ItemBase {
	override def addInformation(stack: ItemStack, player: EntityPlayer, _lore: util.List[_], par4: Boolean) {
		val lore = _lore.asInstanceOf[util.List[String]]

		val spell = Util.checkNull(ItemUtil.compound(stack).getString("spell"), "")

		lore.add(spell)

		try {
			val context = RootContext.create
			for {
				parsedRune <- Parser.parse(spell)
				rune = parsedRune(context)
			} {
				lore.add(parsedRune.toString)
				lore.add(rune.toString)
			}
		} catch {
			case e: Exception => {
				lore.add(e.toString)
				println(e)
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
		mod.proxy.activateSpellCasting(player)
		stack
	}
}