package shnupbups.tinkersaether.traits;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.modifiers.IToolMod;
import slimeknights.tconstruct.library.utils.ToolBuilder;
import slimeknights.tconstruct.tools.TinkerModifiers;

public class Cushy extends TATrait {
	public static final Cushy cushy = new Cushy();

	public Cushy() {
		super("cushy", 0xBBBBBF);
	}

	@Override
	public boolean canApplyTogether(IToolMod modifier) {
		return !modifier.getIdentifier().equals(TinkerModifiers.modSilktouch.getIdentifier())
				&& !modifier.getIdentifier().equals(TinkerModifiers.modLuck.getIdentifier());
	}

	@Override
	public boolean canApplyTogether(Enchantment enchantment) {
		return enchantment != Enchantments.LOOTING && enchantment != Enchantments.FORTUNE;
	}

	@Override
	public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
		super.applyEffect(rootCompound, modifierTag);
		ToolBuilder.addEnchantment(rootCompound, Enchantments.SILK_TOUCH);
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
		return newDamage * 0.1F;
	}
}