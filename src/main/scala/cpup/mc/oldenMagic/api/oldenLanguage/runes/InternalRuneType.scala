package cpup.mc.oldenMagic.api.oldenLanguage.runes

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.util.IIcon
import net.minecraft.client.renderer.texture.IIconRegister

trait InternalRuneType extends TRuneType {
	@SideOnly(Side.CLIENT)
	def registerIcons(register: IIconRegister) {}
}