package cpup.mc.magic.api.oldenLanguage

object OldenLanguageRegistry {
	protected var _runeTypes = Set[TRuneType]()
	def runeTypes = _runeTypes

	def registerRune(runeType: TRuneType) {
		_runeTypes += runeType
	}

	protected var _rootContextTransformers = List[(Context) => Unit]()
	def rootContextTransformers = _rootContextTransformers

	def registerRootContextTransformer(transformer: (Context) => Unit) {
		_rootContextTransformers ++= List(transformer)
	}
}
