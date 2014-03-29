package cpup.mc.oldenMagic.api.oldenLanguage.runes

import cpw.mods.fml.relauncher.{Side, SideOnly}

trait InternalRune extends TRune {
	@SideOnly(Side.CLIENT)
	def icons = List()
}