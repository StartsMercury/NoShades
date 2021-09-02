package com.github.startsmercury.noshades;

import net.fabricmc.api.ClientModInitializer;

public class NoShadesMod implements ClientModInitializer {
	private static NoShadesConfig config;

	public static ShadeOverride getBlockEntityShadeOverride() {
		return getConfig().getBlockEntityShadeOverride();
	}

	public static ShadeOverride getBlockShadeOverride() {
		return getConfig().getBlockShadeOverride();
	}

	public static NoShadesConfig getConfig() {
		if (NoShadesMod.config == null) {
			NoShadesMod.config = NoShadesConfig.load();
		}

		return NoShadesMod.config;
	}

	public static ShadeOverride getEntityShadeOverride() {
		return getConfig().getEntityShadeOverride();
	}

	public static ShadeOverride getFluidShadeOverride() {
		return getConfig().getFluidShadeOverride();
	}

	public static ShadeOverride getGlobalShadeOverride() {
		return getConfig().getGlobalShadeOverride();
	}

	public static boolean isReloadOnSave() {
		return getConfig().isReloadOnSave();
	}

	public static void setBlockEntityShadeOverride(ShadeOverride blockEntityShadeOverride) {
		getConfig().setBlockEntityShadeOverride(blockEntityShadeOverride);
	}

	public static void setBlockShadeOverride(ShadeOverride blockShadeOverride) {
		getConfig().setBlockShadeOverride(blockShadeOverride);
	}

	public static void setConfig(NoShadesConfig config) {
		NoShadesMod.config = config == null ? NoShadesConfig.load() : config;
	}

	public static void setEntityShadeOverride(ShadeOverride entityShadeOverride) {
		getConfig().setEntityShadeOverride(entityShadeOverride);
	}

	public static void setFluidShadeOverride(ShadeOverride fluidShadeOverride) {
		getConfig().setFluidShadeOverride(fluidShadeOverride);
	}

	public static void setGlobalShadeOverride(ShadeOverride globalShadeOverride) {
		getConfig().setGlobalShadeOverride(globalShadeOverride);
	}

	public static void setReloadOnSave(boolean reloadOnSave) {
		getConfig().setReloadOnSave(reloadOnSave);
	}

	@Override
	public void onInitializeClient() {
//		final Thread rendererCheckerThread = new Thread(() -> {
//			final Logger logger = LogManager.getLogger("noshades");
//
//			while (true) {
//				logger.info(() -> String.valueOf(MinecraftClient.getInstance().worldRenderer));
//
//				try {
//					Thread.sleep(1000L);
//				} catch (InterruptedException e) {
//					logger.warn(() -> e);
//				}
//			}
//		});
//
//		rendererCheckerThread.setDaemon(true);
//		rendererCheckerThread.setPriority(Thread.MIN_PRIORITY);
//		rendererCheckerThread.start();
	}
}
