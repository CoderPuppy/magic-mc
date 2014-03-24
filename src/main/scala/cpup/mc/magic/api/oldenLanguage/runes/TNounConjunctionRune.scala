package cpup.mc.magic.api.oldenLanguage.runes

trait TNounConjunctionRune extends TRune {
	def combineNouns(a: TNounRune, b: TNounRune): TNounRune
}