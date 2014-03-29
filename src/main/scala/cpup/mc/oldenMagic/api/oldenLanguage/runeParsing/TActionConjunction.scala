package cpup.mc.oldenMagic.api.oldenLanguage.runeParsing

trait TActionConjunction {
	def combineActions(a: TAction, b: TAction): TAction
}