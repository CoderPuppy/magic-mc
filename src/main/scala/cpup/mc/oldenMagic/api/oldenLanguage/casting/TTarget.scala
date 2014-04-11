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
	def chunkX: Int
	def chunkZ: Int
	def x: Double
	def y: Double
	def z: Double
	def isValid = true
	def obj: Either[Entity, BlockPos]
	def ownedTargets(typeNoun: TTypeNounRune[_ <: Entity, _ <: Block]): List[TTarget]
	def owner: TTarget

	def sameObj(other: TTarget) = obj match {
		case Left(entity) =>
			other.obj match {
				case Left(otherEntity) =>
					otherEntity == entity
				case _ => false
			}
		case Right(pos) =>
			other.obj match {
				case Right(otherPos) =>
					otherPos== pos
				case _ => false
			}
		case _ => false
	}
}