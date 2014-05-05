package cpup.mc.oldenMagic.api.oldenLanguage

import net.minecraft.entity.Entity
import cpup.mc.lib.util.EntityUtil
import net.minecraftforge.common.IExtendedEntityProperties
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World
import cpup.mc.oldenMagic.OldenMagicMod

class EntityPowerData(var level: Int, var power: Int) extends IExtendedEntityProperties {
	override def init(entity: Entity, world: World) {}

	override def loadNBTData(nbt: NBTTagCompound) {
		level = nbt.getInteger("level")
		power = nbt.getInteger("power")
	}
	override def saveNBTData(nbt: NBTTagCompound) {
		nbt.setInteger("level", level)
		nbt.setInteger("power", power)
	}
}

object EntityPowerData {
	def mod = OldenMagicMod

	def get(e: Entity) = {
		EntityUtil.getExtendedData(e, s"${mod.ref.modID}:${classOf[EntityPowerData].getName}", new EntityPowerData(0, 0))
	}
}