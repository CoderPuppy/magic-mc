package cpup.mc.oldenMagic.api.oldenLanguage.casting

import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.Spell

class CastingContext(val player: String, val caster: TCaster) {
	var it: TTarget = null

	def cast(spell: Spell) {
		var targets = List[TTarget]()

		for(noun <- spell.targetPath) {
			targets = noun.getTargets(this, targets)
		}

		spell.action.act(this, targets)
	}
}