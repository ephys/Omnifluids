package nf.fr.ephys.omnifluids.common.core;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import nf.fr.ephys.omnifluids.common.block.FluidBlock;
import nf.fr.ephys.omnifluids.helper.BlockHelper;

public class EventHandler {
	@SubscribeEvent
	public void fillBucket(FillBucketEvent event) {
		Block block = event.world.getBlock(event.target.blockX, event.target.blockY, event.target.blockZ);

		if (block instanceof FluidBlock) {
			ItemStack filledBucket = FluidContainerRegistry.fillFluidContainer(new FluidStack(((FluidBlock) block).getFluid(), 1000), event.current);

			if (filledBucket == null) {
				event.setResult(Event.Result.DENY);
				event.setCanceled(true);

				return;
			}

			event.setResult(Event.Result.ALLOW);
			event.result = filledBucket;
			event.world.setBlockToAir(event.target.blockX, event.target.blockY, event.target.blockZ);
		}
	}

	@SubscribeEvent
	public void emptyBucket(PlayerInteractEvent event) {
		if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) return;

		ItemStack stack = event.entityPlayer.getHeldItem();

		if (FluidContainerRegistry.isBucket(stack)) {
			FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(stack);

			if (fluid == null || !(fluid.getFluid().getBlock() instanceof FluidBlock)) return;

			if (!BlockHelper.placeFluidInWorld(event.entityPlayer, BlockHelper.getAdjacentBlock(event.x, event.y, event.z, event.face), BlockHelper.getOppositeSide(event.face), stack, event.world, fluid)) return;

			ItemStack bucket = new ItemStack(Items.bucket);

			stack.stackSize--;
			if (!event.entityPlayer.inventory.addItemStackToInventory(bucket)) {
				event.entityPlayer.dropPlayerItemWithRandomChoice(bucket, false);
			}

			event.entityPlayer.inventory.markDirty();
		}
	}
}