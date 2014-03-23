package cpup.mc.magic.content

import net.minecraft.item.ItemStack
import net.minecraft.entity.player.EntityPlayer
import java.util
import cpup.lib.Util
import cpup.mc.lib.util.ItemUtil
import net.minecraft.world.World
import net.minecraft.client.renderer.texture.IIconRegister
import cpw.mods.fml.relauncher.{Side, SideOnly}
import cpup.mc.magic.api.oldenLanguage._
import cpup.mc.magic.api.oldenLanguage.textParsing.{TextParser, RootContext}
import cpup.mc.magic.api.oldenLanguage.runeParsing.RuneParser

class ItemSpell extends ItemBase with TWritableItem {
	def readRunes(stack: ItemStack) = Util.checkNull(ItemUtil.compound(stack).getString("spell"), "").split(' ')
	def writeRunes(stack: ItemStack, runes: Seq[String]) {
		ItemUtil.compound(stack).setString("spell", runes.mkString(" "))
	}
	def writingType = WritingType.Ink

	override def addInformation(stack: ItemStack, player: EntityPlayer, _lore: util.List[_], par4: Boolean) {
		val lore = _lore.asInstanceOf[util.List[String]]

		val spell = Util.checkNull(ItemUtil.compound(stack).getString("spell"), "")
		val parser = new RuneParser

		lore.add(spell)

		try {
			val context = RootContext.create
			for {
				parsedRune <- TextParser.parse(spell)
				rune = parsedRune(context)
			} {
				lore.add(parsedRune.toString)
				lore.add(rune.toString)
				parser.handle(rune)
			}
		} catch {
			case e: Exception => {
				lore.add(e.toString)
			}
		}

		lore.add(parser.toString)
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