package cpup.mc.magic.api.oldenLanguage.runeParsing

import cpup.mc.magic.api.oldenLanguage.casting.{TTarget, TCaster}

trait TNoun {
	def getTargets(caster: TCaster, existing: List[TTarget]): List[TTarget]
}