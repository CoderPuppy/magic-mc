package cpup.mc.magic.client.runeSelection

import net.minecraft.entity.{Entity, EntityList}
import cpup.mc.magic.content.runes.RootContext
import java.util
import java.lang.reflect.Constructor
import net.minecraft.world.World

object RootCategory {
	def create = {
		val root = new Category(RootContext.create, "root")

		val entities = root.createSubCategory("entities")

		for(entry <- EntityList.stringToClassMapping.entrySet.toArray.asInstanceOf[Array[util.Map.Entry[String, Class[_ <: Entity]]]]) {
			if(entry.getValue.getConstructors.exists((constr: Constructor[_]) => {
				val types = constr.getTypeParameters
				types.length == 1 && types(0) == classOf[World]
			})) {
				entities.addRune("tn!entity!" + entry.getKey)
			}
		}

		val stuff = root.createSubCategory("stuff")
		stuff.addRune("p!of")

		root
	}
}