package cpup.mc.magic.api.oldenLanguage

import cpup.mc.magic.content.runes.TextRune

/**
 * Created by cpup on 3/22/14.
 */
object Parser {
	def parse(context: TContext, str: String) = str.split(" ").map(parseRune(context, _))

	def parseRune(context: TContext, str: String) = {
		if(str.contains("!")) {
			val transformStr = str.substring(0, str.indexOf('!'))
			val transform = context.transform(transformStr)
			var rune: TRune = TextRune(str.substring(str.indexOf('!') + 1))
			if(transform == null) {
				throw new InvalidTransformException("No such transform: " + transformStr)
			} else {
				if(transform.isValid(rune)) {
					rune = transform.transform(context, rune)
				} else {
					throw new InvalidTransformException("Rune: " + rune.toString + " is not valid for transform: " + transformStr)
				}
			}
		} else {
			TextRune(str)
		}
	}
}
