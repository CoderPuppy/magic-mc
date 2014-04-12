package cpup.mc.oldenMagic.content.testing

import net.minecraft.command.{ICommandSender, CommandBase}
import cpup.mc.lib.ModLifecycleHandler
import cpup.mc.oldenMagic.OldenMagicMod
import cpup.mc.oldenMagic.api.oldenLanguage.PassiveSpells
import cpup.mc.oldenMagic.api.oldenLanguage.casting.PlayerCaster
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.Spell
import cpup.mc.oldenMagic.content.runes.{ItRune, MeRune, ProtectRune, DamageRune}

object CommandAntiDamage extends CommandBase with ModLifecycleHandler {
	def mod = OldenMagicMod

	def getCommandName = "antidamage"
	def getCommandUsage(sender: ICommandSender) = s"commands.${mod.ref.modID}:antidamage.usage"
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
			Spell(new DamageRune, List(MeRune)),
			Spell(new ProtectRune, List(ItRune))
		)
		println(enderSpells)
	}
}