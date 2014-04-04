package cpup.mc.oldenMagic.content.runes

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.util.IIcon
import cpup.mc.oldenMagic.OldenMagicMod
import cpup.mc.lib.util.pos.BlockPos
import net.minecraft.entity.{EntityAgeable, Entity}
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.TActionRune
import cpup.mc.oldenMagic.api.oldenLanguage.runes.{SingletonRune, TRune}
import cpup.mc.oldenMagic.api.oldenLanguage.casting.CastingContext

case object GrowRune extends SingletonRune with TActionRune {
	def mod = OldenMagicMod

	def name = s"${mod.ref.modID}:grow"

	def actUponBlock(context: CastingContext, pos: BlockPos) {
		for(_ <- 0 to 80) {
			pos.scheduleUpdateTick(0)
		}
	}

	def actUponEntity(context: CastingContext, entity: Entity) {
		entity match {
			case entity: EntityAgeable =>
				entity.addGrowth(20000)
			case _ =>
		}
	}

	@SideOnly(Side.CLIENT)
	var icon: IIcon = null
	@SideOnly(Side.CLIENT)
	def icons = List(icon)
	@SideOnly(Side.CLIENT)
	def registerIcons(registerIcon: (String) => IIcon) {
		icon = registerIcon(s"${mod.ref.modID}:runes/grow")
	}
}