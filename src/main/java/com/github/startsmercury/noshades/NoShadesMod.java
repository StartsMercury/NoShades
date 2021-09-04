package com.github.startsmercury.noshades;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ClientModInitializer;

public class NoShadesMod implements ClientModInitializer {
	private static NoShadesConfig config;

	private static Logger logger;

	public static int getBlockShadeMultiplier() {
		return getConfig().getBlockShadeMultiplier();
	}

	public static NoShadesConfig getConfig() {
		if (NoShadesMod.config == null) {
			NoShadesMod.config = NoShadesConfig.load();
		}

		return NoShadesMod.config;
	}

	public static int getFluidShadeMultiplier() {
		return getConfig().getFluidShadeMultiplier();
	}

	public static int getGlobalShadeMultiplier() {
		return getConfig().getGlobalShadeMultiplier();
	}

	public static Logger getLogger() {
		if (NoShadesMod.logger == null || !logger.getName().equals("noshades")) {
			NoShadesMod.logger = LogManager.getLogger("noshades");

			NoShadesMod.getLogger().debug("Logger created");
		}

		return NoShadesMod.logger;
	}

	public static void setBlockShadeMultiplier(int blockShadeMultiplier) {
		getConfig().setBlockShadeMultiplier(blockShadeMultiplier);
	}

	public static void setConfig(NoShadesConfig config) {
		NoShadesMod.config = config == null ? NoShadesConfig.load() : config;
	}

	public static void setFluidShadeMultiplier(int fluidShadeMultiplier) {
		getConfig().setFluidShadeMultiplier(fluidShadeMultiplier);
	}

	public static void setGlobalShadeMultiplier(int globalShadeMultiplier) {
		getConfig().setGlobalShadeMultiplier(globalShadeMultiplier);
	}

	public static void setLogger(Logger logger) {
		if (logger == null || !logger.getName().equals("noshades")) {
			logger = LogManager.getLogger("noshades");

			logger.debug("Logger created");
		}

		NoShadesMod.logger = logger;
	}

	@Override
	public void onInitializeClient() {}
}
