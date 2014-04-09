package cpup.mc.oldenMagic.api.oldenLanguage.runeParsing

import cpup.mc.oldenMagic.api.oldenLanguage.casting.{CastingContext, TTarget, TCaster}
import cpup.mc.oldenMagic.api.oldenLanguage.runes.TRune

trait TNounRune extends TRune {
	def getTargets(context: CastingContext, existing: List[TTarget]): List[TTarget]
	def filter(context: CastingContext, prev: List[TNounRune], targets: List[TTarget]): List[TTarget]
}