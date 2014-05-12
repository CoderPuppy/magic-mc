package cpup.mc.oldenMagic.content.targets

import cpup.mc.lib.util.pos.BlockPos
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.TTypeNounRune
import net.minecraft.entity.Entity
import net.minecraft.block.Block
import net.minecraft.nbt.NBTTagCompound
import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.relauncher.Side
import net.minecraft.client.Minecraft
import net.minecraft.server.MinecraftServer
import cpup.mc.oldenMagic.OldenMagicMod
import cpup.mc.lib.targeting.{TTargetFilter, TTargetType, TTarget}

case class BlockTarget(pos: BlockPos) extends TTarget {
	def world = Some(pos.world)
	def ownedTargets(typeNoun: TTargetFilter[_ <: Entity, _ <: Block]) = List()
	def obj = Some(Right(pos))
	def targetType = BlockTarget
	def owner = null
	def writeToNBT(nbt: NBTTagCompound) {
		nbt.setInteger("dim", pos.world.provider.dimensionId)
		nbt.setInteger("x", pos.x)
		nbt.setInteger("y", pos.y)
		nbt.setInteger("z", pos.z)
	}
	def chunkX = Some(pos.chunkX)
	def chunkZ = Some(pos.chunkZ)
	def x = Some(pos.x)
	def y = Some(pos.y)
	def z = Some(pos.z)
}

object BlockTarget extends TTargetType {
	def mod = OldenMagicMod

	def name = s"${mod.ref.modID}:block"
	def targetClass = classOf[BlockTarget]
	def readFromNBT(nbt: NBTTagCompound) = {
		val dim = nbt.getInteger("dim")
		BlockTarget(BlockPos(
			FMLCommonHandler.instance.getEffectiveSide match {
				case Side.CLIENT =>
					val world = Minecraft.getMinecraft.theWorld
					if(world.provider.dimensionId != dim) {
						throw new Exception(s"entity isn't in the same dimension as the player, $dim, ${world.provider.dimensionId}")
					}
					world
				case Side.SERVER =>
					MinecraftServer.getServer.worldServerForDimension(dim)
			},
			nbt.getInteger("x"),
			nbt.getInteger("y"),
			nbt.getInteger("z")
		))
	}
}