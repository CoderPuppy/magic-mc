package cpup.mc.oldenMagic.api.oldenLanguage.runes

import cpup.mc.oldenMagic.api.oldenLanguage.textParsing.{TextRune, TContext, TTransform}

trait SingletonRune extends TRune with TRuneType with TTransform {
	def runeType = this
	def runeClass = getClass

	def transform(context: TContext, rune: TextRune) = this
}