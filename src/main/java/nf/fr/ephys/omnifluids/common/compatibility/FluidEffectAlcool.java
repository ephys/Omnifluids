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

import java.util.Random;

public class FluidEffectAlcool implements IFluidAction {
	@Override
	public void onTouch(World world, int x, int y, int z, Entity entity) {
		if (entity instanceof EntityLivingBase) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), 1200, 2));
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.digSpeed.getId(), 600, 1));
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), 600, 1));
		}
	}

	@Override
	public void onTick(World world, int x, int y, int z, Random rand) {}

	public static void addEffect(String[] fluidnames) {
		FluidEffectAlcool effect = new FluidEffectAlcool();

		for (String fluidname : fluidnames) {
			Fluid fluid = FluidRegistry.getFluid(fluidname);
			if (fluid == null || !(fluid.getBlock() instanceof FluidBlock)) {
				System.out.println("INVALID FLUID " + fluidname);
				continue;
			}

			((FluidBlock) fluid.getBlock()).setFluidAction(effect);
		}
	}
}
