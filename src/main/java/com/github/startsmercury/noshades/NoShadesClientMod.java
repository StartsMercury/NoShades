package com.github.startsmercury.noshades;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ClientModInitializer;

public class NoShadesClientMod implements ClientModInitializer {
	public static final Logger LOGGER;

	private static NoShadesConfig config;

	static {
		LOGGER = LogManager.getLogger("NoShades");

		LOGGER.debug("Logger created.");
	}

	public static final NoShadesConfig getConfig() {
		return NoShadesClientMod.config;
	}

	public static final void setConfig(final NoShadesConfig config) {
		NoShadesClientMod.config = config;
	}

	@Override
	public void onInitializeClient() {
		setConfig(NoShadesConfig.load());
	}
}
