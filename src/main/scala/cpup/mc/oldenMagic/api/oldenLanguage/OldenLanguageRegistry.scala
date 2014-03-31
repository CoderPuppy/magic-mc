package cpup.mc.oldenMagic.api.oldenLanguage

import cpup.mc.oldenMagic.api.oldenLanguage.textParsing.Context
import cpup.mc.oldenMagic.api.oldenLanguage.runes.{TRuneType, TRune}
import net.minecraft.nbt.NBTTagCompound
import cpup.mc.oldenMagic.api.oldenLanguage.casting.{TTarget, TTargetType}

object OldenLanguageRegistry {
	protected var _runeTypes = List[TRuneType]()
	def runeTypes = _runeTypes
	def registerRune(runeType: TRuneType) {
		_runeTypes ++= List(runeType)
	}

	protected var _targetTypes = List[TTargetType]()
	def targetTypes = _targetTypes
	def registerTarget(targetType: TTargetType) {
		_targetTypes ++= List(targetType)
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

		_targetTypes = _targetTypes.sortBy((targetType) =>
			targetType.targetClass.getCanonicalName + ":" + targetType.getClass.getCanonicalName + ":" + targetType.name
		)
	}

	def writeRuneToNBT(nbt: NBTTagCompound, rune: TRune) {
		nbt.setInteger("olden:rune-id", _runeTypes.indexOf(rune.runeType))
		rune.writeToNBT(nbt)
	}

	def writeRuneToNBT(rune: TRune): NBTTagCompound = {
		val nbt = new NBTTagCompound
		writeRuneToNBT(nbt, rune)
		nbt
	}

	def readRuneFromNBT(nbt: NBTTagCompound) = {
		val id = nbt.getInteger("olden:rune-id")
		if(_runeTypes.isDefinedAt(id)) {
			_runeTypes(id).readFromNBT(nbt)
		} else { null }
	}


	def writeTargetToNBT(nbt: NBTTagCompound, target: TTarget) {
		nbt.setInteger("olden:target-id", _targetTypes.indexOf(target.targetType))
		target.writeToNBT(nbt)
	}

	def writeTargetToNBT(target: TTarget): NBTTagCompound = {
		val nbt = new NBTTagCompound
		writeTargetToNBT(nbt, target)
		nbt
	}

	def readTargetFromNBT(nbt: NBTTagCompound) = {
		val id = nbt.getInteger("olden:target-id")
		if(_targetTypes.isDefinedAt(id)) {
			_targetTypes(id).readFromNBT(nbt)
		} else { null }
	}
}