package cpup.mc.oldenMagic.content.runes

import cpup.mc.oldenMagic.api.oldenLanguage.casting.conditions.{TConditionFilter, TCondition}
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.TConditionRune
import cpup.mc.oldenMagic.api.oldenLanguage.runes.TRuneType
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.client.renderer.texture.IIconRegister
import cpup.mc.oldenMagic.OldenMagicMod
import cpw.mods.fml.relauncher.{Side, SideOnly}
import cpup.mc.oldenMagic.api.oldenLanguage.OldenLanguageRegistry
import net.minecraft.util.IIcon

class AndConditionFilterRune(val a: TConditionRune, val b: TConditionRune) extends TConditionRune {
	override def runeType = AndConditionFilterRune

	override def writeToNBT(nbt: NBTTagCompound) {
		nbt.setTag("a", OldenLanguageRegistry.writeRuneToNBT(a))
		nbt.setTag("b", OldenLanguageRegistry.writeRuneToNBT(b))
	}

	@SideOnly(Side.CLIENT)
	override def icons = List(AndConditionFilterRune.icon)

	override def conditionTypes = a.conditionTypes union b.conditionTypes
	override def filter(conditions: Set[TCondition]) = a.filter(conditions) && b.filter(conditions)
}

object AndConditionFilterRune extends TRuneType {
	def mod = OldenMagicMod

	override def name = s"${mod.ref.modID}:condition/and"
	override def runeClass = classOf[AndConditionFilterRune]

	override def readFromNBT(nbt: NBTTagCompound) = new AndConditionFilterRune(
		OldenLanguageRegistry.readRuneFromNBT(nbt.getCompoundTag("a")) match {
			case rune: TConditionRune => rune
			case _ =>
				throw new ClassCastException("a isn't a TConditionRune")
		},
		OldenLanguageRegistry.readRuneFromNBT(nbt.getCompoundTag("b")) match {
			case rune: TConditionRune => rune
			case _ =>
				throw new ClassCastException("b isn't a TConditionRune")
		}
	)

	@SideOnly(Side.CLIENT)
	var icon: IIcon = null

	@SideOnly(Side.CLIENT)
	override def registerIcons(register: IIconRegister) {
		icon = register.registerIcon(s"${mod.ref.modID}:runes/condition/and")
	}
}