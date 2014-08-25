package nf.fr.ephys.omnifluids.common.compatibility;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import nf.fr.ephys.omnifluids.api.IFluidAction;
import nf.fr.ephys.omnifluids.common.block.FluidBlock;
import nf.fr.ephys.omnifluids.common.damageSources.DamageSourceRegistry;

import java.util.Random;

public class FluidEffectCold implements IFluidAction {
	@Override
	public void onTouch(World world, int x, int y, int z, Entity entity) {
		if (entity instanceof EntityLivingBase) {
			if (!((EntityLivingBase) entity).isPotionActive(Potion.moveSlowdown)) {
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), 1200, 1));
			}

			entity.attackEntityFrom(DamageSourceRegistry.cold, 1);
		}
	}

	@Override
	public void onTick(World world, int x, int y, int z, Random rand) {}

	public static void addEffect(String[] fluidnames) {
		FluidEffectCold effect = new FluidEffectCold();

		for (String fluidname : fluidnames) {
			Fluid fluid = FluidRegistry.getFluid(fluidname);
			if (fluid == null || !(fluid.getBlock() instanceof FluidBlock))
				continue;

			((FluidBlock) fluid.getBlock()).setFluidAction(effect);
		}
	}
}
