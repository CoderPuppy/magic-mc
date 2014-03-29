package cpup.mc.oldenMagic.content

import cpup.mc.lib.content.CPupItem
import cpup.mc.oldenMagic.{MagicMod, TMagicMod}

trait TItemBase extends CPupItem[TMagicMod] {
	def mod = MagicMod
}
class ItemBase extends TItemBase
