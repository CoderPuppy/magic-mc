package cpup.mc.oldenMagic.api.oldenLanguage.runeParsing

import cpup.mc.oldenMagic.OldenMagicMod
import cpup.mc.oldenMagic.api.oldenLanguage.runes._
import scala.collection.mutable
import scala.collection.mutable.ListBuffer

// TODO: limit what runes a player can use
class RuneParser {
	def mod = OldenMagicMod

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
//		println(s"handling rune: $rune in mode: $mode, stack: ${modeStack.mkString(", ")}")
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
	var action: TVerbRune = null
	val targetPath = new ListBuffer[TNounRune]()
	def spell = Spell(action, targetPath.toList)

	def handle(parser: RuneParser, rune: TRune) {
		rune match {
			case conjunction: TSpellConjunction =>
				parser.enter(new SpellConjunctionMode(conjunction))
			case _ =>
		}
	}

	override def onEnter(parser: RuneParser) {
		parser.enter(new VerbMode)
	}

	override def onReturn(parser: RuneParser, child: RuneParserMode) {
		child match {
			case mode: VerbMode =>
				action = mode.verb
				parser.enter(new TargetMode(targetPath, true))
			case _ =>
		}
	}

	override def toString = s"SpellMode { action = ${action}, targetPath = [ ${targetPath.mkString(", ")} ] }"
}
case class SpellConjunctionMode(conjunction: TSpellConjunction) extends SpellMode {
	override def toString = s"${super.toString}, conjunction = $conjunction"
}

class TargetMode(var targetPath: ListBuffer[TNounRune], val canHavePrepositions: Boolean) extends RuneParserMode {
	def this(canHavePrepositions: Boolean) {
		this(new ListBuffer[TNounRune], canHavePrepositions)
	}

	def handle(parser: RuneParser, rune: TRune) {
		rune match {
			case _: TNounRune | _: TNounModifierRune =>
				parser.enter(new NounMode(true))
				parser.handle(rune)

			case conjunction: TNounConjunctionRune if !targetPath.isEmpty =>
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
	var noun: TNounRune = null
	val modifiers = new ListBuffer[TNounModifierRune]()

	def handle(parser: RuneParser, rune: TRune) {
		rune match {
			case mod: TNounModifierRune if noun == null =>
				modifiers += mod

			case _noun: TNounRune if noun == null =>
				noun = _noun
				modifiers.foreach(_.modifyNoun(noun))

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
class NounConjunctionMode(val conjunction: TNounConjunctionRune) extends TargetMode(true) {
	override def toString = s"${super.toString}, conjunction = $conjunction"
}
class NounPrepositionMode(val preposition: TNounPreposition  ) extends TargetMode(false) {
	override def toString = s"${super.toString}, preposition = $preposition"
}

class VerbMode extends RuneParserMode {
	var verb: TVerbRune = null
	val modifiers = new ListBuffer[TVerbModifierRune]

	def handle(parser: RuneParser, rune: TRune) {
		rune match {
			case mod: TVerbModifierRune if verb == null =>
				modifiers += mod
			case act: TVerbRune if verb == null =>
				verb = act

				for(mod <- modifiers) {
					mod.modifyVerb(verb)
				}
			case preposition: TVerbPrepositionRune if verb != null =>
				parser.enter(new VerbPrepositionMode(preposition))
			case conjunction: TVerbConjunctionRune if verb != null =>
				parser.enter(new VerbConjunctionMode(conjunction))
			case _ =>
				parser.leave
				parser.handle(rune)
		}
	}

	override def onReturn(parser: RuneParser, child: RuneParserMode) {
		child match {
			case mode: VerbConjunctionMode =>
				verb = mode.conjunction.combineVerbs(verb, mode.verb)

			case mode: VerbPrepositionMode =>
				mode.preposition.createVerbModifier(mode.targetPath.toList).modifyVerb(verb)
		}
	}
}
class VerbConjunctionMode(val conjunction: TVerbConjunctionRune) extends VerbMode {
	override def toString = s"${super.toString}, conjunction = $conjunction"
}
class VerbPrepositionMode(val preposition: TVerbPrepositionRune) extends TargetMode(false) {
	// maybe allow preposition
	override def toString = s"${super.toString}, preposition = $preposition"
}