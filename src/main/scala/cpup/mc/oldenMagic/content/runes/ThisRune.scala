package cpup.mc.oldenMagic.content.runes

import cpw.mods.fml.relauncher.{SideOnly, Side}
import net.minecraft.util.IIcon
import cpup.mc.oldenMagic.MagicMod
import cpup.mc.oldenMagic.api.oldenLanguage.casting.{EntityCaster, BlockTarget, TTarget, TCaster}
import cpup.mc.lib.util.pos.BlockPos
import net.minecraft.util.MovingObjectPosition.MovingObjectType
import net.minecraft.entity.Entity
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.{TTypeNounRune, TNounModifierRune, TNounRune}
import net.minecraft.block.Block
import cpup.mc.oldenMagic.api.oldenLanguage.runes.{InternalRuneType, InternalRune, SingletonRune, TRune}

object ThisRune extends SingletonRune with TNounModifierRune {
	def mod = MagicMod

	def name = "this"
	def modifyNoun(rune: TNounRune) {
		rune match {
			case rune: TTypeNounRune[_, _] =>
				rune.specify(ThisNounRune)
			case _ =>
		}
	}

	@SideOnly(Side.CLIENT)
	var icon: IIcon = null

	@SideOnly(Side.CLIENT)
	def icons = List(icon)

	@SideOnly(Side.CLIENT)
	def registerIcons(registerIcon: (String) => IIcon) {
		icon = registerIcon(s"${mod.ref.modID}:runes/this")
	}
}

object ThisNounRune extends SingletonRune with InternalRune with InternalRuneType with TNounRune {
	def name = "this:noun"

	override def getTargets(caster: TCaster, existing: List[TTarget]) = {
		val mop = caster.mop
		mop.typeOfHit match {
			case MovingObjectType.BLOCK =>
				List(BlockTarget(BlockPos(caster.world, mop.blockX, mop.blockY, mop.blockZ)))

			case MovingObjectType.ENTITY =>
				List(new EntityCaster(mop.entityHit.worldObj, mop.entityHit.getEntityId))

			case _ => List()
		}
	}
}