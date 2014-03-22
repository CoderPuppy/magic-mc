package cpup.mc.magic.content.runes

import cpup.mc.magic.api.oldenLanguage.{OldenLanguageRegistry, Context, TContext}

object RootContext {
	def create: TContext = {
		val context = new Context
		for(rootContextTransformer <- OldenLanguageRegistry.rootContextTransformers) {
			rootContextTransformer(context)
		}
		context
	}
}