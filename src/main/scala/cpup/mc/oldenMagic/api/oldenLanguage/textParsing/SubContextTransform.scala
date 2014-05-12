package cpup.mc.oldenMagic.api.oldenLanguage.textParsing


class SubContextTransform(val name: String) extends TTransform with TParsingTransform {
	def transform(context: TParsingContext, rune: ParsedRune) = rune(context.subContext(name))
}