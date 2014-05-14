package cpup.mc.oldenMagic.api.oldenLanguage.casting.conditions

trait TConditionType {
	def conditionClass: Class[_ <: TCondition]
}