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
import cpup.mc.oldenMagic.api.oldenLanguage.EntityMagicData
import org.apache.logging.log4j.Marker

case class EntityCaster(entity: Entity) extends TCaster {
	def mod = OldenMagicMod

	if(entity == null) {
		throw new NullPointerException("entity cannot be null")
	}

	def targetType = EntityCaster
	def ownedTargets(typeNoun: TTypeNounRune[_ <: Entity, _ <: Block]) = List()
	def owner = null // TODO: owner

	override def naturalPower = EntityMagicData.get(entity).map(_.naturalPower).getOrElse(0)
	override def maxSafePower = EntityMagicData.get(entity).map(_.maxSafePower).getOrElse(0)
	override def power = EntityMagicData.get(entity).map(_.power).getOrElse(0)
	override def usePower(amt: Int) = EntityMagicData.get(entity) match {
		case Some(data) =>
			if(data.power > amt) {
				data.power -= amt
				amt
			} else {
				data.power = 0
				data.power
			}
		case None =>
			mod.logger.warn(s"Cannot get EntityMagicData for $entity")
			0
	}

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