package nf.fr.ephys.omnifluids.common.core;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import nf.fr.ephys.cookiecore.helpers.BlockHelper;
import nf.fr.ephys.cookiecore.helpers.FluidHelper;
import nf.fr.ephys.omnifluids.common.block.FluidBlock;

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

			// deny block use as we're calling it now
			if (event.world.getBlock(event.x, event.y, event.z).onBlockActivated(event.world, event.x, event.y, event.z, event.entityPlayer, event.face, 0, 0, 0)) {
				event.useBlock = Event.Result.DENY;

				return;
			}

			if (!FluidHelper.playerPlaceFluid(event.entityPlayer, BlockHelper.getAdjacentBlock(event.x, event.y, event.z, event.face), BlockHelper.getOppositeSide(event.face), stack, event.world, fluid)) {
				return;
			}

			ItemStack bucket = new ItemStack(Items.bucket);

			if (stack.stackSize == 1) {
				event.entityPlayer.setCurrentItemOrArmor(0, bucket);

				stack.stackSize = 0;
			} else {
				stack.stackSize--;

				if(!event.entityPlayer.inventory.addItemStackToInventory(bucket)) {
					event.entityPlayer.dropPlayerItemWithRandomChoice(bucket, false);
				}
			}

			if (event.entityPlayer instanceof EntityPlayerMP) {
				((EntityPlayerMP) event.entityPlayer).mcServer.getConfigurationManager().syncPlayerInventory((EntityPlayerMP) event.entityPlayer);
			}
		}
	}
}