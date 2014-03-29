package cpup.mc.oldenMagic.api.oldenLanguage.textParsing

import scala.collection.mutable

class Context extends TContext {
	val transforms = new mutable.HashMap[String, TTransform]
	def transform(name: String) = transforms(name)

	val subContexts = new mutable.HashMap[String, TContext]
	def subContext(name: String) = subContexts(name)
}