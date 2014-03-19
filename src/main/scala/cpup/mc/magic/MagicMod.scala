package cpup.mc.magic

import cpup.mc.lib.CPupMod
import cpw.mods.fml.common.Mod
import cpup.mc.magic.content.Content
import cpup.mc.lib.network.CPupMessage

trait TMagicMod extends CPupMod[TRef, CPupMessage] {
	def ref = Ref
	override def content = Content
}

@Mod(modid = Ref.modID, modLanguage = "scala")
object MagicMod extends TMagicMod