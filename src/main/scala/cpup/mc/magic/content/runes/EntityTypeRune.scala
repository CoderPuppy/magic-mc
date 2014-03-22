package cpup.mc.magic.content.runes

import cpup.mc.magic.api.oldenLanguage._
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.IIcon
import cpup.mc.magic.MagicMod

case class EntityTypeRune(name: String) extends TRune {
	@SideOnly(Side.CLIENT)
	def icons = List(EntityTypeRune.icon)

	def runeType = EntityTypeRune
	def writeToNBT(nbt: NBTTagCompound) {
		nbt.setString("name", name)
	}
}

object EntityTypeRune extends TRuneType {
	def mod = MagicMod

	def runeClass = classOf[EntityTypeRune]
	def readFromNBT(nbt: NBTTagCompound) = EntityTypeRune(nbt.getString("name"))

	@SideOnly(Side.CLIENT)
	var icon: IIcon = null

	@SideOnly(Side.CLIENT)
	def registerIcons(registerIcon: (String) => IIcon) {
		icon = registerIcon(mod.ref.modID + ":runes/entity")
	}
}

object EntityTypeTransform extends TTransform {
	def transform(context: TContext, rune: TextRune) = EntityTypeRune(rune.txt)
}