package cpup.mc.magic.api.oldenLanguage

object Parser {
	def parse(str: String) = str.split(" ").map(parseRune(_))

	def parseRune(str: String) = {
		if(str.contains("!")) {
			val transformName = str.substring(0, str.indexOf('!'))
			new ParsedRune(transformName, str.substring(str.indexOf('!') + 1))
		} else {
			new ParsedRune(str)
		}
	}
}