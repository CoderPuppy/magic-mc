package cpup.mc.magic.api.oldenLanguage.parsing

import cpup.mc.magic.api.oldenLanguage.OldenLanguageRegistry

/**
 * Created by cpup on 3/23/14.
 */
object RootContext {
	 def create: TContext = {
		 val context = new Context
		 for(rootContextTransformer <- OldenLanguageRegistry.rootContextTransformers) {
			 rootContextTransformer(context)
		 }
		 context
	 }
 }
