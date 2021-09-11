package com.github.startsmercury.noshades.util;

import me.jellysquid.mods.sodium.client.model.light.data.LightDataAccess;
import net.minecraft.world.BlockRenderView;

public class FluidLightDataAccess extends FilterLightDataAccess {
	private static final Factory.Filter FILTER = new Factory.WeakFilter();

	public FluidLightDataAccess(final LightDataAccess delegate) {
		super(delegate);
	}

	@Override
	public BlockRenderView getWorld() {
		return FILTER.of(super.getWorld(), FluidBlockRenderView::new);
	}
}
