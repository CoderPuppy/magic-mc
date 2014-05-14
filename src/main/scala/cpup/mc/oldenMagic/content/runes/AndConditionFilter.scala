package cpup.mc.oldenMagic.content.runes

import cpup.mc.oldenMagic.api.oldenLanguage.casting.conditions.{TCondition, TConditionFilter}

class AndConditionFilter(val a: TConditionFilter, val b: TConditionFilter) extends TConditionFilter {
	override def conditionTypes = a.conditionTypes union b.conditionTypes
	override def filter(conditions: Set[TCondition]) = a.filter(conditions) && b.filter(conditions)
}