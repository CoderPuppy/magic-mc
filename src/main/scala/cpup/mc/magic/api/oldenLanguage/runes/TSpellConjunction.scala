package cpup.mc.magic.api.oldenLanguage.runes

import cpup.mc.magic.api.oldenLanguage.runeParsing.Spell

trait TSpellConjunction {
	def combineSpells(a: Spell, b: Spell): Spell
}