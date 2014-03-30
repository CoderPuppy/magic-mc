package cpup.mc.oldenMagic.api.oldenLanguage.runeParsing

import cpup.mc.oldenMagic.MagicMod
import cpup.mc.oldenMagic.api.oldenLanguage.runes._
import scala.collection.mutable
import scala.collection.mutable.ListBuffer

// TODO: limit what runes a player can use
class RuneParser {
	def mod = MagicMod

	var spell: Spell = null

	override def toString = "RuneParser {\n  " + List(
		"spell = " + spell,
		"modeStack = " + modeStack.map("{\n    " + _ + "  \n}").mkString(",\n  ")
	).mkString(",\n  ") + "\n}"

	val modeStack = new mutable.Stack[RuneParserMode]

	def mode = if(modeStack.isEmpty) null else modeStack.head

	def switchMode(newMode: RuneParserMode) = {
		leave
		enter(newMode)
		this
	}
	def mode_=(newMode: RuneParserMode) = {
		leave
		enter(newMode)
		newMode
	}

	def enter(newMode: RuneParserMode) {
		Option(mode).foreach(_.onChild(this, newMode))
		modeStack.push(newMode)
		newMode.onEnter(this)
	}
	def leave {
		val old = modeStack.pop
		Option(old).foreach(_.onLeave(this))
		Option(mode).foreach(_.onReturn(this, old))
	}

	enter(StartMode)

	def finish {
		while(!modeStack.isEmpty) {
			leave
		}
	}

	def handle(rune: TRune) {
		println(s"handling rune: $rune in mode: $mode, stack: ${modeStack.mkString(", ")}")
		if(mode == null) {
			unhandledRune(rune)
		} else {
			mode.handle(this, rune)
		}
	}

	def unhandledRune(rune: TRune) {
		// TODO: instability
		mod.logger.warn(s"Unhandled rune: $rune in mode: $mode, stack: $modeStack")
	}

	def handle(runes: List[TRune]) { runes.foreach(handle) }
}

trait RuneParserMode {
	def onEnter(parser: RuneParser) {}
	def handle(parser: RuneParser, rune: TRune)
	def onChild(parser: RuneParser, child: RuneParserMode) {}
	def onReturn(parser: RuneParser, child: RuneParserMode) {}
	def onLeave(parser: RuneParser) {}
}

case object StartMode extends RuneParserMode {
	def handle(parser: RuneParser, rune: TRune) { parser.unhandledRune(rune) }

	override def onEnter(parser: RuneParser) {
		parser.enter(new SpellMode)
	}

	override def onReturn(parser: RuneParser, child: RuneParserMode) {
		child match {
			case mode: SpellMode =>
				parser.spell = mode.spell
		}
	}
}

class SpellMode extends RuneParserMode {
	var action: TAction = null
	val targetPath = new ListBuffer[TNoun]()
	def spell = Spell(action, targetPath.toList)

	def handle(parser: RuneParser, rune: TRune) {
		rune match {
			case conjunction: TSpellConjunction =>
				parser.enter(new SpellConjunctionMode(conjunction))
			case _ =>
		}
	}

	override def onEnter(parser: RuneParser) {
		parser.enter(new ActionMode)
	}

	override def onReturn(parser: RuneParser, child: RuneParserMode) {
		child match {
			case mode: ActionMode =>
				action = mode.action
				parser.enter(new TargetMode(targetPath, true))
			case _ =>
		}
	}

	override def toString = s"SpellMode { action = ${action}, targetPath = [ ${targetPath.mkString(", ")} ] }"
}
case class SpellConjunctionMode(conjunction: TSpellConjunction) extends SpellMode {
	override def toString = s"${super.toString}, conjunction = $conjunction"
}

class TargetMode(var targetPath: ListBuffer[TNoun], val canHavePrepositions: Boolean) extends RuneParserMode {
	def this(canHavePrepositions: Boolean) {
		this(new ListBuffer[TNoun], canHavePrepositions)
	}

	def handle(parser: RuneParser, rune: TRune) {
		rune match {
			case _: TNoun | _: TNounModifier =>
				parser.enter(new NounMode(true))
				parser.handle(rune)

			case conjunction: TNounConjunction if !targetPath.isEmpty =>
				parser.enter(new NounConjunctionMode(conjunction))

			case _ =>
				parser.leave
				parser.handle(rune)
		}
	}

	override def onReturn(parser: RuneParser, child: RuneParserMode) {
		super.onReturn(parser, child)

		child match {
			case mode: NounMode =>
				targetPath += mode.noun

			case mode: NounConjunctionMode =>
				targetPath.clear
				for(noun <- mode.conjunction.combineNouns(targetPath.toList, mode.targetPath.toList)) {
					targetPath += noun
				}
		}
	}

	override def toString = "TargetMode { targetPath = [ " + targetPath.mkString(", ") + " ] }"
}

class NounMode(val canHavePrepositions: Boolean) extends RuneParserMode {
	var noun: TNoun = null
	val modifiers = new ListBuffer[TNounModifier]()

	def handle(parser: RuneParser, rune: TRune) {
		rune match {
			case mod: TNounModifier if noun == null =>
				modifiers += mod

			case _noun: TNoun if noun == null =>
				noun = _noun

			case preposition: TNounPreposition if noun != null && canHavePrepositions =>
				parser.enter(new NounPrepositionMode(preposition))

			case _ =>
				parser.leave
				parser.handle(rune)
		}
	}


	override def onReturn(parser: RuneParser, child: RuneParserMode) {
		child match {
			case mode: NounPrepositionMode =>
				mode.preposition.createNounModifier(mode.targetPath.toList).modifyNoun(noun)
		}
	}

	override def toString = s"NounMode { noun = $noun, modifiers = $modifiers }"
}
class NounConjunctionMode(val conjunction: TNounConjunction) extends TargetMode(true) {
	override def toString = s"${super.toString}, conjunction = $conjunction"
}
class NounPrepositionMode(val preposition: TNounPreposition  ) extends TargetMode(false) {
	override def toString = s"${super.toString}, preposition = $preposition"
}

class ActionMode extends RuneParserMode {
	var action: TAction = null
	val modifiers = new ListBuffer[TActionModifier]

	def handle(parser: RuneParser, rune: TRune) {
		rune match {
			case mod: TActionModifier if action == null =>
				modifiers += mod
			case act: TAction if action == null =>
				action = act

				for(mod <- modifiers) {
					mod.modifyAction(action)
				}
			case preposition: TActionPreposition if action != null =>
				parser.enter(new ActionPrepositionMode(preposition))
			case conjunction: TActionConjunction if action != null =>
				parser.enter(new ActionConjunctionMode(conjunction))
			case _ =>
				parser.leave
				parser.handle(rune)
		}
	}

	override def onReturn(parser: RuneParser, child: RuneParserMode) {
		child match {
			case mode: ActionConjunctionMode =>
				action = mode.conjunction.combineActions(action, mode.action)

			case mode: ActionPrepositionMode =>
				mode.preposition.createActionModifier(mode.targetPath.toList).modifyAction(action)
		}
	}
}
class ActionConjunctionMode(val conjunction: TActionConjunction) extends ActionMode {
	override def toString = s"${super.toString}, conjunction = $conjunction"
}
class ActionPrepositionMode(val preposition: TActionPreposition) extends TargetMode(false) {
	// maybe allow preposition
	override def toString = s"${super.toString}, preposition = $preposition"
}