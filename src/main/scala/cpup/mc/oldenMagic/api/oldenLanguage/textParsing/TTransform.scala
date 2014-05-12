package cpup.mc.oldenMagic.api.oldenLanguage.textParsing

import cpup.mc.oldenMagic.api.oldenLanguage.runes.TRune

trait TTransform {
	def transform(context: TContext, content: String): TRune
}