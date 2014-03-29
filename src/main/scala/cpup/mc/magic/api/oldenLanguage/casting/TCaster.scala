package cpup.mc.magic.api.oldenLanguage.casting

import net.minecraft.util.MovingObjectPosition
import cpup.mc.magic.api.oldenLanguage.runeParsing.Spell

trait TCaster extends TTarget {
	def mop: MovingObjectPosition

	def cast(spell: Spell) {
		var targets = List[TTarget]()

		for(noun <- spell.targetPath) {
			targets = noun.getTargets(this, targets)
		}

		for(target <- targets) {
			target match {
				case BlockTarget(pos) =>
					spell.action.actUponBlock(pos)
				case EntityTarget(entity) =>
					spell.action.actUponEntity(entity)
				case _ =>
			}
		}
	}
}