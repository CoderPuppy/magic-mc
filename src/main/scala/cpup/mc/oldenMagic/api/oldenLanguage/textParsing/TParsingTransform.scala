package cpup.mc.oldenMagic.api.oldenLanguage.textParsing

import cpup.mc.oldenMagic.api.oldenLanguage.runes.TRune

trait TParsingTransform extends TTransform {
	def transform(context: TParsingContext, content: String) = transform(context, TextParser.parseRune(content))
	def transform(context: TParsingContext, rune: ParsedRune): TRune
}