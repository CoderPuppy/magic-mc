package cpup.mc.oldenMagic.api.oldenLanguage.runeParsing

import cpup.mc.oldenMagic.api.oldenLanguage.casting.CastingContext
import cpup.mc.oldenMagic.api.oldenLanguage.runes.TRune
import cpup.mc.lib.targeting.TTarget

trait TNounRune extends TRune {
	def getTargets(context: CastingContext, existing: List[TTarget]): List[TTarget]
	def filter(context: CastingContext, prev: List[TNounRune], target: TTarget): Boolean
}