package cpup.mc.oldenMagic.content.runes

import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.TVerbRune
import cpup.mc.oldenMagic.api.oldenLanguage.runes.TRuneType
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.util.IIcon
import cpup.mc.oldenMagic.OldenMagicMod
import net.minecraft.nbt.NBTTagCompound
import cpup.mc.oldenMagic.api.oldenLanguage.casting.{EntityCaster, TCancellableAction, TAction, CastingContext}
import cpup.mc.lib.util.pos.BlockPos
import net.minecraft.entity.{EntityLiving, Entity}
import cpup.mc.oldenMagic.api.oldenLanguage.textParsing.{TextRune, TContext, TTransform}
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent

class SeenRune extends TVerbRune {
	def runeType = SeenRune
	def writeToNBT(nbt: NBTTagCompound) {}

	def act(context: CastingContext, pos: BlockPos) {}
	def act(context: CastingContext, entity: Entity) {}

	@SideOnly(Side.CLIENT)
	def icons = List(SeenRune.icon)
}
object SeenRune extends TRuneType {
	def mod = OldenMagicMod

	def name = s"${mod.ref.modID}:seen"
	def runeClass = classOf[SeenRune]
	def readFromNBT(nbt: NBTTagCompound) = new SeenRune

	@SideOnly(Side.CLIENT)
	var icon: IIcon = null
	@SideOnly(Side.CLIENT)
	def registerIcons(registerIcon: (String) => IIcon) {
		icon = registerIcon(s"${mod.ref.modID}:runes/seen")
	}
}

object SeenTransform extends TTransform {
	def transform(context: TContext, rune: TextRune) = new SeenRune
}

class SeenAction(val e: LivingSetAttackTargetEvent) extends TAction with TCancellableAction {
	val affectedTarget = new EntityCaster(e.entity)

	override def runeType = SeenRune

	def cancel {
		e.setCanceled(true)
//		e.entityLiving.setRevengeTarget(null)
//		e.entityLiving.setLastAttacker(null)

		if(e.entityLiving.isInstanceOf[EntityLiving]) {
			e.entityLiving.asInstanceOf[EntityLiving].setAttackTarget(null)
		}
	}

	def uncancel {
		e.setCanceled(false)

//		e.entityLiving.setRevengeTarget(e.target)
//		e.entityLiving.setLastAttacker(e.target)

		if(e.entityLiving.isInstanceOf[EntityLiving]) {
			e.entityLiving.asInstanceOf[EntityLiving].setAttackTarget(e.target)
		}
	}

	def isCanceled = e.isCanceled
}