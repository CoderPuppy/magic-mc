package cpup.mc.magic.client.runeSelection

import net.minecraft.entity.EntityList
import cpup.mc.magic.api.oldenLanguage.RootContext

object RootCategory {
	def create = {
		val root = new Category(RootContext.create, "root")

		val entities = root.createSubCategory("entities")

		for(name <- EntityList.stringToClassMapping.keySet.toArray) {
			entities.addRune("tn!entity!" + name)
		}

		val stuff = root.createSubCategory("stuff")
		stuff.addRune("p!of")

		root
	}
}