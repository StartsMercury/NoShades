package com.github.startsmercury.noshades;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import net.fabricmc.loader.api.FabricLoader;

public class NoShadesConfig {
	public static class Main {
		private boolean enabled = true;

		private boolean reloadOnSave;

		private float defaultLightness;

		public float getDefaultLightness() {
			return this.defaultLightness;
		}

		public boolean isEnabled() {
			return this.enabled;
		}

		public boolean isReloadOnSave() {
			return this.reloadOnSave;
		}

		public void setDefaultLightness(final float defaultLightness) {
			this.defaultLightness = defaultLightness;
		}

		public void setEnabled(final boolean enabled) {
			this.enabled = enabled;
		}

		public void setReloadOnSave(final boolean reloadOnSave) {
			this.reloadOnSave = reloadOnSave;
		}
	}

	public static class Shading {
		private boolean defaultLightnessSeperated;

		private float defaultLightness;

		private Map<String, Float> entries = new LinkedHashMap<>();

		public float getDefaultLightness() {
			return this.defaultLightness;
		}

		public Map<String, Float> getEntries() {
			return this.entries;
		}

		public boolean isDefaultLightnessSeperated() {
			return this.defaultLightnessSeperated;
		}

		public void setDefaultLightness(final float defaultLightness) {
			this.defaultLightness = defaultLightness;
		}

		public void setDefaultLightnessSeperated(final boolean independant) {
			this.defaultLightnessSeperated = independant;
		}

		public void setEntries(final Map<String, Float> entries) {
			this.entries = entries;
		}
	}

	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	public static final NoShadesConfig load() {
		final File file = new File(FabricLoader.getInstance().getConfigDir().toString(), "noshades.json");
		NoShadesConfig config;

		try (final FileReader fr = new FileReader(file);
				final BufferedReader br = new BufferedReader(fr);
				final JsonReader jr = new JsonReader(br)) {
			config = GSON.fromJson(jr, NoShadesConfig.class);
			config.file = file;

			NoShadesClientMod.LOGGER.debug("Config loaded.");
		} catch (final IOException ioe) {
			config = new NoShadesConfig();
			config.file = file;

			config.save();

			NoShadesClientMod.LOGGER.debug("Config created.");
		}

		return config;
	}

	private transient File file;

	private Main main = new Main();

	private Shading blockShading = new Shading();

	private Shading fluidShading = new Shading();

	public Shading getBlockShading() {
		return this.blockShading;
	}

	public Shading getFluidShading() {
		return this.fluidShading;
	}

	public Main getMain() {
		return this.main;
	}

	public void save() {
		if (this.file == null) {
			return;
		}

		try (final FileWriter fw = new FileWriter(this.file); final BufferedWriter bw = new BufferedWriter(fw)) {
			GSON.toJson(this, bw);

			NoShadesClientMod.LOGGER.debug("Saved config of " + toString());
		} catch (final IOException ioe) {
			NoShadesClientMod.LOGGER.warn(ioe);
		}
	}

	public void setBlockShading(final Shading blockShading) {
		this.blockShading = blockShading;
	}

	public void setFluidShading(final Shading fluidShading) {
		this.fluidShading = fluidShading;
	}

	public void setMain(final Main main) {
		this.main = main;
	}
}
