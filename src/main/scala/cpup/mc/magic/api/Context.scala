package cpup.mc.magic.api

import scala.collection.mutable

trait TContext {
	def transform(name: String): TTransform
	def subContext(name: String): TContext
}
class Context extends TContext {
	val transforms = new mutable.HashMap[String, TTransform]
	def transform(name: String) = transforms.getOrElse(name, null)

	val subContexts = new mutable.HashMap[String, TContext]
	def subContext(name: String) = subContexts.getOrElse(name, null)
}