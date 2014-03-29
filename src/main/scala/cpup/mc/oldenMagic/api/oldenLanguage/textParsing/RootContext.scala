package cpup.mc.oldenMagic.api.oldenLanguage.textParsing

import cpup.mc.oldenMagic.api.oldenLanguage.OldenLanguageRegistry

object RootContext {
	 def create: TContext = {
		 val context = new Context
		 for(rootContextTransformer <- OldenLanguageRegistry.rootContextTransformers) {
			 rootContextTransformer(context)
		 }
		 context
	 }
 }