package cpup.mc.oldenMagic.api.oldenLanguage.casting

trait TCancellableAction extends TAction {
	def cancel
	def uncancel
	def isCanceled: Boolean
}