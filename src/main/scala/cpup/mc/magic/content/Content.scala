package cpup.mc.magic.content

import cpup.mc.lib.content.CPupContent
import cpup.mc.magic.{MagicMod, TMagicMod}
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.ItemStack
import net.minecraft.init.{Blocks, Items}

object Content extends CPupContent[TMagicMod] {
	def mod = MagicMod

	override def preInit(e: FMLPreInitializationEvent) {
		super.preInit(e)

		registerItem(new ItemSpell().setName("spell"))

		registerItem(new ItemDeed().setName("deed").setCreativeTab(CreativeTabs.tabMisc).asInstanceOf[TItemBase])

		addRecipe(
			new ItemStack(items("deed")),
			Array(
				"TRT",
				"RPR",
				"TRT"
			),
			'P', Items.paper,
			'T', Blocks.redstone_torch,
			'R', Items.redstone
		)

		registerBlock(new BlockDeedOffice().setName("deedOffice").setCreativeTab(CreativeTabs.tabBlock).asInstanceOf[TBlockBase])

		addRecipe(
			new ItemStack(blocks("deedOffice"), 6),
			Array(
				" D ",
				"WWW",
				"CCC"
			),
			'D', items("deed"),
			'W', "woodPlank",
			'C', "cobblestone"
		)

		registerBlock(new BlockWritingDesk().setName("writingDesk").setCreativeTab(CreativeTabs.tabBlock).asInstanceOf[TBlockBase], classOf[ItemWritingDesk])
	}
}