package cpup.mc.magic.api.impl

import cpup.mc.magic.api.{Context, TContext}

object ContextFactory {
	def create: TContext = {
		val context = new Context
		context
	}
}