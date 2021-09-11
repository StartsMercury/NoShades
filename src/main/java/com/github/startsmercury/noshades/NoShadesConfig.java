package com.github.startsmercury.noshades;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.Level;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;

import net.fabricmc.loader.api.FabricLoader;

public class NoShadesConfig {
	public static NoShadesConfig load() {
		final File file = new File(FabricLoader.getInstance().getConfigDir().toString(), "noshades.toml");
		final NoShadesConfig config;

		if (file.exists()) {
			final Toml configTOML = (new Toml()).read(file);
			config = configTOML.to(NoShadesConfig.class);
			config.file = file;

			NoShadesMod.LOGGER.debug("Config loaded.");
		} else {
			config = new NoShadesConfig();
			config.file = file;
			config.save(null);

			NoShadesMod.LOGGER.debug("Config created.");
		}

		return config;
	}

	private int blockShadeMultiplier;

	private transient File file;

	private int fluidShadeMultiplier;

	private boolean reloadOnSave;

	public NoShadesConfig() {
		setBlockShadeMultiplier(100);
		setFluidShadeMultiplier(100);
		setReloadOnSave(true);
	}

	public int getBlockShadeMultiplier() {
		return this.blockShadeMultiplier;
	}

	public int getFluidShadeMultiplier() {
		return this.fluidShadeMultiplier;
	}

	public boolean isReloadOnSave() {
		return this.reloadOnSave;
	}

	public void save() {
		save(Level.DEBUG);
	}

	private void save(final Level level) {
		try {
			TomlWriter writer = new TomlWriter();

			writer.write(this, this.file);

			if (level != null) {
				NoShadesMod.LOGGER.log(level, "Saved config of " + toString());
			}
		} catch (IOException ioe) {
			NoShadesMod.LOGGER.warn(ioe);
		}
	}

	public void setBlockShadeMultiplier(final int blockShadeMultiplier) {
		this.blockShadeMultiplier = blockShadeMultiplier;
	}

	public void setFluidShadeMultiplier(final int fluidShadeMultiplier) {
		this.fluidShadeMultiplier = fluidShadeMultiplier;
	}

	public void setReloadOnSave(final boolean reloadOnSave) {
		this.reloadOnSave = reloadOnSave;
	}
}
