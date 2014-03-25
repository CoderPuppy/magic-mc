package cpup.mc.magic.content

import cpup.mc.lib.content.CPupContent
import cpup.mc.magic.{MagicMod, TMagicMod}
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.ItemStack
import net.minecraft.init.{Blocks, Items}
import cpw.mods.fml.common.registry.GameRegistry
import cpup.mc.magic.content.runes._
import cpup.mc.magic.api.oldenLanguage._
import cpup.mc.magic.api.oldenLanguage.textParsing.{SubContextTransform, TextRune, Context}

object Content extends CPupContent[TMagicMod] {
	def mod = MagicMod

	override def preInit(e: FMLPreInitializationEvent) {
		super.preInit(e)

		registerItem(new ItemWand().setName("wand"))

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

		registerBlock(new BlockWritingDesk().setName("writingDesk").setCreativeTab(CreativeTabs.tabBlock).asInstanceOf[TBlockBase])
		GameRegistry.registerTileEntity(classOf[TEWritingDesk], "writingDesk")

		registerItem(new ItemBase().setName("quill").setMaxDamage(500).setMaxStackSize(1).asInstanceOf[TItemBase])
		registerItem(new ItemBase().setName("inkWell").setMaxDamage(200).setMaxStackSize(1).asInstanceOf[TItemBase])

		addShapelessRecipe(
			new ItemStack(items("inkWell")),
			Items.dye,
			Blocks.flower_pot
		)

		OldenLanguageRegistry.registerRune(TextRune)
		OldenLanguageRegistry.registerRune(PlayerRune)
		OldenLanguageRegistry.registerRune(EntityTypeRune)
		OldenLanguageRegistry.registerRune(BlockTypeRune)
		OldenLanguageRegistry.registerRune(BurnRune)
		OldenLanguageRegistry.registerRune(ThisRune)
		OldenLanguageRegistry.registerRootContextTransformer((root: Context) => {
			val actions = new Context
			actions.transforms("burn") = BurnRune

			root.subContexts("actions") = actions
			root.transforms("a") = new SubContextTransform("actions")

			val pronouns = new Context
			pronouns.transforms("this") = ThisRune

			root.subContexts("pronouns") = pronouns
			root.transforms("pn") = new SubContextTransform("pronouns")

			val prepositions = new Context
			prepositions.transforms("of") = OfRune

			root.subContexts("prepositions") = prepositions
			root.transforms("pp") = new SubContextTransform("prepositions")

			val specificNouns = new Context
			specificNouns.transforms("pl") = PlayerTransform

			root.subContexts("specificNouns") = specificNouns
			root.transforms("sn") = new SubContextTransform("specificNouns")

			val typeNouns = new Context
			typeNouns.transforms("entity") = EntityTypeTransform
			typeNouns.transforms("block") = BlockTypeTransform

			root.subContexts("typeNouns") = typeNouns
			root.transforms("tn") = new SubContextTransform("typeNouns")
		})
	}
}