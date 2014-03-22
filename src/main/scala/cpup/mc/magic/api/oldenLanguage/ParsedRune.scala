package cpup.mc.magic.api.oldenLanguage

case class ParsedRune(val transformName: String, val content: String) {
	def this(content: String) {
		this(null, content)
	}

	def serialize = if(transformName == null) {
		content
	} else {
		transformName + "!" + content
	}

	def apply(context: TContext) = if(transformName == null) { TextRune(content) }
	else {
		val transform = context.transform(transformName)
		if(transform == null) {
			throw new InvalidTransformException("No such transform: " + transformName)
		} else {
			transform.transform(context, TextRune(content))
		}
	}
}