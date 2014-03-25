package cpup.mc.magic.api.oldenLanguage.textParsing

import cpup.mc.magic.api.oldenLanguage.runes.TRune

trait TTransform {
	def transform(context: TContext, rune: TextRune): TRune
}