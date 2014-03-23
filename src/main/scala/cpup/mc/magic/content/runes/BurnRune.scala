package cpup.mc.magic.content.runes

import cpup.mc.magic.api.oldenLanguage._
import cpw.mods.fml.relauncher.{SideOnly, Side}
import net.minecraft.util.IIcon
import cpup.mc.magic.MagicMod
import net.minecraft.nbt.NBTTagCompound
import cpup.mc.magic.api.oldenLanguage.parsing.{TContext, TTransform, TextRune}

object BurnRune extends TRune with TRuneType {
	def mod = MagicMod

	@SideOnly(Side.CLIENT)
	var icon: IIcon = null

	@SideOnly(Side.CLIENT)
	def icons = List(icon)

	@SideOnly(Side.CLIENT)
	def registerIcons(registerIcon: (String) => IIcon) {
		icon = registerIcon(mod.ref.modID + ":runes/burn")
	}

	def runeType = this
	def runeClass = getClass

	def writeToNBT(nbt: NBTTagCompound){}
	def readFromNBT(nbt: NBTTagCompound) = this
}

object BurnTransform extends TTransform {
	def transform(context: TContext, rune: TextRune) = BurnRune
}