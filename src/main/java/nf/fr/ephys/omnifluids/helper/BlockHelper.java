package nf.fr.ephys.omnifluids.helper;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class BlockHelper {
	private static final int OPPOSITE_SIDES[] = { 1, 0, 3, 2, 5, 4 };

	public static int getOppositeSide(int side) {
		return OPPOSITE_SIDES[side];
	}

	public static boolean placeFluidInWorld(EntityPlayer player, int[] coords, int side, ItemStack stack, World world, FluidStack fluidstack) {
		Fluid fluid = fluidstack.getFluid();
		if (!fluid.canBePlacedInWorld() || fluidstack.tag != null) {
			ChatHelper.sendChatMessage(player, "Can't place this fluid in world. :(");

			return false;
		}

		if (player.canPlayerEdit(coords[0], coords[1], coords[2], side, stack)) {
			Block block = world.getBlock(coords[0], coords[1], coords[2]);
			Material material = block.getMaterial();

			if (material.isSolid()) return false;

			if (world.provider.isHellWorld && fluid == FluidRegistry.WATER) {
				world.playSoundEffect(coords[0] + 0.5D, coords[1] + 0.5D, coords[2] + 0.5D, "random.fizz", 0.5F, 2.6F + world.rand.nextFloat() - world.rand.nextFloat() * 0.8F);

				for (int l = 0; l < 8; ++l) {
					world.spawnParticle("largesmoke", coords[0] + Math.random(), coords[1] + Math.random(), coords[2] + Math.random(), 0.0D, 0.0D, 0.0D);
				}
			} else {
				if (!world.isRemote && !material.isLiquid()) {
					world.func_147480_a(coords[0], coords[1], coords[2], true);
				}

				world.setBlock(coords[0], coords[1], coords[2], BlockHelper.getBlockForFluid(fluid), 0, 3);
			}

			return true;
		}

		return false;
	}

	public static int[] getAdjacentBlock(int x, int y, int z, int side) {
		if ((side & 0b100) == 0b100)
			x += ((side & 0b001) == 0) ? -1 : 1;
		else if ((side & 0b010) == 0b010)
			z += ((side & 0b001) == 0) ? -1 : 1;
		else
			y += ((side & 0b001) == 0) ? -1 : 1;

		return new int[] {x, y, z};
	}

	public static Block getBlockForFluid(Fluid fluid) {
		Block fluidBlock = fluid.getBlock();

		if (fluidBlock == Blocks.water)
			fluidBlock = Blocks.flowing_water;
		else if (fluidBlock == Blocks.lava)
			fluidBlock = Blocks.flowing_lava;

		return fluidBlock;
	}
}
