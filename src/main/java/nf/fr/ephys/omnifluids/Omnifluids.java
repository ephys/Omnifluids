package nf.fr.ephys.omnifluids;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;
import nf.fr.ephys.omnifluids.common.core.CommonProxy;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mod(modid = Omnifluids.MODID, version = Omnifluids.VERSION, name = Omnifluids.NAME, dependencies = "after:*;required-after:cookiecore;required-after:Forge@[10.13.0.1188,)")
public class Omnifluids {
    public static final String MODID = "omnifluids";
    public static final String VERSION = "1.2.0";
	public static final String NAME = "Omnifluids";

	@Mod.Instance(MODID)
	public static Omnifluids instance;

	@SidedProxy(clientSide = "nf.fr.ephys.omnifluids.common.core.CommonProxy",
			serverSide = "nf.fr.ephys.omnifluids.client.core.ClientProxy")
	public static CommonProxy proxy;

	private Logger logger;

	public static final List<String> FLUID_BLACKLIST = new ArrayList<>();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();

		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();

		Collections.addAll(FLUID_BLACKLIST, config.get("fluids", "blacklist", new String[] {"glass"}, "List of fluids this mod should not generate a block for.").getStringList());

		proxy.preInit(event);
	}

	@EventHandler
    public void init(FMLInitializationEvent event) {
		proxy.init(event);
    }

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

	public static Logger getLogger() {
		return instance.logger;
	}
}
