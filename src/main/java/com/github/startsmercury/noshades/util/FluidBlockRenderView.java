package com.github.startsmercury.noshades.util;

import com.github.startsmercury.noshades.NoShadesMod;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;

public class FluidBlockRenderView extends FilterBlockRenderView {
	public FluidBlockRenderView(final BlockRenderView delegate) {
		super(delegate);
	}

	@Override
	public float getBrightness(final Direction paramDirection, final boolean paramBoolean) {
		return NoShadesMod
				.getMultipliedFluidShade(NoShadesMod.getBrightness(paramDirection, paramBoolean, isDarkened()));
	}

	protected boolean isDarkened() {
		return super.delegate instanceof ClientWorld client && client.getSkyProperties().isDarkened();
	}
}
