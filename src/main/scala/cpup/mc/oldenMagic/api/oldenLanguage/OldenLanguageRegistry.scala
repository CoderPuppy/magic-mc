package cpup.mc.oldenMagic.api.oldenLanguage

import cpup.mc.oldenMagic.api.oldenLanguage.textParsing.Context
import cpup.mc.oldenMagic.api.oldenLanguage.runes.{TRuneType, TRune}
import net.minecraft.nbt.NBTTagCompound

object OldenLanguageRegistry {
	protected var _runeTypes = List[TRuneType]()
	def runeTypes = _runeTypes

	def registerRune(runeType: TRuneType) {
		_runeTypes ++= List(runeType)
	}

	protected var _rootContextTransformers = List[(Context) => Unit]()
	def rootContextTransformers = _rootContextTransformers

	def registerRootContextTransformer(transformer: (Context) => Unit) {
		_rootContextTransformers ++= List(transformer)
	}

	protected[oldenMagic] def finish {
		_runeTypes = _runeTypes.sortBy((runeType) => {
			runeType.runeClass.getCanonicalName + ":" + runeType.getClass.getCanonicalName + ":" + runeType.name
		})
	}

	def writeRuneToNBT(nbt: NBTTagCompound, rune: TRune) {
		nbt.setInteger("olden:rune-id", _runeTypes.indexOf(rune.runeType))
		rune.writeToNBT(nbt)
	}

	def readRuneFromNBT(nbt: NBTTagCompound) = {
		val id = nbt.getInteger("olden:rune-id")
		if(_runeTypes.isDefinedAt(id)) {
			_runeTypes(id).readFromNBT(nbt)
		} else { null }
	}
}