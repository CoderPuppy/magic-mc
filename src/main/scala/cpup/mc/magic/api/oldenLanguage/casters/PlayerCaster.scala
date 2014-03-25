package cpup.mc.magic.api.oldenLanguage.casters

import net.minecraft.entity.player.EntityPlayer
import cpup.mc.lib.util.EntityUtil

class PlayerCaster(val player: EntityPlayer) extends TCaster {
	def world = player.worldObj
	val mop = EntityUtil.getMOPBoth(player, if(player.capabilities.isCreativeMode) 6 else 3)
}