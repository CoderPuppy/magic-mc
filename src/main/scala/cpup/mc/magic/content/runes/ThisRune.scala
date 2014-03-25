package cpup.mc.magic.content.runes

import cpup.mc.magic.api.oldenLanguage.runes.{TNoun, SingletonRune}
import cpw.mods.fml.relauncher.{SideOnly, Side}
import net.minecraft.util.IIcon
import cpup.mc.magic.MagicMod
import cpup.mc.magic.api.oldenLanguage.casters.TCaster
import cpup.mc.lib.util.pos.BlockPos
import net.minecraft.util.MovingObjectPosition.MovingObjectType
import net.minecraft.entity.Entity

object ThisRune extends SingletonRune with TNoun {
	def mod = MagicMod

	def name = "this-pronoun"

	def getBlocks(caster: TCaster, origin: BlockPos) =if(caster.mop.typeOfHit == MovingObjectType.BLOCK) {
		List(BlockPos(caster.world, caster.mop.blockX, caster.mop.blockY, caster.mop.blockZ))
	} else { List() }

	def getEntities(caster: TCaster, origin: Entity) = if(caster.mop.typeOfHit == MovingObjectType.ENTITY) {
		List(caster.mop.entityHit)
	} else { List() }

	@SideOnly(Side.CLIENT)
	var icon: IIcon = null

	@SideOnly(Side.CLIENT)
	def icons = List(icon)

	@SideOnly(Side.CLIENT)
	def registerIcons(registerIcon: (String) => IIcon) {
		icon = registerIcon(s"${mod.ref.modID}:runes/this")
	}
}