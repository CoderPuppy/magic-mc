package cpup.mc.oldenMagic.api.oldenLanguage.textParsing

trait TContext {
	def transform(name: String): TTransform
	def subContext(name: String): TContext
}