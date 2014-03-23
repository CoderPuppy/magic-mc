package cpup.mc.magic.api.oldenLanguage.runeParsing

import cpup.mc.magic.api.oldenLanguage.TRune
import cpup.mc.magic.MagicMod

// TODO: limit what runes a player can use
class RuneParser {
	def mod = MagicMod

	def handle(runes: List[TRune]) { runes.foreach(handle(_)) }

	def handle(rune: TRune) {
		rune match {
			case _ =>
				mod.logger.warn("What is this rune? " + rune.toString)
		}
	}
}