package cpup.mc.magic.content.runes

import cpup.mc.magic.api.oldenLanguage._
import cpw.mods.fml.relauncher.{SideOnly, Side}
import net.minecraft.util.IIcon
import cpup.mc.magic.MagicMod
import net.minecraft.nbt.NBTTagCompound
import cpup.mc.magic.api.oldenLanguage.textParsing.{TContext, TTransform, TextRune}
import cpup.mc.magic.api.oldenLanguage.runeParsing.ActionRune
import cpup.mc.lib.util.pos.BlockPos
import net.minecraft.entity.Entity
import cpup.mc.lib.util.Direction
import net.minecraft.init.Blocks

object BurnRune extends ActionRune with TRune with TRuneType {
	def mod = MagicMod

	@SideOnly(Side.CLIENT)
	var icon: IIcon = null

	@SideOnly(Side.CLIENT)
	def icons = List(icon)

	@SideOnly(Side.CLIENT)
	def registerIcons(registerIcon: (String) => IIcon) {
		icon = registerIcon(mod.ref.modID + ":runes/burn")
	}

	def runeType = this
	def runeClass = getClass

	def writeToNBT(nbt: NBTTagCompound){}
	def readFromNBT(nbt: NBTTagCompound) = this

	def actUponBlock(pos: BlockPos) {
		pos.offset(Direction.Up).tryReplaceWith(Blocks.fire)
	}
	def actUponEntity(entity: Entity) {
		entity.setFire(10) // TODO: more depending on something
	}
}

object BurnTransform extends TTransform {
	def transform(context: TContext, rune: TextRune) = BurnRune
}