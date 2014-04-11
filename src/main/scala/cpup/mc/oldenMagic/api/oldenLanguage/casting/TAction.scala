package cpup.mc.oldenMagic.api.oldenLanguage.casting

import cpup.mc.oldenMagic.api.oldenLanguage.runes.TRuneType
import net.minecraft.world.World

trait TAction {
	def world: World
	def chunkX: Int
	def chunkZ: Int
	def affectedTarget: TTarget
	def runeType: TRuneType
}