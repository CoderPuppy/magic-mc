package cpup.mc.oldenMagic.api.oldenLanguage.casting

import net.minecraft.entity.Entity
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.TTypeNounRune
import net.minecraft.block.Block
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World
import net.minecraft.server.MinecraftServer
import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.relauncher.Side
import net.minecraft.client.Minecraft
import cpup.mc.lib.util.EntityUtil
import cpup.mc.oldenMagic.OldenMagicMod

case class EntityCaster(var world: World, entityID: Int) extends TCaster {
	def targetType = EntityCaster
	def ownedTargets(typeNoun: TTypeNounRune[_ <: Entity, _ <: Block]) = List()

	def mop = EntityUtil.getMOPBoth(obj.a, 4)
	def obj = Left(world.getEntityByID(entityID))

	def writeToNBT(nbt: NBTTagCompound) {
		nbt.setInteger("dim", world.provider.dimensionId)
		nbt.setInteger("id", entityID)
	}
}

object EntityCaster extends TTargetType {
	def mod = OldenMagicMod

	def name = s"${mod.ref.modID}:entity"
	def targetClass = classOf[EntityCaster]
	def readFromNBT(nbt: NBTTagCompound) = {
		val dim = nbt.getInteger("dim")
		EntityCaster(
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
			nbt.getInteger("id")
		)
	}
}