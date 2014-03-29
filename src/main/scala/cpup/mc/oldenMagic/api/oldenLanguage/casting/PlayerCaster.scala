package cpup.mc.oldenMagic.api.oldenLanguage.casting

import net.minecraft.entity.player.EntityPlayer
import cpup.mc.lib.util.EntityUtil
import cpup.mc.lib.util.pos.BlockPos
import cpw.mods.fml.common.FMLCommonHandler
import cpup.mc.oldenMagic.api.oldenLanguage.runeParsing.TTypeNoun
import net.minecraft.entity.Entity
import net.minecraft.block.Block

class PlayerCaster(val player: EntityPlayer) extends EntityTarget(player) with TCaster {
	val mop = EntityUtil.getMOPBoth(player, if(player.capabilities.isCreativeMode) 6 else 3)

	// TODO: Implement
	override def ownedTargets(typeNoun: TTypeNoun[_ <: Entity, _ <: Block]) = List()
}