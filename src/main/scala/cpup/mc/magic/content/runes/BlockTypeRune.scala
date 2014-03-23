package cpup.mc.magic.content.runes

import cpup.mc.magic.api.oldenLanguage._
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.IIcon
import cpup.mc.magic.MagicMod

case class BlockTypeRune(name: String) extends TRune {
	@SideOnly(Side.CLIENT)
	def icons = List(BlockTypeRune.icon)

	def runeType = BlockTypeRune

	def writeToNBT(nbt: NBTTagCompound) {
		nbt.setString("name", name)
	}
}

object BlockTypeRune extends TRuneType {
	def mod = MagicMod
	def runeClass = classOf[BlockTypeRune]

	@SideOnly(Side.CLIENT)
	var icon: IIcon = null

	@SideOnly(Side.CLIENT)
	def registerIcons(registerIcon: (String) => IIcon) {
		icon = registerIcon(mod.ref.modID + ":runes/block")
	}

	def readFromNBT(nbt: NBTTagCompound) = BlockTypeRune(nbt.getString("name"))
}

object BlockTypeTransform extends TTransform {
	def transform(context: TContext, rune: TextRune) = BlockTypeRune(rune.text)
}