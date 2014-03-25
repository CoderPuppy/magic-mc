package cpup.mc.magic.api.oldenLanguage.runes

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.util.IIcon

trait TRuneType {
	def runeClass: Class[_ <: TRune]
	@SideOnly(Side.CLIENT)
	def registerIcons(registerIcon: (String) => IIcon)

	def name: Int = 1
}
