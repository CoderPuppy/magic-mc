package cpup.mc.magic.api.oldenLanguage

object RootContext {
	def create: TContext = {
		val context = new Context
		for(rootContextTransformer <- OldenLanguageRegistry.rootContextTransformers) {
			rootContextTransformer(context)
		}
		context
	}
}