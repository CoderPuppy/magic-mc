package cpup.mc.magic.api.oldenLanguage

import cpup.mc.magic.api.oldenLanguage.textParsing.Context
import cpup.mc.magic.api.oldenLanguage.runes.TRuneType

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
