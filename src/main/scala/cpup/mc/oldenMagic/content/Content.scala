package cpup.mc.oldenMagic.content

import cpup.mc.lib.content.CPupContent
import cpup.mc.oldenMagic.{OldenMagicMod, TOldenMagicMod}
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.ItemStack
import net.minecraft.init.{Blocks, Items}
import cpw.mods.fml.common.registry.GameRegistry
import cpup.mc.oldenMagic.content.runes._
import cpup.mc.oldenMagic.api.oldenLanguage._
import cpup.mc.oldenMagic.api.oldenLanguage.textParsing.{SubContextTransform, TextRune, Context}

object Content extends CPupContent[TOldenMagicMod] {
	def mod = OldenMagicMod

	override def preInit(e: FMLPreInitializationEvent) {
		super.preInit(e)

		registerItem(new ItemBase().setName("leatherHandle").setMaxStackSize(1).asInstanceOf[TItemBase])
		addRecipe(
			new ItemStack(items("leatherHandle")),
			Array(
				"L L",
				"LLL"
			),
			'L', Items.leather
		)

		registerItem(new ItemWand().setName("wand").setMaxStackSize(1).asInstanceOf[TItemBase])

		registerItem(new ItemSpell().setName("spell").setMaxStackSize(1).asInstanceOf[TItemBase])
		addShapelessRecipe(
			new ItemStack(items("spell")),
			Items.paper
		)

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
		addRecipe(
			new ItemStack(blocks("writingDesk")),
			Array(
				"SSS",
				"P B"
			),
			'S', "slabWood",
			'P', "plankWood",
			'B', Blocks.bookshelf
		)

		registerItem(new ItemKnife().setName("knife").setMaxDamage(500).setMaxStackSize(1).asInstanceOf[TItemBase])
		addRecipe(
			new ItemStack(items("knife")),
			Array(
				" F",
				"S "
			),
			'F', Items.flint,
			'S', Items.stick
		)

		registerItem(new ItemBase().setName("quill").setMaxDamage(500).setMaxStackSize(1).asInstanceOf[TItemBase])
		addShapelessRecipe(
			new ItemStack(items("quill")),
			items("knife"),
			Items.feather
		)

		registerItem(new ItemBase().setName("inkWell").setMaxDamage(200).setMaxStackSize(1).asInstanceOf[TItemBase])
		addShapelessRecipe(
			new ItemStack(items("inkWell")),
			Items.dye,
			Items.potionitem,
			Items.flower_pot
		)

		registerItem(new ItemBend().setName("bend").setMaxStackSize(1).asInstanceOf[TItemBase])

		OldenLanguageRegistry.registerRune(TextRune)

		// Actions
		OldenLanguageRegistry.registerRune(GrowRune)
		OldenLanguageRegistry.registerRune(BurnRune)
		OldenLanguageRegistry.registerRune(ProtectRune)
		OldenLanguageRegistry.registerRune(DamageRune)
		OldenLanguageRegistry.registerRune(SeenRune)
		OldenLanguageRegistry.registerRune(HideRune)

		// Nouns
		OldenLanguageRegistry.registerRune(PlayerRune)
		OldenLanguageRegistry.registerRune(EntityTypeRune)
		OldenLanguageRegistry.registerRune(BlockTypeRune)
		OldenLanguageRegistry.registerRune(ThisNounRune)

		// Pronouns
		OldenLanguageRegistry.registerRune(ThisRune)
		OldenLanguageRegistry.registerRune(MeRune)
		OldenLanguageRegistry.registerRune(ItRune)

		// Noun Modifiers & Prepositions
		OldenLanguageRegistry.registerRune(OfRune)
		OldenLanguageRegistry.registerRune(OfModifierRune)

		OldenLanguageRegistry.registerRootContextTransformer((root: Context) => {
			val actions = new Context
			actions.transforms("burn") = BurnRune
			actions.transforms("grow") = GrowRune
			actions.transforms("protect") = ProtectTransform

			root.subContexts("actions") = actions
			root.transforms("a") = new SubContextTransform("actions")

			val adjectives = new Context
			adjectives.transforms("this") = ThisRune

			root.subContexts("adjectives") = adjectives
			root.transforms("adj") = new SubContextTransform("adjectives")

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