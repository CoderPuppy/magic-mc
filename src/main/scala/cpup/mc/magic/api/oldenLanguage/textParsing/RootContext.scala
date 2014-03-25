package cpup.mc.magic.api.oldenLanguage.textParsing

import cpup.mc.magic.api.oldenLanguage.OldenLanguageRegistry

object RootContext {
	 def create: TContext = {
		 val context = new Context
		 for(rootContextTransformer <- OldenLanguageRegistry.rootContextTransformers) {
			 rootContextTransformer(context)
		 }
		 context
	 }
 }