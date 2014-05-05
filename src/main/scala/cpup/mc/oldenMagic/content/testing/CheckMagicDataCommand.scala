package cpup.mc.oldenMagic.content.testing

import net.minecraft.command.{ICommandSender, CommandBase}
import cpup.mc.lib.ModLifecycleHandler
import cpup.mc.oldenMagic.OldenMagicMod
import cpup.mc.oldenMagic.api.oldenLanguage.{EntityPowerData, PassiveSpells}
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.Spell
import cpup.mc.oldenMagic.content.runes.{ItRune, MeRune, ProtectRune, DamageRune}
import cpup.mc.oldenMagic.content.targets.{OPCaster, PlayerCaster}
import net.minecraft.util.ChatComponentTranslation
import net.minecraft.entity.Entity
import cpup.mc.lib.util.EntityUtil

object CheckMagicDataCommand extends CommandBase with ModLifecycleHandler {
	def mod = OldenMagicMod

	def getCommandName = "check-magic"
	def internalName = "check-magic"
	def getCommandUsage(sender: ICommandSender) = s"commands.${mod.ref.modID}:$internalName.usage"
	def processCommand(sender: ICommandSender, args: Array[String]) {
		sender match {
			case ent: Entity =>
				EntityPowerData.get(ent) match {
					case Some(data) =>
						sender.addChatMessage(new ChatComponentTranslation(
							s"commands.${mod.ref.modID}:$internalName.result",
							sender.getCommandSenderName,
							data.level: Integer,
							data.power: Integer
						))

					case None =>
						sender.addChatMessage(new ChatComponentTranslation(
							s"commands.${mod.ref.modID}:$internalName.failure",
							"cannot get or create data"
						))
				}

			case _ =>
				sender.addChatMessage(new ChatComponentTranslation(
					s"commands.${mod.ref.modID}:$internalName.failure",
					"not an Entity"
				))
		}
	}
}