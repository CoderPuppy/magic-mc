package cpup.mc.oldenMagic.api.oldenLanguage.runeParsing

import net.minecraft.nbt.NBTTagCompound
import cpup.mc.oldenMagic.api.oldenLanguage.OldenLanguageRegistry
import net.minecraftforge.common.util.Constants
import cpup.mc.lib.util.NBTUtil

case class Spell(action: TActionRune, targetPath: List[TNounRune])
object Spell {
	def readFromNBT(nbt: NBTTagCompound) = try {
		Spell(
			OldenLanguageRegistry.readRuneFromNBT(nbt.getCompoundTag("action")).asInstanceOf[TActionRune],
			NBTUtil.readList(nbt.getTagList("targetPath", Constants.NBT.TAG_COMPOUND)).map(OldenLanguageRegistry.readRuneFromNBT(_).asInstanceOf[TNounRune])
		)
	} catch {
		case _: Exception => null
	}
}