package cpup.mc.oldenMagic.api.oldenLanguage.runeParsing

import cpup.mc.oldenMagic.api.oldenLanguage.runes.TRune

trait TNounConjunctionRune extends TRune {
	def combineNouns(a: List[TNounRune], b: List[TNounRune]): List[TNounRune]
}