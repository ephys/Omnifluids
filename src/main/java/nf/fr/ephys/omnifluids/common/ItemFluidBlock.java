package nf.fr.ephys.omnifluids.common;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemFluidBlock extends ItemBlock {
	public ItemFluidBlock(Block block) {
		super(block);
	}

	@Override
	public String getItemStackDisplayName(ItemStack p_77653_1_) {
		return this.field_150939_a.getLocalizedName();
	}
}
