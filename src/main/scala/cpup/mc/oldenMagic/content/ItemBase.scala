package cpup.mc.oldenMagic.content

import cpup.mc.lib.content.CPupItem
import cpup.mc.oldenMagic.{OldenMagicMod, TOldenMagicMod}

trait TItemBase extends CPupItem[TOldenMagicMod] {
	def mod = OldenMagicMod
}
class ItemBase extends TItemBase
