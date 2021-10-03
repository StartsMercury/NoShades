package com.github.startsmercury.noshades.mixin.plugin;

import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import com.github.startsmercury.noshades.entrypoint.NoShadesClientMod;

import net.fabricmc.loader.api.FabricLoader;

public class NoShadesMixinPlugin implements IMixinConfigPlugin {
	public static enum Compatibility {
		SODIUM("sodium"), INDIGO("fabric-renderer-indigo", "indigo"), VANILLA("minecraft");

		public static final Compatibility of(final FabricLoader loader) {
			for (final Compatibility compatibility : values()) {
				if (loader.isModLoaded(compatibility.modid)) {
					return compatibility;
				}
			}

			// this should'nt happen!
			throw new InternalError("No compatiblity found!");
		}

		public final String modid;

		private final String subpackage;

		Compatibility(final String modid) {
			this(modid, modid);
		}

		Compatibility(final String modid, final String namespace) {
			this.modid = modid;
			this.subpackage = "com.github.startsmercury.noshades.mixin." + namespace;
		}
	}

	private final Compatibility compatibility;

	public NoShadesMixinPlugin() {
		this(Compatibility.of(FabricLoader.getInstance()));
	}

	public NoShadesMixinPlugin(final Compatibility compatibility) {
		this.compatibility = compatibility;

		NoShadesClientMod.getLogger().info("Compatibility mode set to " + compatibility);
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
	public boolean shouldApplyMixin(final String targetClassName, final String mixinClassName) {
		if (mixinClassName.startsWith(this.compatibility.subpackage)) {
			NoShadesClientMod.getLogger()
					.info(() -> "Applying mixin '" + mixinClassName + "' to target '" + targetClassName + "'");

			return true;
		} else {
			return false;
		}
	}
}
