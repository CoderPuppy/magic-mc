package cpup.mc.magic.client.runeSelection

import cpup.mc.magic.api.oldenLanguage.textParsing.{ParsedRune, TContext, TextParser}

class RuneOption(val context: TContext, val parsedRune: ParsedRune) extends SelectionOption {
	val rune = parsedRune(context)

	def this(context: TContext, rune: String) {
		this(context, TextParser.parseRune(rune))
	}
}