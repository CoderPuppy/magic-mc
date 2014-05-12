package cpup.mc.oldenMagic.api.oldenLanguage.textParsing

import scala.collection.mutable

class ParsingContext extends TParsingContext {
	val transforms = new mutable.HashMap[String, TTransform]
	def transform(name: String) = transforms(name)

	val subContexts = new mutable.HashMap[String, TParsingContext]
	def subContext(name: String) = subContexts(name)
}