package cpup.mc.oldenMagic.api.oldenLanguage.runeParsing

import cpup.mc.oldenMagic.api.oldenLanguage.runes.TRune

trait TConditionPrepositionRune extends TRune {
	def createConditionModifier(condition: TConditionRune): TConditionModifierRune
}