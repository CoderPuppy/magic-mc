package cpup.mc.oldenMagic.api.oldenLanguage.runeParsing

import cpup.mc.oldenMagic.api.oldenLanguage.runes.TRune

trait TNounModifierRune extends TRune {
	def modifyNoun(rune: TNounRune)
}