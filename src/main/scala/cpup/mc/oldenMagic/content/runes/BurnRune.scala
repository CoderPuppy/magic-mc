package cpup.mc.oldenMagic.content.runes

import cpw.mods.fml.relauncher.{SideOnly, Side}
import net.minecraft.util.IIcon
import cpup.mc.oldenMagic.OldenMagicMod
import cpup.mc.lib.util.pos.BlockPos
import net.minecraft.entity.{EntityLivingBase, Entity}
import cpup.mc.lib.util.Direction
import net.minecraft.init.Blocks
import cpup.mc.oldenMagic.api.oldenLanguage.runes.SingletonRune
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.TActionRune
import cpup.mc.oldenMagic.api.oldenLanguage.casting.CastingContext

object BurnRune extends SingletonRune with TActionRune {
	def mod = OldenMagicMod

	def name = s"${mod.ref.modID}:burn"
	def act(context: CastingContext, pos: BlockPos) {
		pos.offset(Direction.Up).tryReplaceWith(Blocks.fire)
	}
	def act(context: CastingContext, entity: Entity) {
		entity.setFire(10) // TODO: more depending on something
	}

	@SideOnly(Side.CLIENT)
	var icon: IIcon = null

	@SideOnly(Side.CLIENT)
	def icons = List(icon)

	@SideOnly(Side.CLIENT)
	def registerIcons(registerIcon: (String) => IIcon) {
		icon = registerIcon(mod.ref.modID + ":runes/burn")
	}
}