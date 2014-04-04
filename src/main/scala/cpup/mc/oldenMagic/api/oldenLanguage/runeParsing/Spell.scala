package cpup.mc.oldenMagic.api.oldenLanguage.runeParsing

import net.minecraft.nbt.NBTTagCompound
import cpup.mc.oldenMagic.api.oldenLanguage.OldenLanguageRegistry
import net.minecraftforge.common.util.Constants
import cpup.mc.lib.util.NBTUtil
import cpup.mc.oldenMagic.OldenMagicMod

case class Spell(action: TVerbRune, targetPath: List[TNounRune]) {
	def writeToNBT(nbt: NBTTagCompound) {
		nbt.setTag("action", OldenLanguageRegistry.writeRuneToNBT(action))
		nbt.setTag("targetPath", NBTUtil.writeList(targetPath.map(OldenLanguageRegistry.writeRuneToNBT)))
	}
}
object Spell {
	def mod = OldenMagicMod

	def readFromNBT(nbt: NBTTagCompound) = try
		Spell(
			action = OldenLanguageRegistry.readRuneFromNBT(nbt.getCompoundTag("action")).asInstanceOf[TVerbRune],
			targetPath = NBTUtil.readList(nbt.getTagList("targetPath", Constants.NBT.TAG_COMPOUND)).map(
				OldenLanguageRegistry.readRuneFromNBT(_).asInstanceOf[TNounRune]
			)
		)
	catch {
		case e: Exception =>
			mod.logger.error(e.toString)
			mod.logger.error(e.getStackTraceString)
			null
	}
}