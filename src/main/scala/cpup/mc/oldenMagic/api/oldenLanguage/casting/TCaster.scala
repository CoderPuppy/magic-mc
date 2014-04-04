package cpup.mc.oldenMagic.api.oldenLanguage.casting

import net.minecraft.util.MovingObjectPosition
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.Spell
import net.minecraft.nbt.NBTTagCompound

trait TCaster extends TTarget {
	def mop: MovingObjectPosition

	def cast(spell: Spell) {
		// TODO: reimplement
//		var targets = List[TTarget]()
//
//		for(noun <- spell.targetPath) {
//			targets = noun.getTargets(this, targets)
//		}
//
//		for(target <- targets) {
//			target.obj match {
//				case Left(entity) =>
//					spell.action.actUponEntity(entity)
//				case Right(pos) =>
//					spell.action.actUponBlock(pos)
//				case _ =>
//			}
//		}
	}
}