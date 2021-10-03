package com.github.startsmercury.noshades.config;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.github.startsmercury.noshades.functions.BrightnessProvider;
import com.github.startsmercury.noshades.functions.LinearBrightnessProvider;

import it.unimi.dsi.fastutil.objects.Object2FloatLinkedOpenHashMap;

public class BakedConfig {
	public static class Lightness {
		private final float lightness;

		private final Map<String, Float> mappings;

		public Lightness(final Config config, final Config.Lightness lightness) {
			if (!config.isEnabled()) {
				this.lightness = 0.0F;
				this.mappings = Map.of();
			} else if (lightness.isSeparate()) {
				this.lightness = lightness.getLightness();
				this.mappings = Collections.unmodifiableMap(remap(lightness.getMappings()));
			} else {
				this.lightness = config.getLightness();
				this.mappings = Map.of();
			}
		}

		public float getLightness() {
			return this.lightness;
		}

		public Map<String, Float> getMappings() {
			return this.mappings;
		}
	}

	private static final Map<String, Float> remap(final List<String> mappings) {
		final Object2FloatLinkedOpenHashMap<String> map = new Object2FloatLinkedOpenHashMap<>(mappings.size());

		for (final String element : mappings) {
			final String[] entry = element.split(";", 2);

			try {
				map.put(entry[0], Float.parseFloat(entry[1]));
			} catch (final Throwable throwable) {
			}
		}

		return map;
	}

	private final Lightness blockLightness;

	private final Lightness fluidLightness;

	private final transient BrightnessProvider provider;

	public BakedConfig(final Config config) {
		this.blockLightness = new Lightness(config, config.getBlockLightness());
		this.fluidLightness = new Lightness(config, config.getFluidLightness());

		if (config.isEnabled()) {
			this.provider = config.getProvider();
		} else {
			this.provider = new LinearBrightnessProvider();
		}
	}

	public Lightness getBlockLightness() {
		return this.blockLightness;
	}

	public Lightness getFluidLightness() {
		return this.fluidLightness;
	}

	public BrightnessProvider getProvider() {
		return this.provider;
	}
}
