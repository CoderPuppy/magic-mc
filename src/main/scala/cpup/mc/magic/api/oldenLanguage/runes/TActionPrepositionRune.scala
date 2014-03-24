package cpup.mc.magic.api.oldenLanguage.runes

trait TActionPrepositionRune extends TRune {
	def createPhrase(noun: TNounRune): TActionModifierRune
}