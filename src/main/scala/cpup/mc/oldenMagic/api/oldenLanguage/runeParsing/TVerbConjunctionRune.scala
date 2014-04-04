package cpup.mc.oldenMagic.api.oldenLanguage.runeParsing

import cpup.mc.oldenMagic.api.oldenLanguage.runes.TRune

trait TVerbConjunctionRune extends TRune {
	def combineVerbs(a: TVerbRune, b: TVerbRune): TVerbRune
}