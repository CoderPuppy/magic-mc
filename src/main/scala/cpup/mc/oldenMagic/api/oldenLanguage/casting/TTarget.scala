package cpup.mc.oldenMagic.api.oldenLanguage.casting

import net.minecraft.world.World
import net.minecraft.entity.Entity
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.TTypeNounRune
import net.minecraft.block.Block
import cpup.mc.lib.util.pos.BlockPos
import net.minecraft.nbt.NBTTagCompound

trait TTarget {
	def targetType: TTargetType
	def writeToNBT(nbt: NBTTagCompound)

	def world: World
	def obj: Either[Entity, BlockPos]
	def ownedTargets(typeNoun: TTypeNounRune[_ <: Entity, _ <: Block]): List[TTarget]
}