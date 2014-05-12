package cpup.mc.oldenMagic.api.oldenLanguage.textParsing

import cpup.mc.oldenMagic.api.oldenLanguage.OldenLanguageRegistry

object RootParsingContext {
	 def create: TParsingContext = {
		 val context = new ParsingContext
		 for(rootContextTransformer <- OldenLanguageRegistry.rootParsingContextTransformers) {
			 rootContextTransformer(context)
		 }
		 context
	 }
 }