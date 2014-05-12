package cpup.mc.oldenMagic.api.oldenLanguage.textParsing

import cpup.mc.oldenMagic.api.oldenLanguage.runes.TRune

trait TParsingTransform extends TTransform {
	def transform(context: TContext, content: String) = transform(context, TextParser.parseRune(content))
	def transform(context: TContext, rune: ParsedRune): TRune
}