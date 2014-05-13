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
import cpup.mc.oldenMagic.api.oldenLanguage.casting.TCaster
import cpup.mc.oldenMagic.api.oldenLanguage.EntityMagicData
import org.apache.logging.log4j.Marker
import cpup.mc.lib.targeting._
import scala.Some

case class EntityCaster(entity: EntityTarget) extends TCaster with TTargetWrapper {
	def mod = OldenMagicMod

	override def wrapped = Some(entity)
	override def writeToNBT(nbt: NBTTagCompound) {
		entity.writeToNBT(nbt)
	}

	override def targetType = EntityCaster

	override def naturalPower = EntityMagicData.get(entity.entity).map(_.naturalPower).getOrElse(0)
	override def maxSafePower = EntityMagicData.get(entity.entity).map(_.maxSafePower).getOrElse(0)
	override def power = EntityMagicData.get(entity.entity).map(_.power).getOrElse(0)
	override def usePower(amt: Int) = EntityMagicData.get(entity.entity) match {
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
}

object EntityCaster extends TTargetType {
	def mod = OldenMagicMod

	override def name = s"${mod.ref.modID}:entity"
	override def targetClass = classOf[EntityCaster]
	override def readFromNBT(nbt: NBTTagCompound) = {
		val dim = nbt.getInteger("dim")
		from((FMLCommonHandler.instance.getEffectiveSide match {
			case Side.CLIENT =>
				val world = Minecraft.getMinecraft.theWorld
				if(world.provider.dimensionId != dim) {
					throw new Exception(s"entity isn't in the same dimension as the player, $dim, ${world.provider.dimensionId}")
				}
				world
			case Side.SERVER =>
				MinecraftServer.getServer.worldServerForDimension(dim)
			case _ => null
		}).getEntityByID(nbt.getInteger("id")))
	}
	def from(entity: Entity) = Some(EntityCaster(EntityTarget(entity)))
}