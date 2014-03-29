package cpup.mc.magic.api.oldenLanguage.runeParsing

trait TNounConjunction {
	def combineNouns(a: TNoun, b: TNoun): TNoun
}