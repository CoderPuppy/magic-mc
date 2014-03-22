package cpup.mc.magic.api.oldenLanguage

import cpup.mc.magic.content.runes.TextRune

class SubContextTransform(val name: String) extends TTransform[ParsedRune] {
	def isValid(context: TContext, rune: TRune) = rune match {
		case TextRune(text) =>
			val parsedRune = Parser.parseRune(text)
			Option(context.subContext(name)).map(_.transform(parsedRune.transformName)).map((transform: TTransform[_]) => parsedRune)
		case _ => None
	}

	def transform(context: TContext, rune: TRune, inter: ParsedRune) = inter(context.subContext(name))
}