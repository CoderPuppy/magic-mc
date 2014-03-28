package cpup.mc.magic.content.runes

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.util.IIcon
import cpup.mc.magic.MagicMod
import net.minecraft.entity.{EntityLiving, EntityLivingBase, Entity, EntityList}
import java.lang.reflect.Constructor
import net.minecraft.world.World
import java.util
import net.minecraft.entity.item.EntityItem
import cpup.mc.lib.util.GUIUtil
import net.minecraft.item.{Item, ItemStack}
import scala.collection.mutable.ListBuffer
import cpup.mc.magic.api.oldenLanguage.textParsing.{TContext, TextRune, TTransform}
import cpup.mc.magic.api.oldenLanguage.runes.{TNoun, TRune, TRuneType}
import cpup.mc.magic.api.oldenLanguage.casters.TCaster
import cpup.mc.lib.util.pos.BlockPos

case class EntityTypeRune(name: String) extends TRune with TNoun {
	val cla = EntityList.stringToClassMapping.get(name).asInstanceOf[Class[_ <: Entity]]

	def getBlocks(caster: TCaster, existing: List[BlockPos]) = List()
	def getEntities(caster: TCaster, existing: List[Entity]) = exisiting.filter(cla.isInstance(_))

	val drops = (() => {
		val constructor = cla.getConstructors.find((constr: Constructor[_]) => {
			val types = constr.getParameterTypes
			types.length == 1 && types(0) == classOf[World]
		})

		if(constructor.isEmpty) {
			throw new NullPointerException("No constructor: " + name + "(World)")
		}

		val entity = constructor.get.newInstance(null).asInstanceOf[Entity]
		entity.captureDrops = true
		entity.capturedDrops = new util.ArrayList[EntityItem]

		EntityTypeRune.dropFew.invoke(entity, true: java.lang.Boolean, 100: java.lang.Integer)
		var drops = entity.capturedDrops.toArray.toList.asInstanceOf[List[EntityItem]].map(_.getEntityItem)
		val droppedItem = EntityTypeRune.getDropItem.invoke(entity).asInstanceOf[Item]

		if(droppedItem != null) {
			drops ++= List(new ItemStack(droppedItem))
		}

		var existing = Set[String]()
		val newDrops = new ListBuffer[ItemStack]

		for(drop <- drops) {
			val key = drop.getUnlocalizedName

			if(!existing.contains(key)) {
				newDrops += drop
			}

			existing += key
		}

		newDrops.toList
	})()

	@SideOnly(Side.CLIENT)
	def icons = List(EntityTypeRune.icon)

	@SideOnly(Side.CLIENT)
	override def render(x: Int, y: Int, width: Int, height: Int) {
		if(drops.size > 0) {
			val centerX = x + width / 2.0
			val centerY = y + height / 2.0
			val degreesBetween = 360 / drops.size
			val dropWidth = width / 3.0
			val dropHeight = width / 3.0
			val radius = width / 2.5

			var angle = 0
			for(drop <- drops) {
				val dropX = centerX + (Math.cos(Math.toRadians(angle)) * radius) - (dropWidth / 2)
				val dropY = centerY + (Math.sin(Math.toRadians(angle)) * radius) - (dropHeight / 2)

				GUIUtil.drawItemIconAt(drop.getIconIndex, dropX, dropY, 0, dropWidth, dropHeight)

				angle += degreesBetween
			}
		}

		super.render(x, y, width, height)
	}

	def runeType = EntityTypeRune
}

object EntityTypeRune extends TRuneType {
	def mod = MagicMod

	// TODO: obfuscated: func_70628_a
	val dropFew = classOf[EntityLivingBase].getDeclaredMethod("dropFewItems", java.lang.Boolean.TYPE, java.lang.Integer.TYPE)
	dropFew.setAccessible(true)

	// TODO: obfuscated: func_146068_u
	val getDropItem = classOf[EntityLiving].getDeclaredMethod("getDropItem")
	getDropItem.setAccessible(true)

	def runeClass = classOf[EntityTypeRune]

	@SideOnly(Side.CLIENT)
	var icon: IIcon = null

	@SideOnly(Side.CLIENT)
	def registerIcons(registerIcon: (String) => IIcon) {
		icon = registerIcon(mod.ref.modID + ":runes/entity")
	}
}

object EntityTypeTransform extends TTransform {
	def transform(context: TContext, rune: TextRune) = EntityTypeRune(rune.text)
}