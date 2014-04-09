package cpup.mc.oldenMagic.content.runes

import cpup.mc.oldenMagic.api.oldenLanguage.runes.SingletonRune
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.TNounRune
import cpup.mc.oldenMagic.api.oldenLanguage.casting.{CastingContext, TTarget, TCaster}
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.util.IIcon
import cpup.mc.oldenMagic.OldenMagicMod

object ItRune extends SingletonRune with TNounRune {
	def mod = OldenMagicMod

	def name = s"${mod.ref.modID}:it"
	override def getTargets(context: CastingContext, existing: List[TTarget]) = List(context.it)
	override def filter(context: CastingContext, prev: List[TNounRune], targets: List[TTarget]) = targets.filter(_ == context.it)

	@SideOnly(Side.CLIENT)
	var icon: IIcon = null
	@SideOnly(Side.CLIENT)
	def icons = List(icon)
	@SideOnly(Side.CLIENT)
	def registerIcons(registerIcon: (String) => IIcon) {
		icon = registerIcon(s"${mod.ref.modID}:runes/it")
	}
}