package cpup.mc.magic.content.runes

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.IIcon
import cpup.mc.magic.MagicMod
import cpw.mods.fml.common.registry.GameData
import cpup.mc.lib.util.GUIUtil
import cpup.mc.magic.api.oldenLanguage.textParsing.{TTransform, TextRune, TContext}
import cpup.mc.magic.api.oldenLanguage.runes.{TRuneType, TRune}

case class BlockTypeRune(name: String) extends TRune {
	println(GameData.blockRegistry.getObject(name))
	if(GameData.blockRegistry.getObject(name).getIcon(1, 0) == null) {
		throw new NullPointerException(name + " doesn't have a texture for the top")
	}

	@SideOnly(Side.CLIENT)
	def icons = List()
//	def icons = List(BlockTypeRune.icon)

	@SideOnly(Side.CLIENT)
	override def render(x: Int, y: Int, width: Int, height: Int) {
		GUIUtil.drawBlockIconAt(GameData.blockRegistry.getObject(name).getIcon(1, 0), x, y, 0, 32, 32)
	}

	def runeType = BlockTypeRune

//	def writeToNBT(nbt: NBTTagCompound) {
//		nbt.setString("name", name)
//	}
}

object BlockTypeRune extends TRuneType {
	def name = "tn-block"

	def mod = MagicMod
	def runeClass = classOf[BlockTypeRune]

	@SideOnly(Side.CLIENT)
	var icon: IIcon = null

	@SideOnly(Side.CLIENT)
	def registerIcons(registerIcon: (String) => IIcon) {
		icon = registerIcon(mod.ref.modID + ":runes/block")
	}

//	def readFromNBT(nbt: NBTTagCompound) = BlockTypeRune(nbt.getString("name"))
}

object BlockTypeTransform extends TTransform {
	def transform(context: TContext, rune: TextRune) = BlockTypeRune(rune.text)
}