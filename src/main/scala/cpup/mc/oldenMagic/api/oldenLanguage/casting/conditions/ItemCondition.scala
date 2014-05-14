package cpup.mc.oldenMagic.api.oldenLanguage.casting.conditions

import net.minecraft.item.ItemStack

case class ItemCondition(name: Symbol, stack: ItemStack) extends TCondition {
	override def conditionType = ItemCondition
}

object ItemCondition extends TConditionType {
	override def conditionClass = classOf[ItemCondition]
}