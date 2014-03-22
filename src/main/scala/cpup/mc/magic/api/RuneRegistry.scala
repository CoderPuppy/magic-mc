package cpup.mc.magic.api

object RuneRegistry {
	protected var _runeTypes = Set[TRuneType]()
	def runeTypes = _runeTypes

	def register(runeType: TRuneType) {
		_runeTypes += runeType
	}
}