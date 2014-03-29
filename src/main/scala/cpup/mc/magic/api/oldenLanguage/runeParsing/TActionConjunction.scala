package cpup.mc.magic.api.oldenLanguage.runeParsing

trait TActionConjunction {
	def combineActions(a: TAction, b: TAction): TAction
}