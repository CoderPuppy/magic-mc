package cpup.mc.oldenMagic.content.runes

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.util.IIcon
import cpup.mc.oldenMagic.OldenMagicMod
import cpw.mods.fml.common.registry.GameData
import cpup.mc.lib.util.GUIUtil
import cpup.mc.oldenMagic.api.oldenLanguage.textParsing.{TTransform, TextRune, TContext}
import cpup.mc.oldenMagic.api.oldenLanguage.runes.{TRuneType, TRune}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.client.renderer.texture.IIconRegister
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.{NonEntityTypeNoun, TTypeNounRune, TNounRune}
import cpup.mc.oldenMagic.api.oldenLanguage.casting.{TTarget, CastingContext}
import cpup.mc.lib.util.pos.BlockPos
import net.minecraft.block.Block

case class BlockTypeRune(name: String) extends TRune with NonEntityTypeNoun {
	def mod = OldenMagicMod

	val block = GameData.getBlockRegistry.getObject(name)
	mod.logger.debug(block)
	if(block.getIcon(1, 0) == null) {
		throw new NullPointerException(name + " doesn't have a texture for the top")
	}

	override def blockClass = block.getClass.asInstanceOf[Class[Block]]
	override def filterBlock(context: CastingContext, pos: BlockPos) = block == pos.block

	def runeType = BlockTypeRune
	def writeToNBT(nbt: NBTTagCompound) {
		nbt.setString("name", name)
	}

	@SideOnly(Side.CLIENT)
	def icons = List()
//	def icons = List(BlockTypeRune.icon)

	@SideOnly(Side.CLIENT)
	override def render(x: Int, y: Int, width: Int, height: Int) {
		GUIUtil.drawBlockIconAt(GameData.getBlockRegistry.getObject(name).getIcon(1, 0), x, y, 0, 32, 32)
	}
}

object BlockTypeRune extends TRuneType {
	def mod = OldenMagicMod

	def name = s"${mod.ref.modID}:block"
	def runeClass = classOf[BlockTypeRune]
	def readFromNBT(nbt: NBTTagCompound) = BlockTypeRune(nbt.getString("name"))

	@SideOnly(Side.CLIENT)
	var icon: IIcon = null

	@SideOnly(Side.CLIENT)
	def registerIcons(register: IIconRegister) {
		icon = register.registerIcon(s"${mod.ref.modID}:runes/block")
	}
}

object BlockTypeTransform extends TTransform {
	def transform(context: TContext, content: String) = BlockTypeRune(content)
}