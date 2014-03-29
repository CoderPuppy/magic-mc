package cpup.mc.magic.api.oldenLanguage.runeParsing

trait TActionPreposition {
	def createActionModifier(targetPath: List[TNoun]): TActionModifier
}