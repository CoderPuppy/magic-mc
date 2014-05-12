package cpup.mc.oldenMagic.content

import net.minecraft.item.ItemStack
import net.minecraft.entity.player.EntityPlayer
import java.util
import cpup.lib.Util
import cpup.mc.lib.util.ItemUtil
import net.minecraft.client.renderer.texture.IIconRegister
import cpw.mods.fml.relauncher.{Side, SideOnly}
import cpup.mc.oldenMagic.api.oldenLanguage._
import cpup.mc.oldenMagic.api.oldenLanguage.textParsing.{TextParser, RootParsingContext}
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.RuneParser
import net.minecraft.world.World
import cpup.mc.oldenMagic.api.oldenLanguage.casting.CastingContext
import cpup.mc.oldenMagic.content.targets.PlayerCaster

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
			val context = RootParsingContext.create
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
			runeType.registerIcons(register)
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
			val transformingContext = RootParsingContext.create
			val parsedRunes = TextParser.parse(spellStr)
			val runes = parsedRunes.map(_(transformingContext))
			val parser = new RuneParser
			parser.handle(runes)
			parser.finish
			val spell = parser.spell

			val caster = new PlayerCaster(player.getCommandSenderName)
			val castingContext = new CastingContext(player.getCommandSenderName, caster)
			castingContext.cast(spell)
		} catch {
			case e: Exception =>
				mod.logger.error(e)
				mod.logger.error(e.getStackTraceString)
		}
	}
}
