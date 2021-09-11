package com.github.startsmercury.noshades.mixin.sodium;

import java.lang.reflect.Field;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.github.startsmercury.noshades.util.Factory;
import com.github.startsmercury.noshades.util.FluidFlatLightPipeline;
import com.github.startsmercury.noshades.util.FluidSmoothLightPipeline;

import me.jellysquid.mods.sodium.client.model.light.LightPipeline;
import me.jellysquid.mods.sodium.client.model.light.data.LightDataAccess;
import me.jellysquid.mods.sodium.client.model.light.flat.FlatLightPipeline;
import me.jellysquid.mods.sodium.client.model.light.smooth.SmoothLightPipeline;
import me.jellysquid.mods.sodium.client.render.pipeline.FluidRenderer;

@Mixin(FluidRenderer.class)
public abstract class FluidRendererMixin {
	private static final Factory.Filter FILTER = new Factory.WeakFilter();

	private FluidRendererMixin() {
	}

	private LightDataAccess getDeclaredLightCache(final LightPipeline lighter, final Class<?> clazz)
			throws IllegalAccessException, IllegalArgumentException {
		Field lightCacheField;

		try {
			lightCacheField = clazz.getDeclaredField("lightCache");
		} catch (NoSuchFieldException nsfe) {
			throw new IllegalAccessException(nsfe.getMessage());
		}

		lightCacheField.setAccessible(true);

		return (LightDataAccess) lightCacheField.get(lighter);
	}

	private LightDataAccess getLightCache(final LightPipeline lighter) {
		for (Class<?> superclass = lighter.getClass(); LightPipeline.class
				.isAssignableFrom(superclass); superclass = superclass.getSuperclass()) {
			try {
				return getDeclaredLightCache(lighter, superclass);
			} catch (IllegalAccessException | IllegalArgumentException iae) {
			}
		}

		return null;
	}

	@ModifyVariable(method = "calculateQuadColors", at = @At("HEAD"), argsOnly = true)
	private LightPipeline modifyLighterInCalculateQuadColors(final LightPipeline lighter) {
		return FILTER.of(getLightCache(lighter), lighter instanceof FlatLightPipeline ? FluidFlatLightPipeline::new
				: lighter instanceof SmoothLightPipeline ? FluidSmoothLightPipeline::new : lightCache -> lighter);
	}

	@ModifyVariable(method = "calculateQuadColors", at = @At("HEAD"), argsOnly = true)
	private float modifyWorldInCalculateQuadColors(final float brightness) {
		return 1.0F;
	}
}
