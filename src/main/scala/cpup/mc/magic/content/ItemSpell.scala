package cpup.mc.magic.content

import net.minecraft.item.ItemStack
import net.minecraft.entity.player.EntityPlayer
import java.util
import cpup.lib.Util
import cpup.mc.lib.util.ItemUtil
import net.minecraft.client.renderer.texture.IIconRegister
import cpw.mods.fml.relauncher.{Side, SideOnly}
import cpup.mc.magic.api.oldenLanguage._
import cpup.mc.magic.api.oldenLanguage.textParsing.{TextParser, RootContext}
import cpup.mc.magic.api.oldenLanguage.runeParsing.RuneParser
import net.minecraft.world.World
import cpup.mc.magic.api.oldenLanguage.casters.PlayerCaster

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
			parser.finish
		} catch {
			case e: Exception => {
				lore.add(e.toString)
				if(e.getStackTrace.isDefinedAt(6)) {
					lore.add(e.getStackTrace()(6).toString)
				}
//				for(el <- e.getStackTrace) {
//					lore.add(el.toString)
//				}
			}
		}

		for(line <- parser.toString.split('\n')) {
			lore.add(line)
		}
	}

	@SideOnly(Side.CLIENT)
	override def registerIcons(register: IIconRegister) {
		super.registerIcons(register)

		for(runeType <- OldenLanguageRegistry.runeTypes) {
			runeType.registerIcons((iconStr: String) => register.registerIcon(iconStr))
		}
	}

	override def getMaxItemUseDuration(stack: ItemStack) = 72000
	override def onItemRightClick(stack: ItemStack, world: World, player: EntityPlayer) = {
		// TODO: check for magical power / focus
		player.setItemInUse(stack, getMaxItemUseDuration(stack))

		stack
	}
	override def onPlayerStoppedUsing(stack: ItemStack, world: World, player: EntityPlayer, oppDur: Int) {
		try {
			val spellStr = Util.checkNull(ItemUtil.compound(stack).getString("spell"), "")
			val context = RootContext.create
			val parsedRunes = TextParser.parse(spellStr)
			val runes = parsedRunes.map(_(context))
			val parser = new RuneParser
			parser.handle(runes)
			parser.finish
			val spell = parser.spell

			val caster = new PlayerCaster(player)
			caster.cast(spell)
		} catch {
			case e: Exception =>
				println(e)
				println(e.getStackTraceString)
		}
	}
}
