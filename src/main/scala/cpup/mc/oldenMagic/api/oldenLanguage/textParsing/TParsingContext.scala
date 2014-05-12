package cpup.mc.oldenMagic.api.oldenLanguage.textParsing

trait TParsingContext {
	def transform(name: String): TTransform
	def subContext(name: String): TParsingContext
}