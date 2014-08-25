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

			if (!fluid.canBePlacedInWorld()) {
				Omnifluids.getLogger().info("Fluid " + fluid.getName() + " has no block version, adding one");

				if (FluidRegistry.getFluid(fluid.getName()) == null) {
					System.out.println(fluid.getName() + " IS REGISTERED BUT NOT FOUND");
				}

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
		Omnifluids.getLogger().info("Adding custom fluid effects, this will take a while");
		Fluid milk = FluidRegistry.getFluid("milk");

		if (milk != null && milk.getBlock() instanceof FluidBlock) {
			((FluidBlock) milk.getBlock()).setFluidAction(new FluidEffectMilk());
		}

		FluidEffectHealthBoost.addEffect(new String[] {"binnie.dna.raw"});

		FluidEffectAlcool.addEffect(new String[] {"short.mead",
				"binnie.beertout", "binnie.wineed", "binnie.brandylum", "binnie.liquorruit", "binnie.beerorn", "binnie.liqueuroffee",
				"binnie.winelderberry", "binnie.brandypple", "binnie.liqueuremon", "binnie.liqueurazelnut",
				"binnie.liquorpple", "binnie.winehite", "binnie.spiritin", "binnie.brandyherry", "binnie.whiskey",
				"binnie.rumhite", "binnie.rumark", "binnie.liqueurerbal", "binnie.beerheat", "binnie.liquorlderberry",
				"binnie.liqueurinnamon", "binnie.ciderear", "binnie.ciderpple", "binnie.vodka", "binnie.liqueurint",
				"binnie.wineineapple", "binnie.liqueurlmond", "binnie.winelum", "binnie.liqueureach", "binnie.whiskeyheat",
				"binnie.brandypricot", "binnie.liqueurelon", "binnie.wineitrus", "binnie.liquorear", "binnie.liqueuraspberry",
				"binnie.liqueurlackberry", "binnie.liqueurherry", "binnie.mashheat", "binnie.wineanana", "binnie.winepricot",
				"binnie.liqueuranana", "binnie.brandyrape", "binnie.liqueurhocolate", "binnie.whiskeyye", "binnie.wineranberry",
				"binnie.whiskeyorn", "binnie.brandylderberry", "binnie.liqueurnise", "binnie.wineherry", "binnie.brandyruit",
				"binnie.tequila", "binnie.liquorherry", "binnie.cidereach", "binnie.spiritugarcane", "binnie.winearrot",
				"binnie.wineomato", "binnie.winegave", "binnie.brandyear", "binnie.wineparkling", "binnie.liquorpricot",
				"binnie.beerager", "binnie.beerrye", "binnie.winefortified", "binnie.beerale", "binnie.brandycitrus",
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
