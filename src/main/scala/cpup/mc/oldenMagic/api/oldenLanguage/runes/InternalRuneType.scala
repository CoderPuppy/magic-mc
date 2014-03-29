package cpup.mc.oldenMagic.api.oldenLanguage.runes

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.util.IIcon

trait InternalRuneType extends TRuneType {
	@SideOnly(Side.CLIENT)
	def registerIcons(registerIcon: (String) => IIcon) {}
}