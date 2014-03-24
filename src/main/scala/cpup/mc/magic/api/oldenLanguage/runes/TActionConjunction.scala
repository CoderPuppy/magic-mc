package cpup.mc.magic.api.oldenLanguage.runes

trait TActionConjunction {
	def combineActions(a: TAction, b: TAction): TAction
}