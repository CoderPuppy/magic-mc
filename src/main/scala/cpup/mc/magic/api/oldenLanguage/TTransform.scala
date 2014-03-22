package cpup.mc.magic.api.oldenLanguage

import cpup.mc.magic.content.runes.TextRune

trait TTransform {
	def transform(context: TContext, rune: TextRune): TRune
}