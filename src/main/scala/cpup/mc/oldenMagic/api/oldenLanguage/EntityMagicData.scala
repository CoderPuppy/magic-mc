package cpup.mc.oldenMagic.api.oldenLanguage

import net.minecraft.entity.Entity
import cpup.mc.lib.util.EntityUtil
import net.minecraftforge.common.IExtendedEntityProperties
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World
import cpup.mc.oldenMagic.OldenMagicMod

class EntityMagicData(var naturalPower: Int = 0, var maxSafePower: Int = 0, var power: Int = 0) extends IExtendedEntityProperties {
	override def init(entity: Entity, world: World) {}

	override def loadNBTData(nbt: NBTTagCompound) {
		naturalPower = nbt.getInteger("naturalPower")
		maxSafePower = nbt.getInteger("maxPower")
		power = nbt.getInteger("power")
	}
	override def saveNBTData(nbt: NBTTagCompound) {
		nbt.setInteger("naturalPower", naturalPower)
		nbt.setInteger("maxPower", maxSafePower)
		nbt.setInteger("power", power)
	}

	def datas = Map(
		"naturalPower" -> naturalPower,
		"maxPower" -> maxSafePower,
		"power"    -> power
	)
	def setData(name: String, value: Int) {
		name match {
			case "naturalPower" =>
				naturalPower = value

			case "maxPower" =>
				maxSafePower = value

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
		EntityUtil.getExtendedData(e, s"${mod.ref.modID}:${classOf[EntityMagicData].getName}", new EntityMagicData())
	}
}