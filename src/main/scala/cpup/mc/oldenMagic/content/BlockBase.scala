package cpup.mc.oldenMagic.content

import cpup.mc.lib.content.CPupBlock
import cpup.mc.oldenMagic.{TMagicMod, MagicMod}
import net.minecraft.block.Block
import net.minecraft.block.material.Material

trait TBlockBase extends CPupBlock[TMagicMod] {
	def mod = MagicMod
}
class BlockBase(material: Material) extends Block(material) with TBlockBase