package nf.fr.ephys.omnifluids.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import nf.fr.ephys.omnifluids.api.IFluidAction;

import java.util.Random;

public class FluidBlock extends BlockFluidClassic {
	private IFluidAction action;
	private FluidStack fluid;

	public FluidBlock(Fluid fluid, Material material) {
		super(fluid, material);

		this.fluid = new FluidStack(fluid, FluidContainerRegistry.BUCKET_VOLUME);

		this.setBlockName(fluid.getUnlocalizedName());
	}

	public void setFluidAction(IFluidAction action) {
		this.action = action;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if (action != null)
			action.onTouch(world, x, y, z, entity);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if (action != null)
			action.onTick(world, x, y, z, rand);

		super.updateTick(world, x, y, z, rand);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		Fluid fluid = this.fluid.getFluid();
		IIcon icon = side <= 1 ? fluid.getStillIcon() : fluid.getFlowingIcon();

		if (icon == null)
			return fluid.getIcon();

		return icon;
	}

	@Override
	public String getLocalizedName() {
		return fluid.getLocalizedName();
	}

	@Override
	public String getUnlocalizedName() {
		return fluid.getUnlocalizedName();
	}

	@Override
	public int getBlockColor() {
		return fluid.getFluid().getColor();
	}

	@Override
	public int getRenderColor(int p_149741_1_) {
		return fluid.getFluid().getColor();
	}

	@Override
	public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_) {
		return fluid.getFluid().getColor();
	}
}