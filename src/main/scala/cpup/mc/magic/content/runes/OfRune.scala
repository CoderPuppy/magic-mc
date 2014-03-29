package cpup.mc.magic.content.runes

import cpup.mc.magic.api.oldenLanguage.runes._
import cpw.mods.fml.relauncher.{SideOnly, Side}
import net.minecraft.util.IIcon
import cpup.mc.magic.MagicMod
import cpup.mc.magic.api.oldenLanguage.runeParsing.{TNounPreposition, TNounModifier, TNoun}

object OfRune extends SingletonRune with TNounPreposition {
	def mod = MagicMod

	def createNounModifier(noun: TNoun) = OfModifier(noun)

	@SideOnly(Side.CLIENT)
	var icon: IIcon = null

	@SideOnly(Side.CLIENT)
	def icons = List(icon)

	@SideOnly(Side.CLIENT)
	def registerIcons(registerIcon: (String) => IIcon) {
		icon = registerIcon(mod.ref.modID + ":runes/or.png")
	}
}

case class OfModifier(noun: TNoun) extends TNounModifier {
	def modifyNoun(rune: TNoun) {
		// TODO: do stuff
		println("of modify", rune)
	}
}