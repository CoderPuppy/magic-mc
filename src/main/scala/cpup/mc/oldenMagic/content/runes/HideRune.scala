package cpup.mc.oldenMagic.content.runes

import cpup.mc.oldenMagic.api.oldenLanguage.runes.SingletonRune
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.TVerbRune
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.util.IIcon
import cpup.mc.oldenMagic.OldenMagicMod
import cpup.mc.oldenMagic.api.oldenLanguage.casting.CastingContext
import cpup.mc.lib.util.pos.BlockPos
import net.minecraft.entity.Entity
import cpup.mc.oldenMagic.api.oldenLanguage.PassiveSpellsContext
import net.minecraft.client.renderer.texture.IIconRegister
import cpup.mc.lib.targeting.TTarget

object HideRune extends SingletonRune with TVerbRune {
	def mod = OldenMagicMod

	def name = s"${mod.ref.modID}:hide"

	def powerAmt = 120

	override def act(context: CastingContext, targets: List[TTarget]) {
		context match {
			case PassiveSpellsContext(player, caster, spell, action: SeenAction) =>
				if(caster.usePower(powerAmt) == powerAmt) {
					action.cancel
				}
			case _ =>
		}
	}

	def act(context: CastingContext, pos: BlockPos) {}
	def act(context: CastingContext, entity: Entity) {}

	@SideOnly(Side.CLIENT)
	def icons = List(icon)
	@SideOnly(Side.CLIENT)
	var icon: IIcon = null
	@SideOnly(Side.CLIENT)
	def registerIcons(register: IIconRegister) {
		icon = register.registerIcon(s"${mod.ref.modID}:runes/hide")
	}
}