package cpup.mc.magic.api.oldenLanguage.textParsing

object TextParser {
	def parse(str: String) = str.split(" ").filter(_.length > 0).map(parseRune(_)).toList

	def parseRune(str: String) = {
		if(str.contains("!")) {
			val transformName = str.substring(0, str.indexOf('!'))
			new ParsedRune(transformName, str.substring(str.indexOf('!') + 1))
		} else {
			new ParsedRune(str)
		}
	}
}