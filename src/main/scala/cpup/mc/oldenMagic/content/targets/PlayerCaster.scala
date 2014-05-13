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
import cpup.mc.oldenMagic.api.oldenLanguage.casting.TCaster
import cpup.mc.oldenMagic.api.oldenLanguage.EntityMagicData
import net.minecraft.util.{MovingObjectPosition, ChatComponentTranslation}
import cpup.mc.lib.targeting.{TTargetWrapper, PlayerTarget, TTargetFilter, TTargetType}
import cpup.mc.lib.inventory.EmptyInventory

case class PlayerCaster(player: PlayerTarget) extends TCaster with TTargetWrapper {
	def mod = OldenMagicMod

	def wrapped = Some(player)

	def targetType = PlayerCaster

	override def naturalPower = player.entity.flatMap(EntityMagicData.get(_)).map(_.naturalPower).getOrElse(0)
	override def maxSafePower = player.entity.flatMap(EntityMagicData.get(_)).map(_.maxSafePower).getOrElse(0)
	override def power = player.entity.flatMap(EntityMagicData.get(_)).map(_.power).getOrElse(0)
	override def usePower(amt: Int) = player.entity.flatMap(EntityMagicData.get(_)) match {
		case Some(data) =>
			if(data.power > amt) {
				player.entity match {
					case Some(playerE) =>
						playerE.addChatComponentMessage(new ChatComponentTranslation(
							s"messages.${mod.ref.modID}:testing.usedPower",
							player.name,
							data.power: Integer,
							amt: Integer
						))
					case None =>
				}
				data.power -= amt
				amt
			} else {
				player.entity match {
					case Some(playerE) =>
						playerE.addChatComponentMessage(new ChatComponentTranslation(
							s"messages.${mod.ref.modID}:testing.outOfPower",
							player.name,
							data.power: Integer,
							amt: Integer
						))
					case None =>
				}
				data.power = 0
				data.power
			}
		case None =>
			mod.logger.warn(s"Cannot get EntityMagicData for ${player.entity}")
			0
	}

	def writeToNBT(nbt: NBTTagCompound) {
		player.writeToNBT(nbt)
	}
}

object PlayerCaster extends TTargetType {
	def mod = OldenMagicMod

	def name = s"${mod.ref.modID}:player"
	def targetClass = classOf[PlayerCaster]
	def readFromNBT(nbt: NBTTagCompound) = PlayerTarget.readFromNBT(nbt).map(PlayerCaster(_))
}