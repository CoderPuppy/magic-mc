package cpup.mc.oldenMagic.content

import net.minecraft.item.ItemStack

class ItemKnife extends TItemBase {
	override def hasContainerItem = true
	override def doesContainerItemLeaveCraftingGrid(stack: ItemStack) = false
	override def getContainerItem(stack: ItemStack) = {
		val stack2 = stack.copy
		stack2.stackSize = 1
		stack2.setItemDamage(stack.getItemDamage + 1)
		stack2
	}
}