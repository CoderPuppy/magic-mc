package cpup.mc.magic.client.runeSelection

object RootCategory {
	def create = {
		val root = new Category("root")

		root.addOption(RuneOption("p!of"))

		root
	}
}