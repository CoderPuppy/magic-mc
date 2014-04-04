package cpup.mc.oldenMagic.api.oldenLanguage.runeParsing

import cpup.mc.oldenMagic.api.oldenLanguage.runes.TRune

trait TVerbPrepositionRune extends TRune {
	def createVerbModifier(targetPath: List[TNounRune]): TVerbModifierRune
}