package cpup.mc.oldenMagic.content.testing

import net.minecraft.command.{ICommandSender, CommandBase}
import cpup.mc.lib.ModLifecycleHandler
import cpup.mc.oldenMagic.OldenMagicMod
import cpup.mc.oldenMagic.api.oldenLanguage.{EntityMagicData, PassiveSpells}
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.Spell
import cpup.mc.oldenMagic.content.runes.{ItRune, MeRune, ProtectRune, DamageRune}
import cpup.mc.oldenMagic.content.targets.{OPCaster, PlayerCaster}
import net.minecraft.util.ChatComponentTranslation
import net.minecraft.entity.Entity
import cpup.mc.lib.util.EntityUtil

object ChangeMagicDataCommand extends CommandBase with ModLifecycleHandler {
	def mod = OldenMagicMod

	def getCommandName = "change-magic"
	def internalName = "change-magic"
	def getCommandUsage(sender: ICommandSender) = s"commands.${mod.ref.modID}:$internalName.usage"
	def processCommand(sender: ICommandSender, args: Array[String]) {
		sender match {
			case ent: Entity =>
				EntityMagicData.get(ent) match {
					case Some(data) =>
						if(args.length < 2) {
							sender.addChatMessage(new ChatComponentTranslation(getCommandUsage(sender)))
						} else {
							try {
								val name = args(0)
								val amt = CommandBase.parseInt(sender, args(1))
								data.setData(name, data.datas(name) + amt)
								sender.addChatMessage(new ChatComponentTranslation(
									s"commands.${mod.ref.modID}:$internalName.success",
									sender.getCommandSenderName,
									name,
									amt: Integer,
									data.datas(name): Integer
								))
							} catch {
								case e: Exception =>
									sender.addChatMessage(new ChatComponentTranslation(
										s"commands.${mod.ref.modID}:$internalName.failure",
										e.getMessage
									))
							}
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