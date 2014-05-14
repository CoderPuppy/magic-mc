package cpup.mc.oldenMagic.content.runes

import cpup.mc.oldenMagic.api.oldenLanguage.casting.conditions.{ItemCondition, TCondition}
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.TConditionRune
import cpup.mc.oldenMagic.api.oldenLanguage.runes.TRuneType
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.client.renderer.texture.IIconRegister
import cpup.mc.oldenMagic.OldenMagicMod
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.util.IIcon

class ItemConditionFilterRune(var name: Symbol, var unlocalizedName: String) extends TConditionRune {
	def mod = OldenMagicMod

	override def runeType = ItemConditionFilterRune

	override def writeToNBT(nbt: NBTTagCompound) {
		nbt.setString("name", name.name)
		nbt.setString("unlocalizedName", unlocalizedName)
	}

	@SideOnly(Side.CLIENT)
	override def icons = List(ItemConditionFilterRune.icon)

	override def conditionTypes = Set(ItemCondition)
	override def filter(conditions: Set[TCondition]) = conditions.exists {
		case cond: ItemCondition =>
			cond.name == name && cond.stack.getUnlocalizedName == unlocalizedName

		case _ => false
	}
}

object ItemConditionFilterRune extends TRuneType {
	def mod = OldenMagicMod

	override def name = s"${mod.ref.modID}:condition/item"
	override def runeClass = classOf[ItemConditionFilterRune]

	override def readFromNBT(nbt: NBTTagCompound) = new ItemConditionFilterRune(
		Symbol(nbt.getString("name")),
		nbt.getString("unlocalizedName")
	)

	@SideOnly(Side.CLIENT)
	var icon: IIcon = null

	@SideOnly(Side.CLIENT)
	override def registerIcons(register: IIconRegister) {
		icon = register.registerIcon(s"${mod.ref.modID}:runes/condition/item")
	}
}