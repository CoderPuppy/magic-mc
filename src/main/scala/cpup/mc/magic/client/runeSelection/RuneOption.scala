package cpup.mc.magic.client.runeSelection

import cpup.mc.magic.api.oldenLanguage.parsing.{ParsedRune, TContext, Parser}

class RuneOption(val context: TContext, val parsedRune: ParsedRune) extends SelectionOption {
	val rune = parsedRune(context)

	def this(context: TContext, rune: String) {
		this(context, Parser.parseRune(rune))
	}
}