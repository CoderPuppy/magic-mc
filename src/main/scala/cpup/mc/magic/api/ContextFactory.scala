package cpup.mc.magic.api

object ContextFactory {
	def create: TContext = {
		val context = new Context
		context
	}
}