package cpup.mc.oldenMagic.api.oldenLanguage.textParsing

import net.minecraft.util.IIcon
import cpw.mods.fml.relauncher.{Side, SideOnly}
import cpup.mc.oldenMagic.MagicMod
import cpup.mc.oldenMagic.api.oldenLanguage.runes.{TRune, TRuneType}

case class TextRune(text: String) extends TRune {
	@SideOnly(Side.CLIENT)
	def icons = List(TextRune.icon)

	def runeType = TextRune
}

object TextRune extends TRuneType {
	def mod = MagicMod

	@SideOnly(Side.CLIENT)
	var icon: IIcon = null

	@SideOnly(Side.CLIENT)
	def registerIcons(registerIcon: (String) => IIcon) {
		icon = registerIcon(mod.ref.modID + ":runes/text")
	}
	def runeClass = classOf[TextRune]
}