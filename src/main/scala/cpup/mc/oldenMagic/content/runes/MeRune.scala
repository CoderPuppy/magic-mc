package cpup.mc.oldenMagic.content.runes

import cpup.mc.oldenMagic.api.oldenLanguage.runes.SingletonRune
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.TNounRune
import cpup.mc.oldenMagic.api.oldenLanguage.casting.CastingContext
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.util.IIcon
import cpup.mc.oldenMagic.OldenMagicMod
import cpup.mc.oldenMagic.content.targets.PlayerCaster
import net.minecraft.client.renderer.texture.IIconRegister
import cpup.mc.lib.targeting.{PlayerTarget, TTarget}

object MeRune extends SingletonRune with TNounRune {
	def mod = OldenMagicMod

	def name = s"${mod.ref.modID}:me"
	override def getTargets(context: CastingContext, existing: List[TTarget]) = List(PlayerCaster(PlayerTarget(context.player)))
	override def filter(context: CastingContext, prev: List[TNounRune], target: TTarget) = {
		target.sameObj(PlayerCaster(PlayerTarget(context.player)))
	}

	@SideOnly(Side.CLIENT)
	var icon: IIcon = null
	@SideOnly(Side.CLIENT)
	def icons = List(icon)
	@SideOnly(Side.CLIENT)
	def registerIcons(register: IIconRegister) {
		icon = register.registerIcon(s"${mod.ref.modID}:runes/me")
	}
}