package cpup.mc.oldenMagic.content.runes

import cpup.mc.oldenMagic.api.oldenLanguage.runes._
import cpw.mods.fml.relauncher.{SideOnly, Side}
import net.minecraft.util.IIcon
import cpup.mc.oldenMagic.MagicMod
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.{TNounPreposition, TNounModifier, TNoun}

object OfRune extends SingletonRune with TNounPreposition {
	def mod = MagicMod

	def createNounModifier(targetPath: List[TNoun]) = OfModifier(targetPath)

	@SideOnly(Side.CLIENT)
	var icon: IIcon = null

	@SideOnly(Side.CLIENT)
	def icons = List(icon)

	@SideOnly(Side.CLIENT)
	def registerIcons(registerIcon: (String) => IIcon) {
		icon = registerIcon(mod.ref.modID + ":runes/or.png")
	}
}

case class OfModifier(targetPath: List[TNoun]) extends TNounModifier {
	def modifyNoun(rune: TNoun) {
		// TODO: do stuff
		println("of modify", rune, targetPath)
	}
}