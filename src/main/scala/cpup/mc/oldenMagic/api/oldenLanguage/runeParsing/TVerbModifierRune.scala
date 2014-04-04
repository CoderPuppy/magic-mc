package cpup.mc.oldenMagic.api.oldenLanguage.runeParsing

import cpup.mc.oldenMagic.api.oldenLanguage.runes.TRune

trait TVerbModifierRune extends TRune {
	def modifyVerb(rune: TVerbRune)
}