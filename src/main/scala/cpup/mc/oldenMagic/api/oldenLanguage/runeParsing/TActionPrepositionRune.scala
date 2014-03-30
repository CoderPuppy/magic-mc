package cpup.mc.oldenMagic.api.oldenLanguage.runeParsing

import cpup.mc.oldenMagic.api.oldenLanguage.runes.TRune

trait TActionPrepositionRune extends TRune {
	def createActionModifier(targetPath: List[TNounRune]): TActionModifierRune
}