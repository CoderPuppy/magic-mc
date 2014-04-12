package cpup.mc.oldenMagic.content.runes

import cpw.mods.fml.relauncher.{SideOnly, Side}
import net.minecraft.util.IIcon
import cpup.mc.oldenMagic.OldenMagicMod
import cpup.mc.oldenMagic.api.oldenLanguage.casting._
import net.minecraft.util.MovingObjectPosition.MovingObjectType
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.{TTypeNounRune, TNounModifierRune, TNounRune}
import cpup.mc.oldenMagic.api.oldenLanguage.runes.{InternalRuneType, InternalRune, SingletonRune}
import cpup.mc.lib.util.pos.BlockPos
import cpup.mc.oldenMagic.content.targets.{EntityCaster, BlockTarget}

object ThisRune extends SingletonRune with TNounModifierRune {
	def mod = OldenMagicMod

	def name = s"${mod.ref.modID}:this"
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
	def mod = OldenMagicMod

	def name = s"${mod.ref.modID}:this:noun"

	def getTarget(context: CastingContext) = {
		val mop = context.caster.mop
		mop.typeOfHit match {
			case MovingObjectType.BLOCK =>
				BlockTarget(BlockPos(context.caster.world, mop.blockX, mop.blockY, mop.blockZ))

			case MovingObjectType.ENTITY =>
				new EntityCaster(mop.entityHit)

			case _ => null
		}
	}

	override def getTargets(context: CastingContext, existing: List[TTarget]) = getTarget(context) match {
		case target: TTarget => List(target)
		case null => List()
	}

	override def filter(context: CastingContext, prev: List[TNounRune], testingTarget: TTarget) = testingTarget.sameObj(getTarget(context))
}