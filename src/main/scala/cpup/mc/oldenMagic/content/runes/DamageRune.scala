package cpup.mc.oldenMagic.content.runes

import cpup.mc.oldenMagic.api.oldenLanguage.runes.{TRuneType, TRune}
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.TVerbRune
import cpup.mc.oldenMagic.OldenMagicMod
import cpup.mc.oldenMagic.api.oldenLanguage.casting.{CastingContext, TCaster}
import net.minecraft.entity.Entity
import net.minecraft.util.{IIcon, DamageSource}
import cpup.mc.lib.util.pos.BlockPos
import cpup.mc.oldenMagic.content.runes.DamageRune.RuneDamageSource
import cpw.mods.fml.relauncher.{Side, SideOnly}
import cpw.mods.fml.relauncher.Side
import net.minecraft.nbt.NBTTagCompound

class DamageRune extends TRune with TVerbRune {
	def mod = OldenMagicMod

	def runeType = DamageRune

	override def act(context: CastingContext, entity: Entity) {
		entity.attackEntityFrom(new RuneDamageSource(context.player, context.caster), Int.MaxValue)
	}
	override def act(context: CastingContext, pos: BlockPos) {}

	@SideOnly(Side.CLIENT)
	def icons = List(DamageRune.icon)

	def writeToNBT(nbt: NBTTagCompound) {}
}

object DamageRune extends TRuneType {
	def mod = OldenMagicMod

	def name = s"${mod.ref.modID}:damage"
	def runeClass = classOf[DamageRune]
	def readFromNBT(nbt: NBTTagCompound) = new DamageRune

	case class RuneDamageSource(player: String, caster: TCaster) extends DamageSource("oldenmagic:damagerune") {

	}

	@SideOnly(Side.CLIENT)
	var icon: IIcon = null

	@SideOnly(Side.CLIENT)
	def registerIcons(registerIcon: (String) => IIcon) {
		icon = registerIcon(s"${mod.ref.modID}:runes/damage")
	}
}
