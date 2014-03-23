package cpup.mc.magic.api.oldenLanguage.runeParsing

import cpup.mc.magic.MagicMod
import cpup.mc.magic.api.oldenLanguage.runes.{TActionRune, TRune}

// TODO: limit what runes a player can use
class RuneParser {
	def mod = MagicMod

	var mode: RuneParserMode = ActionMode
	var action: TActionRune = null

	def handle(rune: TRune) {
		mode match {
			case ActionMode =>
				rune match {
					case newAct: TActionRune =>
						action = newAct
						mode = ActionModifierRune
					case _ =>
						unhandledRune(rune)
				}
			case _ =>
				throw new RuntimeException("Unknown mode: " + mode.toString)
		}
	}

	override def toString = "{ " + List("action = " + action).mkString(", ") + " }"

	def unhandledRune(rune: TRune) {
		mod.logger.warn("Unhandled rune: " + rune.toString)
		// TODO: instability
	}

	def handle(runes: List[TRune]) { runes.foreach(handle(_)) }
}

trait RuneParserMode {}
case object ActionMode extends RuneParserMode
case object ActionModifierMode extends RuneParserMode
case object NounModifierMode extends RuneParserMode