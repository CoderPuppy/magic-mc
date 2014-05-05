package cpup.mc.oldenMagic.content.targets

import cpup.mc.lib.util.EntityUtil
import cpw.mods.fml.common.FMLCommonHandler
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.TTypeNounRune
import net.minecraft.entity.Entity
import net.minecraft.block.Block
import cpw.mods.fml.relauncher.Side
import net.minecraft.client.Minecraft
import net.minecraft.server.MinecraftServer
import net.minecraft.nbt.NBTTagCompound
import cpup.mc.oldenMagic.OldenMagicMod
import cpup.mc.oldenMagic.api.oldenLanguage.casting.{TTargetType, TCaster}
import cpup.mc.oldenMagic.api.oldenLanguage.EntityMagicData

case class PlayerCaster(name: String) extends TCaster {
	def mod = OldenMagicMod

	def targetType = PlayerCaster
	def owner = null

	override def naturalPower = entity.flatMap(EntityMagicData.get(_)).map(_.naturalPower).getOrElse(0)
	override def maxSafePower = entity.flatMap(EntityMagicData.get(_)).map(_.maxSafePower).getOrElse(0)
	override def power = entity.flatMap(EntityMagicData.get(_)).map(_.power).getOrElse(0)
	override def usePower(amt: Int) = entity.flatMap(EntityMagicData.get(_)) match {
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

	def entity = FMLCommonHandler.instance.getEffectiveSide match {
		case Side.CLIENT =>
			val player = Minecraft.getMinecraft.thePlayer
			if(player.getCommandSenderName != name) {
				throw new Exception(s"who is this: $name")
				None
			}
			Some(player)

		case Side.SERVER =>
			Some(MinecraftServer.getServer.getConfigurationManager.getPlayerForUsername(name))

		case _ => None
	}

	def world = entity.map(_.worldObj).getOrElse(null)
	def mop = entity.map((e) => EntityUtil.getMOPBoth(e, if(e.capabilities.isCreativeMode) 6 else 3)).getOrElse(null)

	def x = entity.map(_.posX).getOrElse(0)
	def y = entity.map(_.posY).getOrElse(0)
	def z = entity.map(_.posZ).getOrElse(0)
	def chunkX = entity.map(_.chunkCoordX).getOrElse(0)
	def chunkZ = entity.map(_.chunkCoordZ).getOrElse(0)

	def writeToNBT(nbt: NBTTagCompound) {
		nbt.setString("name", name)
	}

	override def isValid = FMLCommonHandler.instance.getEffectiveSide match {
		case Side.CLIENT => Minecraft.getMinecraft.thePlayer.getCommandSenderName == name
		case Side.SERVER => MinecraftServer.getServer.getConfigurationManager.getPlayerForUsername(name) != null
		case _ => false
	}

	def obj = entity.map(Left(_)).getOrElse(Left(null))

	// TODO: Implement
	override def ownedTargets(typeNoun: TTypeNounRune[_ <: Entity, _ <: Block]) = List()
}

object PlayerCaster extends TTargetType {
	def mod = OldenMagicMod

	def name = s"${mod.ref.modID}:player"
	def targetClass = classOf[PlayerCaster]
	def readFromNBT(nbt: NBTTagCompound) = PlayerCaster(nbt.getString("name"))
}