package cpup.mc.magic.api.oldenLanguage

import cpup.mc.magic.api.oldenLanguage.textParsing.Context
import cpup.mc.magic.api.oldenLanguage.runes.{TRune, TRuneType}
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

	protected[magic] def finish {
		_runeTypes = _runeTypes.sortBy((runeType) => {
			runeType.runeClass.getCanonicalName + ":" + runeType.getClass.getCanonicalName + ":" + runeType.name
		})
	}

	def writeRuneToNBT(rune: TRune, nbt: NBTTagCompound) {
		if(!_runeTypes.contains(rune.runeType)) {
			throw new NoSuchElementException("Attempt to write an unregistered rune to nbt: " + rune)
		}

		nbt.setInteger("olden:runeID", _runeTypes.indexOf(rune.runeType))
		rune.writeToNBT(nbt)
	}

	def readRuneFromNBT(nbt: NBTTagCompound) = {
		val id = nbt.getInteger("olden:runeID")

		if(_runeTypes.size < id) {
			_runeTypes(id).readFromNBT(nbt)
		} else { null }
	}
}
