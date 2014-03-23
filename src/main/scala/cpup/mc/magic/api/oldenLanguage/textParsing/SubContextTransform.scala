package cpup.mc.magic.api.oldenLanguage.textParsing

import cpup.mc.magic.api.oldenLanguage.textParsing.ParsedRune

class SubContextTransform(val name: String) extends TTransform with TParsingTransform {
	def transform(context: TContext, rune: ParsedRune) = rune(context.subContext(name))
}