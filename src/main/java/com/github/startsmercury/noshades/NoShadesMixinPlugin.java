package com.github.startsmercury.noshades;

import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import net.fabricmc.loader.api.FabricLoader;

public class NoShadesMixinPlugin implements IMixinConfigPlugin {
	public static enum Compatibility {
		SODIUM, VANILLA;

		public static Compatibility of(final FabricLoader loader) {
			if (loader.isModLoaded(SODIUM.namespace)) {
				return SODIUM;
			} else {
				return VANILLA;
			}
		}

		public final String namespace;

		Compatibility() {
			this.namespace = toString().toLowerCase();
		}

		private boolean isSubpackage(final String x) {
			final int packageLength = x.indexOf('.');

			if (packageLength < 0) {
				return true;
			}

			return x.substring(0, packageLength).equals(namespace);
		}

		public String namespace() {
			return this.namespace;
		}
	}

	private static final String MIXIN_PACKAGE_NAME = "com.github.startsmercury.noshades.mixin";

	private final Compatibility compatibility;

	public NoShadesMixinPlugin() {
		this(Compatibility.of(FabricLoader.getInstance()));
	}

	public NoShadesMixinPlugin(final Compatibility compatibility) {
		this.compatibility = compatibility;
	}

	@Override
	public void acceptTargets(final Set<String> myTargets, final Set<String> otherTargets) {
	}

	@Override
	public List<String> getMixins() {
		return null;
	}

	@Override
	public String getRefMapperConfig() {
		return null;
	}

	@Override
	public void onLoad(final String mixinPackage) {
	}

	@Override
	public void postApply(final String targetClassName, final ClassNode targetClass, final String mixinClassName,
			final IMixinInfo mixinInfo) {
	}

	@Override
	public void preApply(final String targetClassName, final ClassNode targetClass, final String mixinClassName,
			final IMixinInfo mixinInfo) {
	}

	@Override
	public boolean shouldApplyMixin(final String targetClassName, String mixinClassName) {
		if (mixinClassName.startsWith(MIXIN_PACKAGE_NAME)) {
			mixinClassName = mixinClassName.substring(MIXIN_PACKAGE_NAME.length() + 1);
		}

		final boolean shouldApply = compatibility.isSubpackage(mixinClassName);

		NoShadesMod.LOGGER.info("mixinClassName: " + mixinClassName + ", shouldApply: " + shouldApply);

		return shouldApply;
	}
}
