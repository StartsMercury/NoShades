package com.github.startsmercury.noshades;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.math.Direction;

public class NoShadesMod implements ClientModInitializer {
	private static NoShadesConfig config;

	public static final Logger LOGGER;

	static {
		LOGGER = LogManager.getLogger("NoShades");
		LOGGER.debug("Logger created");
	}

	public static int getBlockShadeMultiplier() {
		return getConfig().getBlockShadeMultiplier();
	}

	public static float getBrightness(final Direction direction, final boolean shaded, final boolean darkened) {
		if (!shaded) {
			return darkened ? 0.9F : 1.0F;
		}

		return switch (direction) {
		case DOWN -> darkened ? 0.9F : 0.5F;
		case UP -> darkened ? 0.9F : 1.0F;
		case NORTH, SOUTH -> 0.8F;
		case WEST, EAST -> 0.6F;
		};
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

	public static float getMultipliedBlockShade(final float defaultbrightness) {
		return getMultipliedShade(getBlockShadeMultiplier(), defaultbrightness);
	}

	public static float getMultipliedFluidShade(final float defaultbrightness) {
		return getMultipliedShade(getFluidShadeMultiplier(), defaultbrightness);
	}

	public static float getMultipliedShade(final int multiplier, final float defaultBrightness) {
		if (multiplier == 0) {
			return defaultBrightness;
		} else if (multiplier > 0) {
			return defaultBrightness * (100 - multiplier) / 100.0F;
		} else {
			return (100 - (100 + multiplier) * (1.0F - defaultBrightness)) / 100.0F;
		}
	}

	public static void setBlockShadeMultiplier(final int blockShadeMultiplier) {
		getConfig().setBlockShadeMultiplier(blockShadeMultiplier);
	}

	public static void setConfig(final NoShadesConfig config) {
		NoShadesMod.config = config == null ? NoShadesConfig.load() : config;
	}

	public static void setFluidShadeMultiplier(final int fluidShadeMultiplier) {
		getConfig().setFluidShadeMultiplier(fluidShadeMultiplier);
	}

	@Override
	public void onInitializeClient() {
	}
}
