package cpup.mc.magic.content.runes

import cpup.mc.magic.api.oldenLanguage.runes.{TAction, SingletonRune}
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.util.IIcon
import cpup.mc.magic.MagicMod
import cpup.mc.lib.util.pos.BlockPos
import net.minecraft.entity.{EntityAgeable, Entity}
import cpw.mods.fml.common.FMLCommonHandler

object GrowRune extends SingletonRune with TAction {
	def mod = MagicMod

	def actUponBlock(pos: BlockPos) {
		for(_ <- 0 to 20) {
			pos.scheduleUpdateTick(0)
		}
	}

	def actUponEntity(entity: Entity) {
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