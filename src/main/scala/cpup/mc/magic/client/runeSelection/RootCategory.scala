package cpup.mc.magic.client.runeSelection

import net.minecraft.entity.EntityList

object RootCategory {
	def create = {
		val root = new Category("root")

		val entities = new Category("entities")

		for(name <- EntityList.stringToClassMapping.keySet.toArray) {
			entities.addOption(RuneOption("tn!entity!" + name))
		}

		root.addOption(entities)

		val stuff = new Category("stuff")
		stuff.addOption(RuneOption("p!of"))
		root.addOption(stuff)

		root
	}
}