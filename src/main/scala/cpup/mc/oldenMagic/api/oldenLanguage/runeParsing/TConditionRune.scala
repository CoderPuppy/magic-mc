package cpup.mc.oldenMagic.api.oldenLanguage.runeParsing

import cpup.mc.oldenMagic.api.oldenLanguage.runes.TRune
import cpup.mc.oldenMagic.api.oldenLanguage.casting.conditions.{TCondition, TConditionType}

trait TConditionRune extends TRune {
	def conditionTypes: Set[TConditionType]
	def filter(conditions: Set[TCondition]): Boolean
}