package nf.fr.ephys.omnifluids.common.damageSources;

import net.minecraft.util.DamageSource;

public class DamageSourceRegistry {
	public static final DamageSource cold = new DamageSource("coldWater").setFireDamage();
	public static final DamageSource acid = new DamageSource("acid");
}
