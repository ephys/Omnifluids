package nf.fr.ephys.omnifluids.common.registry;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import nf.fr.ephys.omnifluids.Omnifluids;
import nf.fr.ephys.omnifluids.common.ItemFluidBlock;
import nf.fr.ephys.omnifluids.common.block.FluidBlock;
import nf.fr.ephys.omnifluids.common.compatibility.FluidEffectMead;
import nf.fr.ephys.omnifluids.common.compatibility.FluidEffectMilk;

import java.util.Map;

public class FluidBlockRegistry {
	public static void generateFluidBlocks() {
		Map<String, Fluid> fluids = FluidRegistry.getRegisteredFluids();

		for (Map.Entry<String, Fluid> entry: fluids.entrySet()) {
			Fluid fluid = entry.getValue();

			if (!fluid.canBePlacedInWorld()) {
				Omnifluids.getLogger().info("Fluid " + fluid.getUnlocalizedName() + " has no block version, adding one");

				fixFluids(fluid);

				FluidBlock block = new FluidBlock(fluid, fluid.getTemperature() > 1000 ? Material.lava : Material.water);

				GameRegistry.registerBlock(block, ItemFluidBlock.class, fluid.getUnlocalizedName());
			}
		}
	}

	public static void scanBuckets() {
		for (Object o : Item.itemRegistry) {
			if (o instanceof Item) {
				System.out.println(o + ((Item) o).getUnlocalizedName());
			}
		}
	}

	private static void fixFluids(Fluid fluid) {
		if (fluid.getName().equals("glass")) {
			fluid.setTemperature(1273);
			fluid.setLuminosity(5);
			fluid.setDensity(2400);
			fluid.setViscosity(3210);
		} else if (fluid.getName().equals("ice")) {
			fluid.setTemperature(270);
			fluid.setDensity(1160);
			fluid.setViscosity(8000);
		} else if (fluid.getName().equals("seedoil")) {
			fluid.setDensity(1200);
			fluid.setViscosity(800);
		}
	}

	public static void addFluidActions() {
		if (Loader.isModLoaded("Forestry")) {
			compatForestry();
		}

		compatVanilla();
	}

	private static void compatForestry() {
		Omnifluids.getLogger().info("Forestry compatibility in progress");

		/* fluid.biomass, fluid.juice */

		Fluid mead = FluidRegistry.getFluid("short.mead");
		if (mead == null)
			mead = FluidRegistry.getFluid("mead");

		if (mead != null && mead.getBlock() instanceof FluidBlock) {
			Omnifluids.getLogger().info("Added special effect to short mead fluid");
			((FluidBlock) mead.getBlock()).setFluidAction(new FluidEffectMead());
		} else {
			Omnifluids.getLogger().info("Skipping short mead effect: block is not from us");
		}

		Fluid ethanol = FluidRegistry.getFluid("short.bioethanol");

		if (ethanol != null && ethanol.getBlock() instanceof FluidBlock) {
			Omnifluids.getLogger().info("Added special effect to short mead fluid");
			((FluidBlock) ethanol.getBlock()).setFluidAction(new FluidEffectMead());
		} else {
			Omnifluids.getLogger().info("Skipping short mead effect: block is not from us");
		}
	}

	private static void compatVanilla() {
		Omnifluids.getLogger().info("Vanilla compatibility in progress");
		Fluid milk = FluidRegistry.getFluid("milk");

		if (milk != null && milk.getBlock() instanceof FluidBlock) {
			((FluidBlock) milk.getBlock()).setFluidAction(new FluidEffectMilk());
			Omnifluids.getLogger().info("Added special effect to milk fluid");
		} else
			Omnifluids.getLogger().info("Skipping milk effect: block is not from us");
	}
}
