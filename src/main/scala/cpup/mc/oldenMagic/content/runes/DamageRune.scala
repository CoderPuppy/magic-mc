package cpup.mc.oldenMagic.content.runes

import cpup.mc.oldenMagic.api.oldenLanguage.runes.{TRuneType, TRune}
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.TVerbRune
import cpup.mc.oldenMagic.OldenMagicMod
import cpup.mc.oldenMagic.api.oldenLanguage.casting.{CastingRegistry, TAction, CastingContext, TCaster}
import net.minecraft.entity.Entity
import net.minecraft.util.{IIcon, DamageSource}
import cpup.mc.lib.util.pos.BlockPos
import cpup.mc.oldenMagic.content.runes.DamageRune.RuneDamageSource
import cpw.mods.fml.relauncher.SideOnly
import cpw.mods.fml.relauncher.Side
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.event.entity.living.LivingHurtEvent
import cpup.mc.oldenMagic.content.targets.EntityCaster
import net.minecraft.client.renderer.texture.IIconRegister
import cpup.mc.lib.targeting.TargetingRegistry

class DamageRune extends TRune with TVerbRune {
	def mod = OldenMagicMod

	def runeType = DamageRune

	override def act(context: CastingContext, entity: Entity) {
		entity.attackEntityFrom(new RuneDamageSource(context.player, context.caster), 4)
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
	def registerIcons(register: IIconRegister) {
		icon = register.registerIcon(s"${mod.ref.modID}:runes/damage")
	}
}

class DamageAction(val e: LivingHurtEvent) extends TAction {
	def runeType = DamageRune

	val affectedTarget = TargetingRegistry.wrapEntity(e.entity).flatMap(CastingRegistry.wrap(_)).get

	def src = e.source

	def amt = e.ammount
	def amt_=(newAmt: Float) = {
		e.ammount = newAmt
		newAmt
	}
}