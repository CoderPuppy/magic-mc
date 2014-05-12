package cpup.mc.oldenMagic.client.runeSelection

import cpup.mc.oldenMagic.api.oldenLanguage.textParsing.{ParsedRune, TParsingContext, TextParser}

class RuneOption(val context: TParsingContext, val parsedRune: ParsedRune) extends SelectionOption {
	val rune = parsedRune(context)

	def this(context: TParsingContext, rune: String) {
		this(context, TextParser.parseRune(rune))
	}
}