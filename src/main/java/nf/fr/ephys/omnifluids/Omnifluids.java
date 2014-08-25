package nf.fr.ephys.omnifluids;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import nf.fr.ephys.omnifluids.common.core.CommonProxy;
import org.apache.logging.log4j.Logger;

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

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();

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
