package cpup.mc.oldenMagic.api.oldenLanguage.casting.conditions

trait TConditionFilterCombinator {
	def combine(a: TConditionFilter, b: TConditionFilter): TConditionFilter
}