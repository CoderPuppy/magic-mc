package cpup.mc.magic.api.oldenLanguage

trait TContext {
	def transform(name: String): TTransform[_]
	def subContext(name: String): TContext
}
