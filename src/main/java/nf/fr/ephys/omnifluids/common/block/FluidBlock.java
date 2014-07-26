package nf.fr.ephys.omnifluids.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import nf.fr.ephys.omnifluids.api.IFluidAction;

import java.util.Random;

public class FluidBlock extends BlockFluidClassic {
	private String unlocalizedName;
	private IFluidAction action;
	private Fluid fluid;

	public FluidBlock(Fluid fluid, Material material) {
		super(fluid, material);

		this.fluid = fluid;


		this.unlocalizedName = fluid.getUnlocalizedName();
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
		IIcon icon = side <= 1? fluid.getStillIcon() : fluid.getFlowingIcon();

		if (icon == null) return fluid.getIcon();

		return icon;
	}

	@Override
	public String getLocalizedName() {
		return StatCollector.translateToLocal(getUnlocalizedName());
	}

	@Override
	public String getUnlocalizedName() {
		return unlocalizedName;
	}
}
