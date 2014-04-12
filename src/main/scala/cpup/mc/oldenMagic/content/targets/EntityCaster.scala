package cpup.mc.oldenMagic.content.targets

import net.minecraft.entity.Entity
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.TTypeNounRune
import net.minecraft.block.Block
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.server.MinecraftServer
import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.relauncher.Side
import net.minecraft.client.Minecraft
import cpup.mc.lib.util.EntityUtil
import cpup.mc.oldenMagic.OldenMagicMod
import cpup.mc.oldenMagic.api.oldenLanguage.casting.{TTargetType, TCaster}

case class EntityCaster(entity: Entity) extends TCaster {
	if(entity == null) {
		throw new NullPointerException("entity cannot be null")
	}

	def targetType = EntityCaster
	def ownedTargets(typeNoun: TTypeNounRune[_ <: Entity, _ <: Block]) = List()
	def owner = null // TODO: owner

	def world = entity.worldObj
	def chunkX = entity.chunkCoordX
	def chunkZ = entity.chunkCoordZ
	def x = entity.posX
	def y = entity.posY
	def z = entity.posZ

	def mop = EntityUtil.getMOPBoth(obj.a, 4)
	def obj = Left(entity)

	def writeToNBT(nbt: NBTTagCompound) {
		nbt.setInteger("dim", world.provider.dimensionId)
		nbt.setInteger("id", entity.getEntityId)
	}
}

object EntityCaster extends TTargetType {
	def mod = OldenMagicMod

	def name = s"${mod.ref.modID}:entity"
	def targetClass = classOf[EntityCaster]
	def readFromNBT(nbt: NBTTagCompound) = {
		val dim = nbt.getInteger("dim")
		EntityCaster(
			(FMLCommonHandler.instance.getEffectiveSide match {
				case Side.CLIENT =>
					val world = Minecraft.getMinecraft.theWorld
					if(world.provider.dimensionId != dim) {
						throw new Exception(s"entity isn't in the same dimension as the player, $dim, ${world.provider.dimensionId}")
					}
					world
				case Side.SERVER =>
					MinecraftServer.getServer.worldServerForDimension(dim)
				case _ => null
			}).getEntityByID(nbt.getInteger("id"))
		)
	}
}