package cpup.mc.magic.api.oldenLanguage.textParsing


class SubContextTransform(val name: String) extends TTransform with TParsingTransform {
	def transform(context: TContext, rune: ParsedRune) = rune(context.subContext(name))
}