package cpup.mc.oldenMagic.content.runes

import cpup.mc.oldenMagic.api.oldenLanguage.runes.{TRuneType, TRune}
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.TVerbRune
import cpup.mc.oldenMagic.api.oldenLanguage.casting.{TTarget, CastingContext}
import net.minecraft.entity.Entity
import cpup.mc.lib.util.pos.BlockPos
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.util.IIcon
import cpup.mc.oldenMagic.OldenMagicMod
import net.minecraft.nbt.NBTTagCompound
import cpup.mc.oldenMagic.api.oldenLanguage.PassiveSpellsContext

class ProtectRune extends TRune with TVerbRune {
	def runeType = ProtectRune
	def writeToNBT(nbt: NBTTagCompound) {}

	override def act(context: CastingContext, targets: List[TTarget]) {
		context match {
			case PassiveSpellsContext(player, caster, spell, action: DamageAction) =>
				action.amt = 0
			case _ =>
		}
	}

	def act(context: CastingContext, entity: Entity) {}
	def act(context: CastingContext, pos: BlockPos) {}

	@SideOnly(Side.CLIENT)
	def icons = List(ProtectRune.icon)
}

object ProtectRune extends TRuneType {
	def mod = OldenMagicMod

	def name = s"${mod.ref.modID}:protect"
	def runeClass = classOf[ProtectRune]
	def readFromNBT(nbt: NBTTagCompound) = new ProtectRune

	@SideOnly(Side.CLIENT)
	var icon: IIcon = null
	@SideOnly(Side.CLIENT)
	def registerIcons(registerIcon: (String) => IIcon) {
		icon = registerIcon(s"${mod.ref.modID}:runes/protect")
	}
}