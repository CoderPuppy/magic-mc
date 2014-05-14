package cpup.mc.oldenMagic.api.oldenLanguage.runeParsing

import cpup.mc.oldenMagic.api.oldenLanguage.runes.TRune
import cpup.mc.oldenMagic.api.oldenLanguage.casting.conditions.{TConditionFilter, TConditionFilterCombinator}

trait TConditionCombinatorRune extends TRune with TConditionFilterCombinator {
	def combine(a: TConditionRune, b: TConditionRune): TConditionRune
	def combine(a: TConditionFilter, b: TConditionFilter) = (a, b) match {
		case (a: TConditionRune, b: TConditionRune) => combine(a, b)
		case _ => null
	}
}