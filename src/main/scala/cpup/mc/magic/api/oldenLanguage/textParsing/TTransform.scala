package cpup.mc.magic.api.oldenLanguage.textParsing

import cpup.mc.magic.api.oldenLanguage.TRune

/**
 * Created by cpup on 3/23/14.
 */
trait TTransform {
	def transform(context: TContext, rune: TextRune): TRune
}
