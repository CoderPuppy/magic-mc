package cpup.mc.oldenMagic.content.runes

import cpw.mods.fml.relauncher.{SideOnly, Side}
import net.minecraft.util.IIcon
import cpup.mc.oldenMagic.OldenMagicMod
import cpup.mc.lib.util.pos.BlockPos
import net.minecraft.entity.Entity
import cpup.mc.lib.util.Direction
import net.minecraft.init.Blocks
import cpup.mc.oldenMagic.api.oldenLanguage.runes.SingletonRune
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.TVerbRune
import cpup.mc.oldenMagic.api.oldenLanguage.casting.CastingContext
import net.minecraft.client.renderer.texture.IIconRegister

object BurnRune extends SingletonRune with TVerbRune {
	def mod = OldenMagicMod

	override def name = s"${mod.ref.modID}:burn"

	def powerAmt = 50

	override def act(context: CastingContext, pos: BlockPos) {
		if(context.caster.usePower(powerAmt) == powerAmt) {
			pos.offset(Direction.Up).tryReplaceWith(Blocks.fire)
		}
	}
	override def act(context: CastingContext, entity: Entity) {
		entity.setFire(context.caster.usePower(Math.max(context.caster.power / 4, powerAmt)))
	}

	@SideOnly(Side.CLIENT)
	var icon: IIcon = null

	@SideOnly(Side.CLIENT)
	override def icons = List(icon)

	@SideOnly(Side.CLIENT)
	override def registerIcons(register: IIconRegister) {
		icon = register.registerIcon(s"${mod.ref.modID}:runes/burn")
	}
}