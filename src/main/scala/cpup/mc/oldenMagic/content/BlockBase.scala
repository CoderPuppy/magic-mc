package cpup.mc.oldenMagic.content

import cpup.mc.lib.content.CPupBlock
import cpup.mc.oldenMagic.{TOldenMagicMod, OldenMagicMod}
import net.minecraft.block.Block
import net.minecraft.block.material.Material

trait TBlockBase extends CPupBlock[TOldenMagicMod] {
	def mod = OldenMagicMod
}
class BlockBase(material: Material) extends Block(material) with TBlockBase