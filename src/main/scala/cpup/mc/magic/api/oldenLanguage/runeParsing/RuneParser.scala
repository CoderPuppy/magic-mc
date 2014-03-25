package cpup.mc.magic.api.oldenLanguage.runeParsing

import cpup.mc.magic.MagicMod
import cpup.mc.magic.api.oldenLanguage.runes._
import scala.collection.mutable
import scala.collection.mutable.ListBuffer

// TODO: limit what runes a player can use
class RuneParser {
	def mod = MagicMod

	var action: TAction = null
	val targetPath = new ListBuffer[TNoun]
	def spell = Spell(action, targetPath.toList)

	override def toString = "RuneParser {\n  " + List(
		"action = " + action,
		"target = [ " + targetPath.mkString(", ") + " ]",
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

	def handle(rune: TRune) {
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
		parser.enter(new ActionMode)
	}

	override def onReturn(parser: RuneParser, child: RuneParserMode) {
		child match {
			case mode: ActionMode =>
				parser.action = mode.action
		}

		parser.mode = TargetMode
	}
}
case object TargetMode extends RuneParserMode {
	def handle(parser: RuneParser, rune: TRune) {
		rune match {
			case _: TNoun | _: TNounModifier =>
				parser.enter(new NounMode(true))
				parser.handle(rune)
			case _ =>
				parser.unhandledRune(rune)
		}
	}

	override def onReturn(parser: RuneParser, child: RuneParserMode) {
		super.onReturn(parser, child)

		child match {
			case mode: NounMode =>
				parser.targetPath += mode.noun
		}
	}
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

			case conjunction: TNounConjunction if noun != null =>
				parser.enter(new NounConjunctionMode(conjunction))

			case _ =>
				parser.leave
				parser.handle(rune)
		}
	}


	override def onReturn(parser: RuneParser, child: RuneParserMode) {
		child match {
			case mode: NounPrepositionMode =>
				mode.preposition.createNounModifier(mode.noun).modifyNoun(noun)

			case mode: NounConjunctionMode =>
				noun = mode.conjunction.combineNouns(noun, mode.noun)
		}
	}

	override def toString = "noun = " + noun + ", modifiers = " + modifiers
}
class NounConjunctionMode(val conjunction: TNounConjunction) extends NounMode(true) {
	override def toString = super.toString + ", conjunction = " + conjunction
}
class NounPrepositionMode(val preposition: TNounPreposition  ) extends NounMode(false) {
	override def toString = super.toString + ", preposition = " + preposition
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
				parser.enter(new ActionPrepositionalMode(preposition))
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
		}
	}
}
class ActionConjunctionMode(val conjunction: TActionConjunction) extends ActionMode {
	override def toString = super.toString + ", conjunction = " + conjunction
}
class ActionPrepositionalMode(val preposition: TActionPreposition) extends NounMode(false) {
	// maybe allow preposition
	override def toString = super.toString + ", preposition = " + preposition
}