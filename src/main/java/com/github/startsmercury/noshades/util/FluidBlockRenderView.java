package com.github.startsmercury.noshades.util;

import com.github.startsmercury.noshades.NoShadesMod;

import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;

public class FluidBlockRenderView extends MinecraftBlockRenderView {
	public FluidBlockRenderView(BlockRenderView delegate) {
		super(delegate);
	}

	@Override
	public float getBrightness(Direction paramDirection, boolean paramBoolean) {
		return NoShadesMod.getMultipliedFluidShade(super.getBrightness(paramDirection, paramBoolean));
	}
}
