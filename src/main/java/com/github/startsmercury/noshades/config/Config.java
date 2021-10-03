package com.github.startsmercury.noshades.config;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.github.startsmercury.noshades.functions.BrightnessProvider;
import com.github.startsmercury.noshades.functions.SmartBrightnessProvider;

import net.minecraft.client.MinecraftClient;

public class Config implements Serializable {
	public static class Lightness implements Serializable {
		@Serial
		private static final long serialVersionUID = -58071558832345503L;

		private boolean separate;

		private float lightness;

		private List<String> mappings;

		public Lightness() {
			this(false, 0.0F, new ArrayList<>());
		}

		public Lightness(final boolean separate, final float lightness, final List<String> mappings) {
			this.separate = separate;
			this.lightness = lightness;
			this.mappings = mappings;
		}

		public float getLightness() {
			return this.lightness;
		}

		public List<String> getMappings() {
			return this.mappings;
		}

		public boolean isSeparate() {
			return this.separate;
		}

		public void setLightness(final float lightness) {
			this.lightness = lightness;
		}

		public void setMappings(final List<String> mappings) {
			this.mappings = mappings;
		}

		public void setSeparate(final boolean separate) {
			this.separate = separate;
		}
	}

	@Serial
	private static final long serialVersionUID = 6824967771503017053L;

	private boolean enabled;

	private boolean reloadChunks;

	private float lightness;

	private Lightness blockLightness;

	private Lightness fluidLightness;

	private transient BrightnessProvider provider;

	public Config() {
		this(true, true, 0.0F, new Lightness(), new Lightness());
	}

	public Config(final boolean enabled, final boolean reloadChunks, final float lightness,
			final Lightness blockLightness, final Lightness fluidLightness) {
		this.enabled = enabled;
		this.reloadChunks = reloadChunks;
		this.lightness = lightness;
		this.blockLightness = blockLightness;
		this.fluidLightness = fluidLightness;
		this.provider = new SmartBrightnessProvider();
	}

	public Lightness getBlockLightness() {
		return this.blockLightness;
	}

	public Lightness getFluidLightness() {
		return this.fluidLightness;
	}

	public float getLightness() {
		return this.lightness;
	}

	public BrightnessProvider getProvider() {
		return this.provider;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public boolean isReloadChunks() {
		return this.reloadChunks;
	}

	public void setBlockLightness(final Lightness blockLightness) {
		this.blockLightness = blockLightness;
	}

	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

	public void setFluidLightness(final Lightness fluidLightness) {
		this.fluidLightness = fluidLightness;
	}

	public void setLightness(final float lightness) {
		this.lightness = lightness;
	}

	public void setProvider(final BrightnessProvider provider) {
		this.provider = provider;
	}

	@SuppressWarnings("resource")
	public void setReloadChunks(final boolean reloadChunks) {
		this.reloadChunks = reloadChunks;

		if (reloadChunks) {
			MinecraftClient.getInstance().worldRenderer.reload();
		}
	}
}