package cpup.mc.magic.content.runes

import net.minecraft.util.IIcon
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.nbt.NBTTagCompound
import cpup.mc.magic.MagicMod
import cpup.mc.magic.api.oldenLanguage.{TContext, TTransform, TRune, TRuneType}

case class PlayerRune(name: String) extends TRune {
	@SideOnly(Side.CLIENT)
	def icons = List(PlayerRune.icon)

	def runeType = PlayerRune

	def writeToNBT(nbt: NBTTagCompound) {
		nbt.setString("name", name)
	}
}

object PlayerRune extends TRuneType {
	def mod = MagicMod

	def runeClass = classOf[PlayerRune]

	def readFromNBT(nbt: NBTTagCompound) = PlayerRune(nbt.getString("name"))

	@SideOnly(Side.CLIENT)
	var icon: IIcon = null

	def registerIcons(registerIcon: (String) => IIcon) {
		icon = registerIcon(mod.ref.modID + ":runes/player")
	}
}

object PlayerTransform extends TTransform[String] {
	def isValid(context: TContext, rune: TRune) = rune match {
		case TextRune(playerName) => Some(playerName)
		case _ => None
	}

	def transform(context: TContext, rune: TRune, playerName: String) = new PlayerRune(playerName)
}