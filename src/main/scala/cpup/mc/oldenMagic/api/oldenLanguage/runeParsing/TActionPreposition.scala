package cpup.mc.oldenMagic.api.oldenLanguage.runeParsing

trait TActionPreposition {
	def createActionModifier(targetPath: List[TNoun]): TActionModifier
}