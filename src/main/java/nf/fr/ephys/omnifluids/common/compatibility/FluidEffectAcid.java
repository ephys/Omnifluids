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

public class FluidEffectAcid implements IFluidAction {
	@Override
	public void onTouch(World world, int x, int y, int z, Entity entity) {
		if (entity instanceof EntityLivingBase) {
			if (!((EntityLivingBase) entity).isPotionActive(Potion.blindness)) {
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.blindness.getId(), 1600, 0));
			}

			entity.attackEntityFrom(DamageSourceRegistry.acid, 3);
		}
	}

	@Override
	public void onTick(World world, int x, int y, int z, Random rand) {}

	public static void addEffect(String[] fluidnames) {
		FluidEffectAcid effect = new FluidEffectAcid();

		for (String fluidname : fluidnames) {
			Fluid fluid = FluidRegistry.getFluid(fluidname);
			if (fluid == null || !(fluid.getBlock() instanceof FluidBlock))
				continue;

			((FluidBlock) fluid.getBlock()).setFluidAction(effect);
		}
	}
}
