package cpup.mc.oldenMagic.api.oldenLanguage.casting

import cpup.mc.oldenMagic.api.oldenLanguage.runes.TRuneType
import net.minecraft.world.World

trait TAction {
	def affectedTarget: TTarget
	def runeType: TRuneType
}