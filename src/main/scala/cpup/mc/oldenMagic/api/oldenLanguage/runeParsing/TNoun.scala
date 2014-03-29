package cpup.mc.oldenMagic.api.oldenLanguage.runeParsing

import cpup.mc.oldenMagic.api.oldenLanguage.casting.{TTarget, TCaster}

trait TNoun {
	def getTargets(caster: TCaster, existing: List[TTarget]): List[TTarget]
}