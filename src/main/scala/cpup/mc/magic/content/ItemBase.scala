package cpup.mc.magic.content

import cpup.mc.lib.content.CPupItem
import cpup.mc.magic.{MagicMod, TMagicMod}

trait TItemBase extends CPupItem[TMagicMod] {
	def mod = MagicMod
}
class ItemBase extends TItemBase
