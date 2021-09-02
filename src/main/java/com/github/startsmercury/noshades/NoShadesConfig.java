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
		} else {
			config = new NoShadesConfig();
			config.file = file;
			config.save();
		}

		return config;
	}

	private ShadeOverride blockEntityShadeOverride = ShadeOverride.DEFAULT;

	private ShadeOverride blockShadeOverride = ShadeOverride.DEFAULT;

	private ShadeOverride entityShadeOverride = ShadeOverride.DEFAULT;

	private transient File file;

	private ShadeOverride fluidShadeOverride = ShadeOverride.DEFAULT;

	private boolean reloadOnSave = true;

	private ShadeOverride globalShadeOverride = ShadeOverride.DEFAULT;

	public ShadeOverride getBlockEntityShadeOverride() {
		if (this.blockEntityShadeOverride == null) {
			this.blockEntityShadeOverride = ShadeOverride.DEFAULT;
		}

		return this.blockEntityShadeOverride;
	}

	public ShadeOverride getBlockShadeOverride() {
		if (this.blockShadeOverride == null) {
			this.blockShadeOverride = ShadeOverride.DEFAULT;
		}

		return this.blockShadeOverride;
	}

	public ShadeOverride getEntityShadeOverride() {
		if (this.entityShadeOverride == null) {
			this.entityShadeOverride = ShadeOverride.DEFAULT;
		}

		return this.entityShadeOverride;
	}

	public ShadeOverride getFluidShadeOverride() {
		if (this.fluidShadeOverride == null) {
			this.fluidShadeOverride = ShadeOverride.DEFAULT;
		}

		return this.fluidShadeOverride;
	}

	public ShadeOverride getGlobalShadeOverride() {
		if (this.globalShadeOverride == null) {
			this.globalShadeOverride = ShadeOverride.DEFAULT;
		}

		return this.globalShadeOverride;
	}

	public boolean isReloadOnSave() {
		return reloadOnSave;
	}

	public void save() {
		TomlWriter writer = new TomlWriter();
		try {
			writer.write(this, this.file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setBlockEntityShadeOverride(ShadeOverride blockEntityShadeOverride) {
		this.blockEntityShadeOverride = blockEntityShadeOverride == null ? ShadeOverride.DEFAULT
				: blockEntityShadeOverride;
	}

	public void setBlockShadeOverride(ShadeOverride blockShadeOverride) {
		this.blockShadeOverride = blockShadeOverride == null ? ShadeOverride.DEFAULT : blockShadeOverride;
	}

	public void setEntityShadeOverride(ShadeOverride entityShadeOverride) {
		this.entityShadeOverride = entityShadeOverride == null ? ShadeOverride.DEFAULT : entityShadeOverride;
	}

	public void setFluidShadeOverride(ShadeOverride fluidShadeOverride) {
		this.fluidShadeOverride = fluidShadeOverride == null ? ShadeOverride.DEFAULT : fluidShadeOverride;
	}

	public void setGlobalShadeOverride(ShadeOverride shadeOverride) {
		this.globalShadeOverride = shadeOverride == null ? ShadeOverride.DEFAULT : shadeOverride;
	}

	public void setReloadOnSave(boolean reloadOnSave) {
		this.reloadOnSave = reloadOnSave;
	}
}
