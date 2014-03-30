package cpup.mc.oldenMagic.api.oldenLanguage.runeParsing

import cpup.mc.oldenMagic.api.oldenLanguage.casting.{TTarget, TCaster}
import cpup.mc.oldenMagic.api.oldenLanguage.runes.TRune

trait TNounRune extends TRune {
	def getTargets(caster: TCaster, existing: List[TTarget]): List[TTarget]
}