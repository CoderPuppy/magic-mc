package cpup.mc.magic.api.oldenLanguage.runeParsing

import cpup.mc.magic.MagicMod
import cpup.mc.magic.api.oldenLanguage.runes._
import scala.collection.mutable
import scala.collection.mutable.ListBuffer

// TODO: limit what runes a player can use
class RuneParser {
	def mod = MagicMod

	var action: TActionRune = null
	val targetPath = new ListBuffer[TNoun]

	override def toString = "RuneParser {\n  " + List(
		"action = " + action,
		"target = [ " + targetPath.mkString(", ") + " ]",
		"modeStack = " + modeStack
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

	modeStack.push(new ActionMode)

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

	def handle(runes: List[TRune]) { runes.foreach(handle(_)) }
}

trait RuneParserMode {
	def onEnter(parser: RuneParser) {}
	def handle(parser: RuneParser, rune: TRune)
	def onChild(parser: RuneParser, child: RuneParserMode) {}
	def onReturn(parser: RuneParser, child: RuneParserMode) {}
	def onLeave(parser: RuneParser) {}
}

class ActionMode extends RuneParserMode {
	val modifiers = new ListBuffer[TActionModifier]()

	def handle(parser: RuneParser, rune: TRune) {
		rune match {
			case mod: TActionModifier =>
				modifiers += mod
			case action: TActionRune =>
				modifiers.foreach(_.modifyAction(action))
				parser.action = action
				parser.mode = PostActionMode
			case _ =>
				parser.unhandledRune(rune)
		}
	}

	override def toString = modifiers.toString
}

case object PostActionMode extends RuneParserMode {
	def handle(parser: RuneParser, rune: TRune) {
		rune match {
			case preposition: TActionPreposition =>
				parser.enter(new ActionPrepositionalMode(preposition))
			case _ =>
				parser.mode = TargetMode
				parser.handle(rune)
		}
	}

	override def onReturn(parser: RuneParser, child: RuneParserMode) {
		super.onReturn(parser, child)

		child match {
			case mode: ActionPrepositionalMode =>
				mode.preposition.createActionModifier(mode.noun).modifyAction(parser.action)
		}
	}
}

object TargetMode extends RuneParserMode {
	def handle(parser: RuneParser, rune: TRune) {
		rune match {
			case _: TNoun | _: TNounModifier =>
				parser.enter(new NounMode)
				parser.handle(rune)
			case preposition: TNounPreposition if !parser.targetPath.isEmpty =>
				parser.enter(new NounPrepositionMode(preposition))
			case _ =>
				parser.unhandledRune(rune)
		}
	}

	override def onReturn(parser: RuneParser, child: RuneParserMode) {
		super.onReturn(parser, child)

		child match {
			case mode: NounPrepositionMode =>
				if(!parser.targetPath.isEmpty) {
					mode.preposition.createNounModifier(mode.noun).modifyNoun(parser.targetPath.last)
				}
			case mode: NounMode =>
				parser.targetPath += mode.noun
		}
	}
}

class ActionPrepositionalMode(val preposition: TActionPreposition) extends NounMode {
	override def toString = super.toString + ", preposition = " + preposition
}
class NounPrepositionMode    (val preposition: TNounPreposition  ) extends NounMode {
	override def toString = super.toString + ", preposition = " + preposition
}

class NounMode extends RuneParserMode {
	val modifiers = new ListBuffer[TNounModifier]()
	var noun: TNoun = null

	def handle(parser: RuneParser, rune: TRune) {
		rune match {
			case mod: TNounModifier =>
				modifiers += mod
			case _noun: TNoun =>
				noun = _noun
				parser.leave
			case _ =>
				parser.unhandledRune(rune)
		}
	}

	override def toString = "noun = " + noun + ", modifiers = " + modifiers
}