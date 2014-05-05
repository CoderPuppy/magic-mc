package cpup.mc.oldenMagic.api.oldenLanguage

import net.minecraft.entity.Entity
import cpup.mc.lib.util.EntityUtil
import net.minecraftforge.common.IExtendedEntityProperties
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World
import cpup.mc.oldenMagic.OldenMagicMod

class EntityMagicData(var maxPower: Int, var power: Int) extends IExtendedEntityProperties {
	override def init(entity: Entity, world: World) {}

	override def loadNBTData(nbt: NBTTagCompound) {
		maxPower = nbt.getInteger("maxPower")
		power = nbt.getInteger("power")
	}
	override def saveNBTData(nbt: NBTTagCompound) {
		nbt.setInteger("maxPower", maxPower)
		nbt.setInteger("power", power)
	}

	def datas = Map(
		"maxPower" -> maxPower,
		"power"    -> power
	)
	def setData(name: String, value: Int) {
		name match {
			case "maxPower" =>
				maxPower = value

			case "power" =>
				power = value

			case _ =>
				throw new NoSuchFieldException(s"Unknown field: $name")
		}
	}
}

object EntityMagicData {
	def mod = OldenMagicMod

	def get(e: Entity) = {
		EntityUtil.getExtendedData(e, s"${mod.ref.modID}:${classOf[EntityMagicData].getName}", new EntityMagicData(0, 0))
	}
}