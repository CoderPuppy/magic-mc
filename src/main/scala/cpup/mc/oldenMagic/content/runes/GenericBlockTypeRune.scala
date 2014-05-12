package cpup.mc.oldenMagic.content.runes

import cpup.mc.oldenMagic.api.oldenLanguage.runes.SingletonRune
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.NonEntityTypeNoun
import cpup.mc.oldenMagic.api.oldenLanguage.casting.CastingContext
import cpup.mc.lib.util.pos.BlockPos
import net.minecraft.client.renderer.texture.IIconRegister
import cpw.mods.fml.relauncher.{SideOnly, Side}
import cpup.mc.oldenMagic.OldenMagicMod
import net.minecraft.block.Block
import net.minecraft.util.IIcon

object GenericBlockTypeRune extends SingletonRune with NonEntityTypeNoun {
	def mod = OldenMagicMod

	override def name = s"${mod.ref.modID}:generic-block"

	override def blockClass = classOf[Block]
	override def filterBlock(context: CastingContext, pos: BlockPos) = true

	@SideOnly(Side.CLIENT)
	var icon: IIcon = null

	@SideOnly(Side.CLIENT)
	override def icons = List()

	@SideOnly(Side.CLIENT)
	override def registerIcons(register: IIconRegister) {
		icon = register.registerIcon(s"${mod.ref.modID}:runes/generic-block")
	}
}