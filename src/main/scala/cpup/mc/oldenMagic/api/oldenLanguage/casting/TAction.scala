package cpup.mc.oldenMagic.api.oldenLanguage.casting

import cpup.mc.oldenMagic.api.oldenLanguage.runes.TRuneType
import cpup.mc.lib.targeting.TTarget

trait TAction {
	def affectedTarget: TTarget
	def runeType: TRuneType
}