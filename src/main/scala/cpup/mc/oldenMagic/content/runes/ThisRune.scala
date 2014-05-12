package cpup.mc.oldenMagic.content.runes

import cpw.mods.fml.relauncher.{SideOnly, Side}
import net.minecraft.util.IIcon
import cpup.mc.oldenMagic.OldenMagicMod
import cpup.mc.oldenMagic.api.oldenLanguage.casting._
import net.minecraft.util.MovingObjectPosition.MovingObjectType
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.{TTypeNounRune, TNounModifierRune, TNounRune}
import cpup.mc.oldenMagic.api.oldenLanguage.runes.{InternalRuneType, InternalRune, SingletonRune}
import cpup.mc.lib.util.pos.BlockPos
import cpup.mc.oldenMagic.content.targets.EntityCaster
import net.minecraft.client.renderer.texture.IIconRegister
import cpup.mc.lib.targeting.{BlockTarget, TTarget}

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
	def registerIcons(register: IIconRegister) {
		icon = register.registerIcon(s"${mod.ref.modID}:runes/this")
	}
}

object ThisNounRune extends SingletonRune with InternalRune with InternalRuneType with TNounRune {
	def mod = OldenMagicMod

	def name = s"${mod.ref.modID}:this:noun"

	def getTarget(context: CastingContext) = context.caster.mop.flatMap((mop) => {
		mop.typeOfHit match {
			case MovingObjectType.BLOCK =>
				context.caster.world.map((world) =>
					BlockTarget(BlockPos(world, mop.blockX, mop.blockY, mop.blockZ))
				)

			case MovingObjectType.ENTITY =>
				EntityCaster.from(mop.entityHit)

			case _ => None
		}
	})

	override def getTargets(context: CastingContext, existing: List[TTarget]) = getTarget(context).toList

	override def filter(context: CastingContext, prev: List[TNounRune], testingTarget: TTarget) = getTarget(context).exists(testingTarget.sameObj(_))
}