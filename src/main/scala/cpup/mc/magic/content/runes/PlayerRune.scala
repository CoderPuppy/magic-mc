package cpup.mc.magic.content.runes

import cpup.mc.magic.api.{TRuneType, TRune}
import net.minecraft.util.IIcon
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.nbt.NBTTagCompound
import cpup.mc.magic.MagicMod

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