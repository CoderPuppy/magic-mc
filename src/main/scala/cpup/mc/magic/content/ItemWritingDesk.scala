package cpup.mc.magic.content

import net.minecraft.item.ItemBlock
import net.minecraft.block.Block

class ItemWritingDesk(_block: Block) extends ItemBlock(_block) with TItemBase {
	if(!_block.isInstanceOf[BlockWritingDesk]) {
		throw new Exception("Attempt to use a non-writing desk for ItemWritingDesk")
	}
	val block = _block.asInstanceOf[BlockWritingDesk]
}