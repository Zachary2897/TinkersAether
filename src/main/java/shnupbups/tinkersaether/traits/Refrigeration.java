package shnupbups.tinkersaether.traits;

import com.gildedgames.the_aether.api.AetherAPI;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import slimeknights.tconstruct.common.config.Config;
import slimeknights.tconstruct.library.modifiers.IToolMod;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.TinkerTraits;

import java.util.ListIterator;

public class Refrigeration extends TATrait {
	public static final Refrigeration refrigeration = new Refrigeration();

	public Refrigeration() {
		super("refrigeration", 0x99999F);
	}

	@Override
	public boolean canApplyTogether(IToolMod modifier) {
		return !modifier.getIdentifier().equals(TinkerTraits.squeaky.getIdentifier())
				&& !modifier.getIdentifier().equals(Cushy.cushy.getIdentifier())
				&& !modifier.getIdentifier().equals(TinkerModifiers.modSilktouch.getIdentifier());
	}

	@Override
	public boolean canApplyTogether(Enchantment enchantment) {
		return enchantment != Enchantments.SILK_TOUCH;
	}

	@Override
	public void blockHarvestDrops(ItemStack tool, BlockEvent.HarvestDropsEvent event) {
		if (ToolHelper.isToolEffective2(tool, event.getState())) {
			ListIterator<ItemStack> dropsList = event.getDrops().listIterator();
			while (dropsList.hasNext()) {
				ItemStack drop = dropsList.next();
				ItemStack frozen = AetherAPI.getInstance().getFreezable(drop).getOutput();
				if (!frozen.isEmpty()) {
					frozen = frozen.copy();
					frozen.setCount(drop.getCount());
					int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, tool);
					if (Config.autosmeltlapis && fortune > 0) {
						frozen.setCount(frozen.getCount() * random.nextInt(fortune + 1) + 1);
					}
					dropsList.set(frozen);
				}
			}
		}
	}
}