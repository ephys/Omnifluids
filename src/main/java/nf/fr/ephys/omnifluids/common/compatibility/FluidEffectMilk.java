package nf.fr.ephys.omnifluids.common.compatibility;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import nf.fr.ephys.omnifluids.api.IFluidAction;

import java.util.Random;

public class FluidEffectMilk implements IFluidAction {
	private ItemStack milk = new ItemStack(Items.milk_bucket);

	@Override
	public void onTouch(World world, int x, int y, int z, Entity entity) {
		if (entity instanceof EntityLivingBase) {
			((EntityLivingBase) entity).curePotionEffects(milk);
		}
	}

	@Override
	public void onTick(World world, int x, int y, int z, Random rand) {

	}
}