package com.github.startsmercury.noshades.util;

import com.github.startsmercury.noshades.NoShadesMod;

import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;

public class FluidBlockRenderView extends FilterBlockRenderView {
	public FluidBlockRenderView(BlockRenderView delegate) {
		super(delegate);
	}

	@Override
	public float getBrightness(Direction paramDirection, boolean paramBoolean) {
		final float x = super.getBrightness(paramDirection, paramBoolean);
		final float y = 1F - NoShadesMod.getFluidShadeMultiplier() * NoShadesMod.getGlobalShadeMultiplier()
				* (1F - x) / 10000F;
		
		NoShadesMod.getLogger().info(() -> this + ": " + x + " -> " + y);
		
		return y;
	}
}
