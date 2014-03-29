package cpup.mc.magic.api.oldenLanguage.runeParsing

trait TNounConjunction {
	def combineNouns(a: List[TNoun], b: List[TNoun]): List[TNoun]
}