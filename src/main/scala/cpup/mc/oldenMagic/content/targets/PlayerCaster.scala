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

case class PlayerCaster(name: String) extends TCaster {
	def targetType = PlayerCaster
	def owner = null

	def level = 2 // TODO: leveling
	def power = 100
	def usePower(amt: Int) = true

	def entity = FMLCommonHandler.instance.getEffectiveSide match {
		case Side.CLIENT =>
			val player = Minecraft.getMinecraft.thePlayer
			if(player.getCommandSenderName != name) {
				throw new Exception(s"who is this: $name")
			}
			player

		case Side.SERVER =>
			MinecraftServer.getServer.getConfigurationManager.getPlayerForUsername(name)

		case _ => null
	}

	def world = entity.worldObj
	def mop = EntityUtil.getMOPBoth(entity, if(entity.capabilities.isCreativeMode) 6 else 3)

	def x = entity.posX
	def y = entity.posY
	def z = entity.posZ
	def chunkX = entity.chunkCoordX
	def chunkZ = entity.chunkCoordZ

	def writeToNBT(nbt: NBTTagCompound) {
		nbt.setString("name", name)
	}

	override def isValid = FMLCommonHandler.instance.getEffectiveSide match {
		case Side.CLIENT => Minecraft.getMinecraft.thePlayer.getCommandSenderName == name
		case Side.SERVER => MinecraftServer.getServer.getConfigurationManager.getPlayerForUsername(name) != null
		case _ => false
	}

	def obj = Left(entity)

	// TODO: Implement
	override def ownedTargets(typeNoun: TTypeNounRune[_ <: Entity, _ <: Block]) = List()
}

object PlayerCaster extends TTargetType {
	def mod = OldenMagicMod

	def name = s"${mod.ref.modID}:player"
	def targetClass = classOf[PlayerCaster]
	def readFromNBT(nbt: NBTTagCompound) = PlayerCaster(nbt.getString("name"))
}