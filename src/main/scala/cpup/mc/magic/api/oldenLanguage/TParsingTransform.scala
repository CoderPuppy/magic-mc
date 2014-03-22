package cpup.mc.magic.api.oldenLanguage

import cpup.mc.magic.content.runes.TextRune

trait TParsingTransform extends TTransform {
	def transform(context: TContext, rune: TextRune) = transform(context, Parser.parseRune(rune.txt))
	def transform(context: TContext, rune: ParsedRune): TRune
}