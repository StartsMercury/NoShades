package com.github.startsmercury.noshades.entrypoint;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.ApiStatus.Internal;

import com.github.startsmercury.noshades.config.BakedConfig;
import com.github.startsmercury.noshades.config.Config;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class NoShadesClientMod implements ClientModInitializer {
	private static BakedConfig bakedConfig;

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	private static Config localConfig;

	private static final File LOCAL_CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir().toString(),
			"noshades.json");

	private static final Logger LOGGER = LogManager.getLogger("NoShades");

	public static final void bakeLocalConfig() {
		setBakedConfig(new BakedConfig(getLocalConfig()));
	}

	public static final BakedConfig getBakedConfig() {
		return NoShadesClientMod.bakedConfig;
	}

	public static Gson getGson() {
		return GSON;
	}

	public static final Config getLocalConfig() {
		return NoShadesClientMod.localConfig;
	}

	public static File getLocalConfigfile() {
		return LOCAL_CONFIG_FILE;
	}

	public static Logger getLogger() {
		return LOGGER;
	}

	public static final void loadLocalConfig() {
		final File file = getLocalConfigfile();

		try (final FileReader fr = new FileReader(file);
				final BufferedReader br = new BufferedReader(fr);
				final JsonReader jr = new JsonReader(br)) {
			setLocalConfig(getGson().fromJson(jr, Config.class));
		} catch (final IOException ioe) {
			setLocalConfig(new Config());

			saveLocalConfig();
		}
	}

	public static final void saveLocalConfig() {
		try (final FileWriter fw = new FileWriter(getLocalConfigfile());
				final BufferedWriter bw = new BufferedWriter(fw)) {
			getGson().toJson(getLocalConfig(), bw);
		} catch (final IOException ioe) {
		}
	}

	@Internal
	public static final void setBakedConfig(final BakedConfig bakedConfig) {
		NoShadesClientMod.bakedConfig = bakedConfig;
	}

	public static final void setLocalConfig(final Config localConfig) {
		NoShadesClientMod.localConfig = localConfig;
	}

	@Override
	public void onInitializeClient() {
		loadLocalConfig();
		bakeLocalConfig();
	}
}
