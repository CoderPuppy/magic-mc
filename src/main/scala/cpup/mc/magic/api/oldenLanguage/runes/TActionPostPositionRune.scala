package cpup.mc.magic.api.oldenLanguage.runes

trait TActionPostPositionRune extends TRune {
	def createActionModifier(noun: TNounRune): TActionModifierRune
}