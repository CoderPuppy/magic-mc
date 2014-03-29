package cpup.mc.oldenMagic.api.oldenLanguage.textParsing

import cpup.mc.oldenMagic.api.oldenLanguage.runes.TRune

trait TParsingTransform extends TTransform {
	def transform(context: TContext, rune: TextRune) = transform(context, TextParser.parseRune(rune.text))
	def transform(context: TContext, rune: ParsedRune): TRune
}