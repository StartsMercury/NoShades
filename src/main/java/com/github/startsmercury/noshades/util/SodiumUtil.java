package com.github.startsmercury.noshades.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.ApiStatus.Internal;

import me.jellysquid.mods.sodium.client.model.light.LightPipeline;
import me.jellysquid.mods.sodium.client.model.light.data.LightDataAccess;
import me.jellysquid.mods.sodium.client.model.light.flat.FlatLightPipeline;
import me.jellysquid.mods.sodium.client.model.light.smooth.SmoothLightPipeline;
import net.minecraft.util.math.BlockPos;

@Internal
public class SodiumUtil {
	private static final Map<BlockPos, LightPipeline> LIGHTERS = new HashMap<>();

	public static final void closeLighter(final BlockPos pos) {
		NoShadesUtil.close(LIGHTERS.remove(pos));
	}

	public static final LightPipeline delegateLighter(final LightPipeline lighter, final LightDataAccess lightCache,
			final float lightness, final BlockPos pos) {
		final LightPipeline delegate;
		boolean ignore = false;

		if (lighter instanceof FlatLightPipeline) {
			delegate = new LightnessFlatLightPipeline(lightCache, lightness);
		} else if (lighter instanceof SmoothLightPipeline) {
			delegate = new LightnessSmoothLightPipeline(lightCache, lightness);
		} else {
			delegate = lighter;
			ignore = true;
		}

		if (!ignore && pos != null) {
			NoShadesUtil.close(LIGHTERS.put(pos, delegate));
		}

		return delegate;
	}

	public static final LightDataAccess getLightCache(final LightPipeline lighter) {
		try {
			final Field lightCacheField = lighter.getClass().getDeclaredField("lightCache");

			lightCacheField.setAccessible(true);

			return (LightDataAccess) lightCacheField.get(lighter);
		} catch (ExceptionInInitializerError | IllegalAccessException | IllegalArgumentException | NoSuchFieldException
				| NullPointerException e) {
			return null;
		}
	}
}
