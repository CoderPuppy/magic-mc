package cpup.mc.oldenMagic.api.oldenLanguage.casting

import cpup.mc.oldenMagic.api.oldenLanguage.runes.TRuneType

trait TAction {
	def affectedTarget: TTarget
	def runeType: TRuneType
}