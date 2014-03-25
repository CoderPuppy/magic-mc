package cpup.mc.magic.api.oldenLanguage.runes

import cpup.mc.magic.api.oldenLanguage.textParsing.{TextRune, TContext, TTransform}

trait SingletonRune extends TRune with TRuneType with TTransform {
	def runeType = this
	def runeClass = getClass

	def transform(context: TContext, rune: TextRune) = this
}