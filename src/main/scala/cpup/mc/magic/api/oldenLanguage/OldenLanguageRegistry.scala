package cpup.mc.magic.api.oldenLanguage

/**
 * Created by cpup on 3/22/14.
 */
object OldenLanguageRegistry {
	protected var _runeTypes = Set[TRuneType]()
	def runeTypes = _runeTypes

	def registerRune(runeType: TRuneType) {
		_runeTypes += runeType
	}

	protected var _rootContextTransformers = List[(TContext) => Unit]()
	def rootContextTransformer = _rootContextTransformers

	def registerRootContextTransformer(transformer: (TContext) => Unit) {
		_rootContextTransformers ++= List(transformer)
	}
}
