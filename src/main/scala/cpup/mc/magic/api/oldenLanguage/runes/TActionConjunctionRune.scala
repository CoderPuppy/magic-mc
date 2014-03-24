package cpup.mc.magic.api.oldenLanguage.runes

trait TActionConjunctionRune extends TRune {
	def combineActions(a: TActionRune, b: TActionRune): TActionRune
}