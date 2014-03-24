package cpup.mc.magic.api.oldenLanguage.runes

trait TActionConjunction {
	def combineActions(a: TActionRune, b: TActionRune): TActionRune
}