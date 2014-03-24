package cpup.mc.magic.content.runes

import cpw.mods.fml.relauncher.{SideOnly, Side}
import net.minecraft.util.IIcon
import cpup.mc.magic.MagicMod
import cpup.mc.lib.util.pos.BlockPos
import net.minecraft.entity.Entity
import cpup.mc.lib.util.Direction
import net.minecraft.init.Blocks
import cpup.mc.magic.api.oldenLanguage.runes.{SingletonRune, TAction}

object BurnRune extends SingletonRune with TAction {
	def mod = MagicMod

	def name = "a-burn"

	@SideOnly(Side.CLIENT)
	var icon: IIcon = null

	@SideOnly(Side.CLIENT)
	def icons = List(icon)

	@SideOnly(Side.CLIENT)
	def registerIcons(registerIcon: (String) => IIcon) {
		icon = registerIcon(mod.ref.modID + ":runes/burn")
	}

	def actUponBlock(pos: BlockPos) {
		pos.offset(Direction.Up).tryReplaceWith(Blocks.fire)
	}
	def actUponEntity(entity: Entity) {
		entity.setFire(10) // TODO: more depending on something
	}
}