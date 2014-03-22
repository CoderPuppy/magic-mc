package cpup.mc.magic.api.oldenLanguage

/**
 * Created by cpup on 3/22/14.
 */
trait TContext {
	def transform(name: String): TTransform
	def subContext(name: String): TContext
}
