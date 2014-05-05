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

object ChangeLevelCommand extends CommandBase with ModLifecycleHandler {
	def mod = OldenMagicMod

	def getCommandName = "change-level"
	def internalName = "change-level"
	def getCommandUsage(sender: ICommandSender) = s"commands.${mod.ref.modID}:$internalName.usage"
	def processCommand(sender: ICommandSender, args: Array[String]) {
		sender match {
			case ent: Entity =>
				EntityPowerData.get(ent) match {
					case Some(data) =>
						if(args.length < 1) {
							sender.addChatMessage(new ChatComponentTranslation(getCommandUsage(sender)))
						} else {
							val amt = parseInt(args(0))
							data.level += amt
							sender.addChatMessage(new ChatComponentTranslation(
								s"commands.${mod.ref.modID}:$internalName.success",
								sender.getCommandSenderName,
								amt,
								data.level
							))
						}

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