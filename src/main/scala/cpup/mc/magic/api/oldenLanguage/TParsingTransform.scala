package cpup.mc.magic.api.oldenLanguage

trait TParsingTransform extends TTransform {
	def transform(context: TContext, rune: TextRune) = transform(context, Parser.parseRune(rune.txt))
	def transform(context: TContext, rune: ParsedRune): TRune
}