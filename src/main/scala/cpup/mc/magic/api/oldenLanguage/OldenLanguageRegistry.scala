package cpup.mc.magic.api.oldenLanguage

import cpup.mc.magic.api.oldenLanguage.textParsing.Context
import cpup.mc.magic.api.oldenLanguage.runes.TRuneType

object OldenLanguageRegistry {
	protected var _runeTypes = List[TRuneType]()
	def runeTypes = _runeTypes

	def registerRune(runeType: TRuneType) {
		_runeTypes ++= List(runeType)
	}

	protected var _rootContextTransformers = List[(Context) => Unit]()
	def rootContextTransformers = _rootContextTransformers

	def registerRootContextTransformer(transformer: (Context) => Unit) {
		_rootContextTransformers ++= List(transformer)
	}

	protected[magic] def finish {
		_runeTypes = _runeTypes.sortBy((runeType) => {
			runeType.runeClass.getCanonicalName + ":" + runeType.getClass.getCanonicalName + ":" + runeType.name
		})
	}
}
