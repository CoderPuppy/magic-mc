package cpup.mc.magic.api.oldenLanguage.runeParsing

import cpup.mc.magic.api.oldenLanguage.runes.{TNoun, TAction}

case class Spell(action: TAction, targetPath: List[TNoun])