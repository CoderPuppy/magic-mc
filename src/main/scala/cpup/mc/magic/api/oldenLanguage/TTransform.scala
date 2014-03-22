package cpup.mc.magic.api.oldenLanguage

/**
 * Created by cpup on 3/22/14.
 */
trait TTransform {
	def isValid(rune: TRune): Boolean
	def transform(context: TContext, rune: TRune): TRune
}
