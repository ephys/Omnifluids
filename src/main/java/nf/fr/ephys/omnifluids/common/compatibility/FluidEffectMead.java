package nf.fr.ephys.omnifluids.common.compatibility;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import nf.fr.ephys.omnifluids.api.IFluidAction;

import java.util.Random;

/**
 * Fluid effect mead - forestry compat
 */
public class FluidEffectMead implements IFluidAction {
	@Override
	public void onTouch(World world, int x, int y, int z, Entity entity) {
		if (entity instanceof EntityLivingBase) {
			if (!((EntityLivingBase) entity).isPotionActive(Potion.moveSlowdown)) {
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), 1200, 2));
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.digSpeed.getId(), 600, 1));
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), 600, 1));
			}
		}
	}

	@Override
	public void onTick(World world, int x, int y, int z, Random rand) {

	}
}
