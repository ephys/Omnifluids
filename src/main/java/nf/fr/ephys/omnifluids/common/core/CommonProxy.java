package nf.fr.ephys.omnifluids.common.core;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import nf.fr.ephys.omnifluids.common.registry.FluidBlockRegistry;

public class CommonProxy {
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new EventHandler());
	}

	public void init(FMLInitializationEvent event) {
		FluidBlockRegistry.generateFluidBlocks();
		// FluidBlockRegistry.scanBuckets();
	}

	public void postInit(FMLPostInitializationEvent event) {
		FluidBlockRegistry.addFluidActions();
	}
}