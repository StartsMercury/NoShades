package com.github.startsmercury.noshades.util;

import me.jellysquid.mods.sodium.client.model.light.data.LightDataAccess;
import me.jellysquid.mods.sodium.client.model.light.data.QuadLightData;
import me.jellysquid.mods.sodium.client.model.light.smooth.SmoothLightPipeline;
import me.jellysquid.mods.sodium.client.model.quad.ModelQuadView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class FluidSmoothLightPipeline extends SmoothLightPipeline {
	public FluidSmoothLightPipeline(final LightDataAccess lightCache) {
		super(new FluidLightDataAccess(lightCache));
	}

	@Override
	public void calculate(final ModelQuadView quad, final BlockPos pos, final QuadLightData out, final Direction face,
			final boolean shade) {
		super.calculate(quad, pos, out, face, true);
	}
}
