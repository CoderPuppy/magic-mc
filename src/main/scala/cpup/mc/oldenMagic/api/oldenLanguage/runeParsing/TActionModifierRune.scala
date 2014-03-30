package cpup.mc.oldenMagic.api.oldenLanguage.runeParsing

import cpup.mc.oldenMagic.api.oldenLanguage.runes.TRune

trait TActionModifierRune extends TRune {
	def modifyAction(rune: TActionRune)
}