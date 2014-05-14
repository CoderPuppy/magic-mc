package cpup.mc.oldenMagic.content.runes

import cpup.mc.oldenMagic.api.oldenLanguage.casting.conditions.{ItemCondition, TCondition, TConditionFilter}

class ItemConditionFilterRune(var name: Symbol, var unlocalizedName: String) extends TConditionFilter {
	override def conditionTypes = Set(ItemCondition)
	override def filter(conditions: Set[TCondition]) = conditions.exists {
		case cond: ItemCondition =>
			cond.name == name && cond.stack.getUnlocalizedName == unlocalizedName

		case _ => false
	}
}