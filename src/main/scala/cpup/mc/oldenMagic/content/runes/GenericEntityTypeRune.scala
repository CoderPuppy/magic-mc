package cpup.mc.oldenMagic.content.runes

import cpup.mc.oldenMagic.api.oldenLanguage.runes.SingletonRune
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.NonBlockTypeNoun
import cpup.mc.oldenMagic.api.oldenLanguage.casting.CastingContext
import net.minecraft.client.renderer.texture.IIconRegister
import cpw.mods.fml.relauncher.{SideOnly, Side}
import cpup.mc.oldenMagic.OldenMagicMod
import net.minecraft.util.IIcon
import net.minecraft.entity.Entity

object GenericEntityTypeRune extends SingletonRune with NonBlockTypeNoun {
	def mod = OldenMagicMod

	override def name = s"${mod.ref.modID}:generic-entity"

	override def entityClass = classOf[Entity]
	override def filterEntity(entity: Entity) = true

	@SideOnly(Side.CLIENT)
	var icon: IIcon = null

	@SideOnly(Side.CLIENT)
	override def icons = List()

	@SideOnly(Side.CLIENT)
	override def registerIcons(register: IIconRegister) {
		icon = register.registerIcon(s"${mod.ref.modID}:runes/generic-entity")
	}
 }