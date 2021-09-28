package com.github.startsmercury.noshades.util;

import me.jellysquid.mods.sodium.client.model.light.data.LightDataAccess;
import me.jellysquid.mods.sodium.client.model.light.data.QuadLightData;
import me.jellysquid.mods.sodium.client.model.light.flat.FlatLightPipeline;
import me.jellysquid.mods.sodium.client.model.quad.ModelQuadView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class LightnessFlatLightPipeline extends FlatLightPipeline implements AutoCloseable {
	private final LightnessLightDataAccess lightCache;

	public LightnessFlatLightPipeline(final LightDataAccess lightCache, final float lightness) {
		this(new LightnessLightDataAccess(lightCache, lightness));
	}

	public LightnessFlatLightPipeline(final LightnessLightDataAccess lightCache) {
		super(lightCache);

		this.lightCache = lightCache;
	}

	@Override
	public void calculate(final ModelQuadView quad, final BlockPos pos, final QuadLightData out, final Direction face,
			final boolean shade) {
		super.calculate(quad, pos, out, face, true);
	}

	@Override
	public void close() throws Exception {
		this.lightCache.close();
	}
}
