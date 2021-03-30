package shnupbups.tinkersaether.modules;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import shnupbups.tinkersaether.TinkersAether;
import shnupbups.tinkersaether.config.TAConfig;
import shnupbups.tinkersaether.tools.ToolDart;
import shnupbups.tinkersaether.tools.ToolDartShooter;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.IToolPart;
import slimeknights.tconstruct.library.tools.Pattern;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.tools.ToolPart;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.TinkerTools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mod.EventBusSubscriber(modid = TinkersAether.modid)
public class ModuleTools {

	public static ToolDartShooter dartShooter;
	public static ToolDart dart;

	public static ToolPart mouthpiece;
	public static ToolPart tube;

	public static ToolPart dartTip;

	public static final List<ToolCore> tools = new ArrayList<>();
	public static final List<IToolPart> parts = new ArrayList<>();

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		TinkersAether.logger.info("Tools Module - Begin ItemInit");

		if (TAConfig.darts) {
			mouthpiece = new ToolPart(Material.VALUE_Ingot);
			mouthpiece.setTranslationKey("mouthpiece").setRegistryName("mouthpiece");
			event.getRegistry().register(mouthpiece);
			TinkerRegistry.registerToolPart(mouthpiece);
			TinkersAether.proxy.registerToolPartModel(mouthpiece);
			parts.add(mouthpiece);

			tube = new ToolPart(Material.VALUE_Ingot * 3);
			tube.setTranslationKey("tube").setRegistryName("tube");
			event.getRegistry().register(tube);
			TinkerRegistry.registerToolPart(tube);
			TinkersAether.proxy.registerToolPartModel(tube);
			parts.add(tube);

			dartTip = new ToolPart(Material.VALUE_Ingot);
			dartTip.setTranslationKey("dart_tip").setRegistryName("dart_tip");
			event.getRegistry().register(dartTip);
			TinkerRegistry.registerToolPart(dartTip);
			TinkersAether.proxy.registerToolPartModel(dartTip);
			parts.add(dartTip);
		}

		TinkersAether.logger.info("Tools Module - Parts Registered");

		if (TAConfig.darts) {
			dartShooter = new ToolDartShooter();
			event.getRegistry().register(dartShooter);
			TinkerRegistry.registerToolForgeCrafting(dartShooter);
			TinkersAether.proxy.registerToolModel(dartShooter);
			tools.add(dartShooter);

			dart = new ToolDart();
			event.getRegistry().register(dart);
			TinkerRegistry.registerToolForgeCrafting(dart);
			TinkersAether.proxy.registerToolModel(dart);
			tools.add(dart);
		}

		TinkersAether.logger.info("Tools Module - Tools Registered");

		for (final IToolPart part: Collections.unmodifiableList(parts)) {
			for (final ToolCore tool: Collections.unmodifiableList(tools)) {
				for (final PartMaterialType pmt: tool.getRequiredComponents()) {
					if (pmt.getPossibleParts().contains(part)) {
						TinkerRegistry.registerStencilTableCrafting(Pattern.setTagForPart(new ItemStack(TinkerTools.pattern), (Item)part));
					}
				}
			}
		}

		TinkersAether.logger.info("Tools Module - Stencil Crafting Registered");

		// for darts and dart shooters
		for (IModifier modifier: new IModifier[] {
				TinkerModifiers.modBaneOfArthopods,
				TinkerModifiers.modBeheading,
				TinkerModifiers.modDiamond,
				TinkerModifiers.modEmerald,
				TinkerModifiers.modFiery,
				TinkerModifiers.modFins,
				TinkerModifiers.modGlowing,
				TinkerModifiers.modHaste,
				TinkerModifiers.modKnockback,
				TinkerModifiers.modLuck,
				TinkerModifiers.modMendingMoss,
				TinkerModifiers.modNecrotic,
				TinkerModifiers.modReinforced,
				TinkerModifiers.modSharpness,
				TinkerModifiers.modShulking,
				TinkerModifiers.modSilktouch,
				TinkerModifiers.modSmite,
				TinkerModifiers.modSoulbound,
				TinkerModifiers.modWebbed,
		}) {
			TinkersAether.proxy.registerModifierModel(modifier,
					new ResourceLocation(TinkersAether.modid, "models/item/modifiers/"+modifier.getIdentifier()));
		}

		TinkersAether.logger.info("Tools Module - Modifier Models Registered");

		TinkersAether.logger.info("Tools Module - End ItemInit");
	}

}