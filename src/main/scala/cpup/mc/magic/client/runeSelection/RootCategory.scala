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

		entities.addRune("tn!entity!Cow")

//		for(name <- EntityList.stringToClassMapping.keySet.toArray) {
////			try {
//				entities.addRune("tn!entity!" + name)
////			} catch {
////				case e: NullPointerException if e.getMessage == "No constructor: " + name + "(World)" =>
////					println(e)
////				case e: Throwable =>
////					throw e
////			}
//		}

		val stuff = root.createSubCategory("stuff")
		stuff.addRune("p!of")

		root
	}
}