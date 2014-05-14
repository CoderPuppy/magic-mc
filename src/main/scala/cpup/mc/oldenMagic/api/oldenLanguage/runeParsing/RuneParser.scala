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

	def handle(parser: RuneParser, rune: TRune) { parser.unhandledRune(rune) }

	override def onEnter(parser: RuneParser) {
		parser.enter(new VerbMode)
	}

	override def onReturn(parser: RuneParser, child: RuneParserMode) {
		child match {
			case mode: VerbMode =>
				action = mode.currentVerb
				parser.enter(new NounMode(targetPath, true))
			case _ =>
		}
	}

	override def toString = s"SpellMode { action = $action, targetPath = [ ${targetPath.mkString(", ")} ] }"
}
case class SpellConjunctionMode(conjunction: TSpellConjunction) extends SpellMode {
	override def toString = s"${super.toString}, conjunction = $conjunction"
}

class NounMode(var targetPath: ListBuffer[TNounRune], val canHavePrepositions: Boolean) extends RuneParserMode {
	def this(canHavePrepositions: Boolean) {
		this(new ListBuffer[TNounRune], canHavePrepositions)
	}

	var currentNoun: TNounRune = null

	def handle(parser: RuneParser, rune: TRune) {
		rune match {
			case noun: TNounRune if currentNoun == null =>
				targetPath += noun
				currentNoun = noun

			case conjunction: TNounCombinatorRune if !targetPath.isEmpty =>
				parser.enter(new NounCombinatorMode(conjunction))

			case mod: TNounModifierRune if currentNoun != null =>
				mod.modifyNoun(currentNoun)

			case preposition: TNounPrepositionRune if currentNoun != null && canHavePrepositions =>
				parser.enter(new NounPrepositionMode(preposition))

			case _ =>
				parser.leave
				parser.handle(rune)
		}
	}

	override def onReturn(parser: RuneParser, child: RuneParserMode) {
		super.onReturn(parser, child)

		child match {
			case mode: NounCombinatorMode =>
				targetPath.clear
				for(noun <- mode.conjunction.combineNouns(targetPath.toList, mode.targetPath.toList)) {
					targetPath += noun
				}

			case mode: NounPrepositionMode =>
				mode.preposition.createNounModifier(mode.targetPath.toList).modifyNoun(currentNoun)
		}
	}

	override def toString = "TargetMode { targetPath = [ " + targetPath.mkString(", ") + " ] }"
}
class NounCombinatorMode(val conjunction: TNounCombinatorRune) extends NounMode(true) {
	override def toString = s"${super.toString}, conjunction = $conjunction"
}
class NounPrepositionMode(val preposition: TNounPrepositionRune  ) extends NounMode(false) {
	override def toString = s"${super.toString}, preposition = $preposition"
}

class VerbMode extends RuneParserMode {
	var currentVerb: TVerbRune = null

	def handle(parser: RuneParser, rune: TRune) {
		rune match {
			case verb: TVerbRune if currentVerb == null =>
				currentVerb = verb

			case mod: TVerbModifierRune if currentVerb != null =>
				mod.modifyVerb(currentVerb)
				
			case preposition: TVerbPrepositionRune if currentVerb != null =>
				parser.enter(new VerbPrepositionMode(preposition))

			case combinator: TVerbCombinatorRune if currentVerb != null =>
				parser.enter(new VerbCombinatorMode(combinator))
				
			case _ =>
				parser.leave
				parser.handle(rune)
		}
	}

	override def onReturn(parser: RuneParser, child: RuneParserMode) {
		child match {
			case mode: VerbCombinatorMode =>
				currentVerb = mode.conjunction.combineVerbs(currentVerb, mode.currentVerb)

			case mode: VerbPrepositionMode =>
				mode.preposition.createVerbModifier(mode.targetPath.toList).modifyVerb(currentVerb)
		}
	}
}
class VerbCombinatorMode(val conjunction: TVerbCombinatorRune) extends VerbMode {
	override def toString = s"${super.toString}, conjunction = $conjunction"
}
class VerbPrepositionMode(val preposition: TVerbPrepositionRune) extends NounMode(false) {
	// maybe allow preposition
	override def toString = s"${super.toString}, preposition = $preposition"
}