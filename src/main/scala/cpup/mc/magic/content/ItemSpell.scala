package cpup.mc.magic.content

import net.minecraft.item.ItemStack
import net.minecraft.entity.player.EntityPlayer
import java.util
import cpup.mc.magic.api.impl.{InvalidTransformException, ContextFactory, Parser}
import cpup.lib.Util
import cpup.mc.magic.api.TRune
import net.minecraft.nbt.NBTTagCompound

class ItemSpell extends ItemBase {
	override def addInformation(stack: ItemStack, player: EntityPlayer, _lore: util.List[_], par4: Boolean) {
		val lore = _lore.asInstanceOf[util.List[String]]

		val spell = Util.checkNull(stack.getTagCompound, (compound: NBTTagCompound) => {
			Util.checkNull(compound.getString("spell"), (str: String) => str, "")
		}, "")

		lore.add(spell)

		try {
			for(rune <- Parser.parse(ContextFactory.create, spell)) {
				lore.add(rune.toString)
			}
		} catch {
			case e: InvalidTransformException => {
				lore.add(e.getMessage)
				println(e.getMessage)
			}
		}
	}
}