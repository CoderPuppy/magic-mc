package cpup.mc.magic.api.oldenLanguage

import cpup.mc.magic.content.runes.TextRune

case class ParsedRune(val transformName: String, val content: String) {
	def this(content: String) {
		this(null, content)
	}

	def apply(context: TContext) = if(transformName == null) { TextRune(content) }
	else {
		val transform = context.transform(transformName).asInstanceOf[TTransform[Any]]
		val rune = TextRune(content)
		if(transform == null) {
			throw new InvalidTransformException("No such transform: " + transformName)
		} else {
			val inter = transform.isValid(context, rune)
			if(inter.isDefined) {
				transform.transform(context, TextRune(content), inter.get)
			} else {
				throw new InvalidTransformException("Rune: " + rune.toString + " is not valid for transform: " + transformName)
			}
		}
	}
}