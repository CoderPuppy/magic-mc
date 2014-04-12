package cpup.mc.oldenMagic.content.testing

import net.minecraft.command.{ICommandSender, CommandBase}
import cpup.mc.lib.ModLifecycleHandler
import cpup.mc.oldenMagic.OldenMagicMod
import cpup.mc.oldenMagic.api.oldenLanguage.PassiveSpells
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.Spell
import cpup.mc.oldenMagic.content.runes._
import cpup.mc.oldenMagic.content.targets.PlayerCaster

object CommandAntiSeeing extends CommandBase with ModLifecycleHandler {
	def mod = OldenMagicMod

	def getCommandName = "antiseeing"
	def getCommandUsage(sender: ICommandSender) = s"commands.${mod.ref.modID}:antiseeing.usage"
	def processCommand(sender: ICommandSender, args: Array[String]) {
		val player = if(args.length >= 1) {
			CommandBase.getPlayer(sender, args(0))
		} else {
			CommandBase.getCommandSenderAsPlayer(sender)
		}

		val enderSpells = PassiveSpells.getOrCreate(player.worldObj)
		enderSpells.registerSpell(
			player.getCommandSenderName,
			PlayerCaster(player.getCommandSenderName),
			Spell(new SeenRune, List(MeRune)),
			Spell(HideRune, List(ItRune))
		)
		println(enderSpells)
	}
}