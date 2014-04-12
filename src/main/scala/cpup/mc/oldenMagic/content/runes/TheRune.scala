package cpup.mc.oldenMagic.content.runes

import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.{TTypeNounRune, TNounRune, TNounModifierRune}
import cpup.mc.oldenMagic.api.oldenLanguage.runes.{InternalRuneType, InternalRune, SingletonRune}
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.util.IIcon
import net.minecraft.client.renderer.texture.IIconRegister
import cpup.mc.oldenMagic.OldenMagicMod
import net.minecraft.entity.Entity
import net.minecraft.block.Block
import cpup.mc.oldenMagic.api.oldenLanguage.casting.{TTarget, CastingContext}

object TheRune extends SingletonRune with TNounModifierRune {
	def mod = OldenMagicMod

	def name = s"${mod.ref.modID}:the"

	def modifyNoun(rune: TNounRune) {
		rune match {
			case rune: TTypeNounRune[_, _] =>
				rune.specify(TheNounRune)
			case _ =>
		}
	}

	@SideOnly(Side.CLIENT)
	def icons = List(icon)
	@SideOnly(Side.CLIENT)
	var icon: IIcon = null
	@SideOnly(Side.CLIENT)
	def registerIcons(register: IIconRegister) {
		icon = register.registerIcon(s"${mod.ref.modID}:runes/the")
	}
}

object TheNounRune extends SingletonRune with InternalRune with InternalRuneType with TNounRune {
	def mod = OldenMagicMod

	def name = s"${mod.ref.modID}:the:noun"

	def getTargets(context: CastingContext, existing: List[TTarget]) = context.it
	def filter(context: CastingContext, prev: List[TNounRune], target: TTarget) = context.it.contains(target)
}