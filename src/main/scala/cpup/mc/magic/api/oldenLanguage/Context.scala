package cpup.mc.magic.api.oldenLanguage

import scala.collection.mutable

class Context extends TContext {
	val transforms = new mutable.HashMap[String, TTransform[_]]
	def transform(name: String) = transforms.getOrElse(name, null)

	val subContexts = new mutable.HashMap[String, TContext]
	def subContext(name: String) = subContexts.getOrElse(name, null)
}