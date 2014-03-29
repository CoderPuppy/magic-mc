package cpup.mc.magic.api.oldenLanguage.runeParsing

trait TNounPreposition {
	def createNounModifier(targetPath: List[TNoun]): TNounModifier
}