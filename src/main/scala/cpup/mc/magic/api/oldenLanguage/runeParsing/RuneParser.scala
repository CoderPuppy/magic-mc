package cpup.mc.magic.api.oldenLanguage.runeParsing

import cpup.mc.magic.MagicMod
import cpup.mc.magic.api.oldenLanguage.runes._
import scala.collection.mutable
import scala.collection.mutable.ListBuffer

// TODO: limit what runes a player can use
class RuneParser {
	def mod = MagicMod

	def mode = modeStack.last
	def mode_=(newMode: RuneParserMode) = {
		leave
		enter(newMode)
		this
	}

	val modeStack = new mutable.Stack[RuneParserMode]

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

	var action: TActionRune = null
	var target: TNounRune = null

	def handle(rune: TRune) {
		if(mode == null) {
			unhandledRune(rune)
		} else {
			mode.handle(this, rune)
		}
	}

	def unhandledRune(rune: TRune) {
		// TODO: instability
		mod.logger.warn("Unhandled rune: " + rune.toString + " in mode: " + mode + ", stack: " + modeStack)
	}

	override def toString = "RuneParser {\n" + List(
		"action = " + action,
		"target = " + target,
		"modeStack = " + modeStack
	).mkString(",\n  ") + "\n}"

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
	val modifiers = new ListBuffer[TActionModifierRune]()

	def handle(parser: RuneParser, rune: TRune) {
		rune match {
			case mod: TActionModifierRune =>
				modifiers += mod
			case action: TActionRune =>
				modifiers.foreach(_.modify(action))
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
			case preposition: TActionPrepositionRune =>
				parser.enter(new ActionPrepositionalMode(preposition))
			case _ =>
				parser.mode = new TargetMode
				parser.handle(rune)
		}
	}

	override def onReturn(parser: RuneParser, child: RuneParserMode) {
		super.onReturn(parser, child)

		child match {
			case mode: ActionPrepositionalMode =>
				mode.preposition.createActionModifier(mode.noun).modify(parser.action)
		}
	}
}

class TargetMode extends RuneParserMode {
	val nouns = new ListBuffer[TNounRune]()

	def handle(parser: RuneParser, rune: TRune) {
		rune match {
			case _: TNounRune | _: TNounModifierRune =>
				parser.enter(new NounMode)
				parser.handle(rune)
			case preposition: TNounPrepositionRune if !nouns.isEmpty =>
				parser.enter(new NounPrepositionMode(preposition))
			case _ =>
				parser.unhandledRune(rune)
		}
	}

	override def onReturn(parser: RuneParser, child: RuneParserMode) {
		super.onReturn(parser, child)

		child match {
			case mode: NounPrepositionMode if !nouns.isEmpty =>
				mode.preposition.createNounModifier(mode.noun).modify(nouns.last)
			case mode: NounMode =>
				nouns += mode.noun
		}
	}

	override def toString = nouns.toString
}

class ActionPrepositionalMode(val preposition: TActionPrepositionRune) extends NounMode {
	override def toString = super.toString + ", preposition = " + preposition
}
class NounPrepositionMode    (val preposition: TNounPrepositionRune  ) extends NounMode {
	override def toString = super.toString + ", preposition = " + preposition
}

class NounMode extends RuneParserMode {
	val modifiers = new ListBuffer[TNounModifierRune]()
	var noun: TNounRune = null

	def handle(parser: RuneParser, rune: TRune) {
		rune match {
			case mod: TNounModifierRune =>
				modifiers += mod
			case _noun: TNounRune =>
				noun = _noun
				parser.leave
			case _ =>
				parser.unhandledRune(rune)
		}
	}

	override def toString = "noun = " + noun + ", modifiers = " + modifiers
}