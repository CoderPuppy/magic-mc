package cpup.mc.oldenMagic.api.oldenLanguage.textParsing

import cpup.mc.oldenMagic.api.oldenLanguage.runes.TRune

trait TTransform {
	def transform(context: TParsingContext, content: String): TRune
}