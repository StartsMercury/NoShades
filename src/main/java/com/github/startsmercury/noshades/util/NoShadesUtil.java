package com.github.startsmercury.noshades.util;

import org.jetbrains.annotations.ApiStatus.Internal;

import com.github.startsmercury.noshades.NoShadesClientMod;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockRenderView;

@Internal
public class NoShadesUtil {
	public static final boolean close(final Object obj) {
		if (obj instanceof final AutoCloseable closeable) {
			try {
				closeable.close();

				return true;
			} catch (final Exception e) {
				return false;
			}
		}

		return false;
	}

	public static final float blockLightness(final BlockState state) {
		return NoShadesClientMod.getConfig().getBlockShading().getEntries()
				.getOrDefault(Registry.BLOCK.getId(state.getBlock()).toString(), defualtBlockLightness());
	}

	public static final BlockRenderView blockLightnessDelegate(final BlockRenderView world, final BlockState state) {
		if (NoShadesClientMod.getConfig().getMain().isEnabled()) {
			return new LightnessBlockRenderView(world, blockLightness(state));
		} else {
			return world;
		}
	}

	public static final float defaultLightness(final boolean seperated, final float defaultLightness) {
		if (seperated) {
			return defaultLightness;
		} else {
			return NoShadesClientMod.getConfig().getMain().getDefaultLightness();
		}
	}

	public static final float defualtBlockLightness() {
		return defaultLightness(NoShadesClientMod.getConfig().getBlockShading().isDefaultLightnessSeperated(),
				NoShadesClientMod.getConfig().getBlockShading().getDefaultLightness());
	}

	public static final float defualtFluidLightness() {
		return defaultLightness(NoShadesClientMod.getConfig().getFluidShading().isDefaultLightnessSeperated(),
				NoShadesClientMod.getConfig().getFluidShading().getDefaultLightness());
	}

	public static final float fluidLightness(final FluidState state) {
		return NoShadesClientMod.getConfig().getFluidShading().getEntries()
				.getOrDefault(Registry.FLUID.getId(state.getFluid()).toString(), defualtFluidLightness());
	}

	public static final BlockRenderView fluidLightnessDelegate(final BlockRenderView world, final FluidState state) {
		if (NoShadesClientMod.getConfig().getMain().isEnabled()) {
			return new LightnessBlockRenderView(world, fluidLightness(state));
		} else {
			return world;
		}
	}
}
