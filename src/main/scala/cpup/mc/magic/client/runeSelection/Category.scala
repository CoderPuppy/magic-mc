package cpup.mc.magic.client.runeSelection

import cpup.mc.magic.api.oldenLanguage.TContext

class Category(val context: TContext, val name: String) extends SelectionOption {
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
			} else if(container._parent != this) {
				throw new Exception("Attempt to reparent a container")
			}
		}

		_options ++= List(option)

		this
	}

	def apply(index: Int) = if(index + scroll * 6 < options.size) {
		options(index + scroll * 6)
	} else { null }

	def createSubCategory(name: String) = {
		val category = new Category(context, name)
		addOption(category)
		category
	}

	def addRune(rune: String) = {
		try {
			val runeOpt = new RuneOption(context, rune)
			addOption(runeOpt)
			runeOpt
		} catch {
			case e: Exception => {
				println(e)
				null
			}
		}
	}
}