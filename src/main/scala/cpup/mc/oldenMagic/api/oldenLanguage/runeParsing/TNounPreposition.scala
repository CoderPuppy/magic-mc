package cpup.mc.oldenMagic.api.oldenLanguage.runeParsing

trait TNounPreposition {
	def createNounModifier(targetPath: List[TNoun]): TNounModifier
}