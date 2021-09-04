package com.github.startsmercury.noshades;

import java.io.File;
import java.io.IOException;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;

import net.fabricmc.loader.api.FabricLoader;

public class NoShadesConfig {
	public static NoShadesConfig load() {
		File file = new File(FabricLoader.getInstance().getConfigDir().toString(), "noshades.toml");
		NoShadesConfig config;

		if (file.exists()) {
			Toml configTOML = (new Toml()).read(file);
			config = configTOML.to(NoShadesConfig.class);
			config.file = file;

			NoShadesMod.getLogger().debug("Config loaded.");
		} else {
			config = new NoShadesConfig();
			config.file = file;
			config.save(false);

			NoShadesMod.getLogger().debug("Config created.");
		}

		return config;
	}

	private int blockShadeMultiplier;

	private transient File file;

	private int fluidShadeMultiplier;

	private int globalShadeMultiplier;

	private boolean reloadOnSave;

	public NoShadesConfig() {
		setBlockShadeMultiplier(100);
		setFluidShadeMultiplier(100);
		setGlobalShadeMultiplier(100);
		setReloadOnSave(true);
	}

	public int getBlockShadeMultiplier() {
		return this.blockShadeMultiplier;
	}

	public int getFluidShadeMultiplier() {
		return this.fluidShadeMultiplier;
	}

	public int getGlobalShadeMultiplier() {
		return this.globalShadeMultiplier;
	}

	public boolean isReloadOnSave() {
		return this.reloadOnSave;
	}

	public void save() {
		save(true);
	}

	private void save(boolean logged) {
		try {
			TomlWriter writer = new TomlWriter();

			writer.write(this, this.file);

			if (logged) {
				NoShadesMod.getLogger().debug("Saved config, hash: " + hashCode());
			}
		} catch (IOException ioe) {
			NoShadesMod.getLogger().warn(ioe);
		}
	}

	public void setBlockShadeMultiplier(int blockShadeMultiplier) {
		if (this.blockShadeMultiplier != blockShadeMultiplier) {
			NoShadesMod.getLogger().debug(() -> "blockShadeMultiplier changed: " + this.blockShadeMultiplier + " -> "
					+ blockShadeMultiplier + ", hash: " + hashCode());
		}

		this.blockShadeMultiplier = blockShadeMultiplier;
	}

	public void setFluidShadeMultiplier(int fluidShadeMultiplier) {
		if (this.fluidShadeMultiplier != fluidShadeMultiplier) {
			NoShadesMod.getLogger().debug(() -> "fluidShadeMultiplier changed: " + this.fluidShadeMultiplier + " -> "
					+ fluidShadeMultiplier + ", hash: " + hashCode());
		}

		this.fluidShadeMultiplier = fluidShadeMultiplier;
	}

	public void setGlobalShadeMultiplier(int globalShadeMultiplier) {
		if (this.globalShadeMultiplier != globalShadeMultiplier) {
			NoShadesMod.getLogger().debug(() -> "globalShadeMultiplier changed: " + this.globalShadeMultiplier + " -> "
					+ globalShadeMultiplier + ", hash: " + hashCode());
		}

		this.globalShadeMultiplier = globalShadeMultiplier;
	}

	public void setReloadOnSave(boolean reloadOnSave) {
		if (this.reloadOnSave != reloadOnSave) {
			NoShadesMod.getLogger().debug(() -> "globalShadeMultiplier changed to " + this.globalShadeMultiplier
					+ " -> " + reloadOnSave + ", hash: " + hashCode());
		}

		this.reloadOnSave = reloadOnSave;
	}
}
