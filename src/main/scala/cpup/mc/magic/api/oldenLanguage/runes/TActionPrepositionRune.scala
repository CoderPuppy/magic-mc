package cpup.mc.magic.api.oldenLanguage.runes

trait TActionPrepositionRune extends TRune {
	def createActionModifier(noun: TNounRune): TActionModifierRune
}