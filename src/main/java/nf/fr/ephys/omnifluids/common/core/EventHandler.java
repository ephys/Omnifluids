package nf.fr.ephys.omnifluids.common.core;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import nf.fr.ephys.omnifluids.common.block.FluidBlock;

public class EventHandler {
	@SubscribeEvent
	public void fillBucket(FillBucketEvent event) {
		Block block = event.world.getBlock(event.target.blockX, event.target.blockY, event.target.blockZ);

		if (block instanceof FluidBlock) {
			event.setResult(Event.Result.DENY);
			event.setCanceled(true);
		}
	}
}