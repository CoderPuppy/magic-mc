package cpup.mc.oldenMagic.api.oldenLanguage.casting

import net.minecraft.util.MovingObjectPosition
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.Spell
import net.minecraft.nbt.NBTTagCompound

trait TCaster extends TTarget {
	def mop: MovingObjectPosition
}