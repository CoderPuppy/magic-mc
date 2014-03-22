package cpup.mc.magic.content.runes

import cpup.mc.magic.api.oldenLanguage._
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.IIcon
import cpup.mc.magic.MagicMod
import net.minecraft.entity.{Entity, EntityList}
import java.lang.reflect.Constructor
import net.minecraft.world.World
import net.minecraft.world.chunk.IChunkProvider
import java.util
import net.minecraft.entity.item.EntityItem
import cpup.mc.lib.util.GUIUtil

case class EntityTypeRune(name: String) extends TRune {
	val drops = (() => {
		val cla = EntityList.stringToClassMapping.get(name).asInstanceOf[Class[_ <: Entity]]
		val constructor = cla.getConstructors.find((constr: Constructor[_]) => {
			val types = constr.getTypeParameters
			types.length == 1 && types(0) == classOf[World]
		})

		if(constructor.isEmpty) {
			throw new NullPointerException("No constructor: " + name + "(World)")
		}

		val entity = constructor.get.newInstance(null).asInstanceOf[Entity]
		entity.captureDrops = true
		entity.capturedDrops = new util.ArrayList[EntityItem]
		// TODO: obfuscated: func_70628_a
		cla.getMethod("dropFew", java.lang.Boolean.TYPE, java.lang.Integer.TYPE).invoke(entity, true: java.lang.Boolean, 100: java.lang.Integer)
		entity.capturedDrops.toArray.toList.asInstanceOf[List[EntityItem]].map(_.getEntityItem)
	})()

	@SideOnly(Side.CLIENT)
	def icons = List(EntityTypeRune.icon)

	@SideOnly(Side.CLIENT)
	override def render(x: Int, y: Int, width: Int, height: Int) {
		val centerX = x + width / 2
		val centerY = y + height / 2
		val degreesBetween = 360 / drops.size
		val dropWidth = width / 4
		val dropHeight = width / 4
		val radius = width / 4

		var angle = 0
		for((drop, i) <- drops.zipWithIndex) {
			val dropX = centerX + Math.cos(angle) * radius + (dropWidth / 2)
			val dropY = centerY + Math.sin(angle) * radius + (dropHeight / 2)
			GUIUtil.drawItemIconAt(drop.getIconIndex, dropX, dropY, 0, dropWidth, dropHeight)

			angle += degreesBetween
		}

		super.render(x, y, width, height)
	}

	def runeType = EntityTypeRune
	def writeToNBT(nbt: NBTTagCompound) {
		nbt.setString("name", name)
	}
}

object EntityTypeRune extends TRuneType {
	def mod = MagicMod

	def runeClass = classOf[EntityTypeRune]
	def readFromNBT(nbt: NBTTagCompound) = EntityTypeRune(nbt.getString("name"))

	@SideOnly(Side.CLIENT)
	var icon: IIcon = null

	@SideOnly(Side.CLIENT)
	def registerIcons(registerIcon: (String) => IIcon) {
		icon = registerIcon(mod.ref.modID + ":runes/entity")
	}
}

object EntityTypeTransform extends TTransform {
	def transform(context: TContext, rune: TextRune) = EntityTypeRune(rune.txt)
}