package cpup.mc.magic.api.oldenLanguage

import cpup.mc.magic.api.oldenLanguage.parsing.{TTransform, ParsedRune, TContext, TParsingTransform}

class SubContextTransform(val name: String) extends TTransform with TParsingTransform {
	def transform(context: TContext, rune: ParsedRune) = rune(context.subContext(name))
}