package cpup.mc.oldenMagic.api.oldenLanguage.runeParsing

import cpup.mc.oldenMagic.api.oldenLanguage.runes.TRune

trait TActionConjunctionRune extends TRune {
	def combineActions(a: TActionRune, b: TActionRune): TActionRune
}