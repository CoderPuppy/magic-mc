package cpup.mc.oldenMagic.api.oldenLanguage.casting

import net.minecraft.entity.player.EntityPlayer
import cpup.mc.lib.util.EntityUtil
import cpup.mc.lib.util.pos.BlockPos
import cpw.mods.fml.common.FMLCommonHandler
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.TTypeNounRune
import net.minecraft.entity.Entity
import net.minecraft.block.Block
import net.minecraft.server.management.ServerConfigurationManager
import cpw.mods.fml.relauncher.Side
import net.minecraft.client.Minecraft
import net.minecraft.server.MinecraftServer
import net.minecraft.util.{Vec3, MovingObjectPosition}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World
import cpup.mc.oldenMagic.OldenMagicMod

case class PlayerCaster(name: String) extends TCaster {
	def targetType = PlayerCaster

	def world = obj.a match {
		case e: Entity => e.worldObj
		case _ => null
	}
	def mop = obj.a match {
		case player: EntityPlayer =>
			EntityUtil.getMOPBoth(player, if(player.capabilities.isCreativeMode) 6 else 3)
		case _ => null
	}

	def writeToNBT(nbt: NBTTagCompound) {
		nbt.setString("name", name)
	}

	def obj = Left(FMLCommonHandler.instance.getEffectiveSide match {
		case Side.CLIENT =>
			val player = Minecraft.getMinecraft.thePlayer
			if(player.getCommandSenderName != name) {
				throw new Exception(s"who is this: $name")
			}
			player

		case Side.SERVER =>
			MinecraftServer.getServer.getConfigurationManager.getPlayerForUsername(name)
	})

	// TODO: Implement
	override def ownedTargets(typeNoun: TTypeNounRune[_ <: Entity, _ <: Block]) = List()
}

object PlayerCaster extends TTargetType {
	def mod = OldenMagicMod

	def name = s"${mod.ref.modID}:player"
	def targetClass = classOf[PlayerCaster]
	def readFromNBT(nbt: NBTTagCompound) = PlayerCaster(nbt.getString("name"))
}