package cpup.mc.magic.api.oldenLanguage.parsing

/**
 * Created by cpup on 3/23/14.
 */
trait TContext {
	def transform(name: String): TTransform
	def subContext(name: String): TContext
}
