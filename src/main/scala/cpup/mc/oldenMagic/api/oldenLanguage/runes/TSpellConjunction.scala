package cpup.mc.oldenMagic.api.oldenLanguage.runes

import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.Spell

trait TSpellConjunction {
	def combineSpells(a: Spell, b: Spell): Spell
}