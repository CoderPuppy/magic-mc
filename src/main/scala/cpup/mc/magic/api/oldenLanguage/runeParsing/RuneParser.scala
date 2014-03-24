package cpup.mc.magic.api.oldenLanguage.runeParsing

import cpup.mc.magic.MagicMod
import cpup.mc.magic.api.oldenLanguage.runes._
import scala.collection.mutable

// TODO: limit what runes a player can use
class RuneParser {
	def mod = MagicMod

	def mode = modeStack.last
	def mode_=(newMode: RuneParserMode) = {
		modeStack.pop
		modeStack.push(newMode)
		this
	}

	val modeStack = new mutable.Stack[RuneParserMode]
	modeStack.push(ActionModifierMode)

	var action: TActionRune = null
	var actionModifiers = List[TActionModifierRune]()

	var noun: TNounRune = null
	var nounModifiers = List[TNounModifierRune]()

	def handle(_rune: TRune) {
		var rune = _rune

		if(rune.isInstanceOf[TActionRune]) {
			var actionRune = rune.asInstanceOf[TActionRune]
			for(mod <- actionModifiers) {
				actionRune = mod.modify(actionRune)
			}
			actionModifiers = List[TActionModifierRune]()
			rune = actionRune
		}

		if(rune.isInstanceOf[TNounRune]) {
			var nounRune = rune.asInstanceOf[TNounRune]
			for(mod <- nounModifiers) {
				nounRune = mod.modify(nounRune)
			}
			nounModifiers = List[TNounModifierRune]()
			rune = nounRune
		}

		mode match {
			case ActionMode =>
				rune match {
					case newAct: TActionRune =>
						action = newAct
						mode = PostActionMode
					case _ =>
						unhandledRune(rune)
				}
			case ActionModifierMode =>
				rune match {
					case act: TActionRune =>
						mode = ActionMode
						handle(act)
					case mod: TActionModifierRune =>
						actionModifiers ++= List(mod)
					case _ =>
						unhandledRune(rune)
				}
			case PostActionMode =>
				rune match {
					case newNoun: TNounRune =>
						noun = newNoun
						mode = PostNounMode

					case preposition: TActionPrepositionRune =>
						mode = ActionPrepositionalMode(preposition)

					case _ =>
						unhandledRune(rune)
				}
			case NounModifierMode =>
				rune match {
					case mod: TNounModifierRune =>
						nounModifiers ++= List(mod)

					case _ =>
						unhandledRune(rune)
				}
			case PostNounMode =>
				rune match {
					case noun: TNounRune =>
						mode = PostActionMode
						handle(rune)
					case mod: TNounModifierRune =>
						mode = NounModifierMode
						handle(rune)

					case preposition: TNounPrepositionRune =>
						mode = NounPrepositionalMode(preposition)

					case _ =>
						unhandledRune(rune)
				}
			case ActionPrepositionalMode(preposition) =>
				rune match {
					case mod: TNounModifierRune =>
						nounModifiers ++= List(mod)

					case obj: TNounRune =>
						action = preposition.createPhrase(obj).modify(action)
					case _ =>
						unhandledRune(rune)
				}
			case NounPrepositionalMode(preposition) =>
				rune match {
					case mod: TNounModifierRune =>
						nounModifiers ++= List(mod)

					case obj: TNounRune =>
						noun = preposition.createPhase(obj).modify(noun)
					case _ =>
						unhandledRune(rune)
				}
			case _ =>
				throw new RuntimeException("Unknown mode: " + mode.toString)
		}
	}

	override def toString = "{ " + List("action = " + action).mkString(", ") + " }"

	def unhandledRune(rune: TRune) {
		mod.logger.warn("Unhandled rune: " + rune.toString)
		// TODO: instability
	}

	def handle(runes: List[TRune]) { runes.foreach(handle(_)) }
}

trait RuneParserMode {}
case object ActionMode         extends RuneParserMode
case object ActionModifierMode extends RuneParserMode
case object PostActionMode           extends RuneParserMode
case object NounModifierMode   extends RuneParserMode
case object PostNounMode       extends RuneParserMode
case class ActionPrepositionalMode(preposition: TActionPrepositionRune) extends RuneParserMode
case class NounPrepositionalMode(preposition: TNounPrepositionRune)   extends RuneParserMode