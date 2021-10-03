package com.github.startsmercury.noshades.util;

import org.jetbrains.annotations.ApiStatus.Internal;

import com.github.startsmercury.noshades.entrypoint.NoShadesClientMod;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockRenderView;

@Internal
public class NoShadesUtil {
	public static final float blockLightness(final BlockState state) {
		return NoShadesClientMod.getBakedConfig().getBlockLightness().getMappings()
				.getOrDefault(Registry.BLOCK.getId(state.getBlock()).toString(), defualtBlockLightness());
	}

	public static final BlockRenderView blockLightnessDelegate(final BlockRenderView world, final BlockState state) {
		return new LightnessBlockRenderView(world, blockLightness(state));
	}

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

	public static final float defualtBlockLightness() {
		return NoShadesClientMod.getBakedConfig().getBlockLightness().getLightness();
	}

	public static final float defualtFluidLightness() {
		return NoShadesClientMod.getBakedConfig().getFluidLightness().getLightness();
	}

	public static final float fluidLightness(final FluidState state) {
		return NoShadesClientMod.getBakedConfig().getFluidLightness().getMappings()
				.getOrDefault(Registry.FLUID.getId(state.getFluid()).toString(), defualtBlockLightness());
	}

	public static final BlockRenderView fluidLightnessDelegate(final BlockRenderView world, final FluidState state) {
		return new LightnessBlockRenderView(world, fluidLightness(state));
	}
}
