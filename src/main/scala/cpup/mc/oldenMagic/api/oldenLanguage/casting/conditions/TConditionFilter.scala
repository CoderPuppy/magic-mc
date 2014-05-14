package cpup.mc.oldenMagic.api.oldenLanguage.casting.conditions

trait TConditionFilter {
	def conditionTypes: Set[TConditionType]
	def filter(conditions: Set[TCondition]): Boolean
}