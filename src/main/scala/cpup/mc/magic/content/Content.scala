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

		registerItem(new ItemBase().setName("leatherWrappedStick").setMaxStackSize(1).asInstanceOf[TItemBase])
		addRecipe(
			new ItemStack(items("leatherWrappedStick")),
			Array(
				"   ",
				"LS ",
				"LL "
			)
		)

		registerItem(new ItemBase().setName("goldBandedStick").setMaxStackSize(1).asInstanceOf[TItemBase])
		addRecipe(
			new ItemStack(items("goldBandedStick")),
			Array(
				"NNN",
				"NSN",
				"NNN"
			),
			'N', Items.gold_nugget,
			'S', items("leatherWrappedStick")
		)

		registerItem(new ItemWand().setName("wand").setMaxStackSize(1).asInstanceOf[TItemBase])
		addRecipe(
			new ItemStack(items("wand")),
			Array(
				" N",
				"S "
			),
			'N', Items.gold_nugget,
			'S', items("goldBandedStick")
		)

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

		registerItem(new ItemBase().setName("knife").setMaxDamage(500).setMaxStackSize(1).asInstanceOf[TItemBase])
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
			Blocks.flower_pot
		)

		OldenLanguageRegistry.registerRune(TextRune)

		// Actions
		OldenLanguageRegistry.registerRune(GrowRune)
		OldenLanguageRegistry.registerRune(BurnRune)

		// Nouns
		OldenLanguageRegistry.registerRune(PlayerRune)
		OldenLanguageRegistry.registerRune(EntityTypeRune)
		OldenLanguageRegistry.registerRune(BlockTypeRune)

		// Pronouns
		OldenLanguageRegistry.registerRune(ThisRune)

		OldenLanguageRegistry.registerRootContextTransformer((root: Context) => {
			val actions = new Context
			actions.transforms("burn") = BurnRune
			actions.transforms("grow") = GrowRune

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