package cpup.mc.oldenMagic.api.oldenLanguage.casting

import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.{TTypeNounRune, Spell}
import scala.collection.immutable.HashMap
import net.minecraft.entity.Entity
import net.minecraft.block.Block
import cpup.mc.lib.targeting.TTarget

class CastingContext(val player: String, val caster: TCaster) {
	var it = List[TTarget]()
	var multiIt = new HashMap[TTypeNounRune[_ <: Entity, _ <: Block], TTarget]

	def cast(spell: Spell) {
		var targets = List[TTarget]()

		for(noun <- spell.targetPath) {
			targets = noun.getTargets(this, targets)
		}

		targets = targets.filter(_.isValid)

		it = targets

		spell.action.act(this, targets)
	}
}