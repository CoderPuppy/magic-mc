package cpup.mc.oldenMagic.api.oldenLanguage.casting

import scala.collection.mutable
import cpup.mc.lib.targeting.TTarget

object CastingRegistry {
	private val wrappers = new mutable.HashMap[Class[TTarget], TTarget => TCaster]

	def register[T <: TTarget](cla: Class[T], wrapper: T => TCaster) {
		wrappers(cla.asInstanceOf[Class[TTarget]]) = wrapper.asInstanceOf[TTarget => TCaster]
	}

	def wrap(target: TTarget): Option[TCaster] = {
		var cla = target.getClass.asInstanceOf[Class[TTarget]]
		while(cla.getInterfaces.contains(classOf[TTarget])) {
			if(wrappers.contains(cla)) {
				return Some(wrappers(cla)(target))
			} else if(cla.getSuperclass == null) {
				return None
			} else {
				cla = cla.getSuperclass.asInstanceOf[Class[TTarget]]
			}
		}
		return None
	}
}