package nf.fr.ephys.omnifluids.common.compatibility;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import nf.fr.ephys.omnifluids.api.IFluidAction;

import java.util.Random;

public class FluidEffectMilk implements IFluidAction {
	@Override
	public void onTouch(World world, int x, int y, int z, Entity entity) {
		if (entity instanceof EntityLivingBase) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.digSpeed.id, 6 * 20, 0));
		}
	}

	@Override
	public void onTick(World world, int x, int y, int z, Random rand) {}
}