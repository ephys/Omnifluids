package nf.fr.ephys.omnifluids.common.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import nf.fr.ephys.omnifluids.Omnifluids;
import nf.fr.ephys.omnifluids.common.ItemFluidBlock;
import nf.fr.ephys.omnifluids.common.block.FluidBlock;
import nf.fr.ephys.omnifluids.common.compatibility.*;

import java.util.Map;

public class FluidBlockRegistry {
	public static void generateFluidBlocks() {
		Map<String, Fluid> fluids = FluidRegistry.getRegisteredFluids();

		for (Map.Entry<String, Fluid> entry: fluids.entrySet()) {
			Fluid fluid = entry.getValue();

			if (!fluid.canBePlacedInWorld() && !Omnifluids.FLUID_BLACKLIST.contains(fluid.getName())) {
				Omnifluids.getLogger().info("Fluid " + fluid.getName() + " has no block version, adding one");

				fixFluids(fluid);

				FluidBlock block = new FluidBlock(fluid, fluid.getTemperature() > 1000 ? Material.lava : Material.water);

				GameRegistry.registerBlock(block, ItemFluidBlock.class, fluid.getUnlocalizedName());
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
		} else if (fluid.getName().equals("liquidnitrogen")) {
			fluid.setDensity(-1);
			fluid.setTemperature(63);
		}
	}

//fluid.binnie.mashGrain                          => slowness 1   15s
	// binnie.mashRye binnie.mashCorn
	public static void addFluidActions() {
		Omnifluids.getLogger().info("Adding custom fluid effects to our blocks");
		Fluid milk = FluidRegistry.getFluid("milk");

		if (milk != null && milk.getBlock() instanceof FluidBlock) {
			((FluidBlock) milk.getBlock()).setFluidAction(new FluidEffectMilk());
		}

		FluidEffectHealthBoost.addEffect(new String[] {"binnie.dna.raw"});

		FluidEffectAlcool.addEffect(new String[] {"short.mead",
				"binnie.beerstout", "binnie.winered", "binnie.brandyplum", "binnie.liquorfruit", "binnie.beercorn", "binnie.liqueurcoffee",
				"binnie.wineelderberry", "binnie.brandyapple", "binnie.liqueurlemon", "binnie.liqueurhazelnut",
				"binnie.liquorapple", "binnie.winewhite", "binnie.spiritgin", "binnie.brandycherry", "binnie.whiskey",
				"binnie.rumwhite", "binnie.rumdark", "binnie.liqueurherbal", "binnie.beerwheat", "binnie.liquorelderberry",
				"binnie.liqueurcinnamon", "binnie.ciderpear", "binnie.ciderapple", "binnie.vodka", "binnie.liqueurmint",
				"binnie.winepineapple", "binnie.liqueuralmond", "binnie.wineplum", "binnie.liqueurpeach", "binnie.whiskeywheat",
				"binnie.brandyapricot", "binnie.liqueurmelon", "binnie.winecitrus", "binnie.liquorpear", "binnie.liqueurraspberry",
				"binnie.liqueurblackberry", "binnie.liqueurcherry", "binnie.mashwheat", "binnie.winebanana", "binnie.wineapricot",
				"binnie.liqueurbanana", "binnie.brandygrape", "binnie.liqueurchocolate", "binnie.whiskeyrye", "binnie.winecranberry",
				"binnie.whiskeycorn", "binnie.brandyelderberry", "binnie.liqueuranise", "binnie.winecherry", "binnie.brandyfruit",
				"binnie.tequila", "binnie.liquorcherry", "binnie.ciderpeach", "binnie.spiritsugarcane", "binnie.winecarrot",
				"binnie.winetomato", "binnie.wineagave", "binnie.brandypear", "binnie.winesparkling", "binnie.liquorapricot",
				"binnie.beerlager", "binnie.beerrye", "binnie.winefortified", "binnie.beerale", "binnie.brandycitrus",
				"binnie.liqueurorange", "binnie.liqueurblackcurrant"
		});

		FluidEffectBiofuel.addEffect(new String[] {"biomass", "bioethanol"});

		FluidEffectCold.addEffect(new String[] {"liquidnitrogen", "ice"});

		FluidEffectPoison.addEffect(new String[] {"turpentine", "binnie.bacteriaVector", "binnie.bacteriaPoly", "binnie.bacteria"});

		FluidEffectJump.addEffect(new String[] {"latex"});

		FluidEffectAcid.addEffect(new String[] {"acid"});

		Omnifluids.getLogger().info("Fluid effects added !");
	}
}
