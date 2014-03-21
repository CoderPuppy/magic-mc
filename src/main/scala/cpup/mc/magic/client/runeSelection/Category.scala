package cpup.mc.magic.client.runeSelection

class Category(val name: String) extends SelectionOption {
	protected var _parent: Category = null
	def parent = _parent

	protected var _scroll = 0
	def scroll = _scroll
	def scrollUp = {
		if(_scroll > 0) {
			_scroll -= 1
		}
		this
	}
	def scrollDown = {
		if(_scroll + 1 < options.size / 6) {
			_scroll += 1
		}
		this
	}

	protected var _options = List[SelectionOption]()
	def options = _options

	def addOption(option: SelectionOption) = {
		if(option.isInstanceOf[Category]) {
			val container = option.asInstanceOf[Category]
			if(container._parent == null) {
				container._parent = this
			} else {
				throw new Exception("Attempt to reparent a container")
			}
		}

		_options ::= option

		this
	}

	def apply(index: Int) = options(index + scroll * 6)
}