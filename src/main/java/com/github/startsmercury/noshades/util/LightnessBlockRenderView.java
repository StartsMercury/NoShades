package com.github.startsmercury.noshades.util;

import org.jetbrains.annotations.ApiStatus.Internal;

import com.github.startsmercury.noshades.entrypoint.NoShadesClientMod;
import com.github.startsmercury.noshades.functions.BrightnessFunction;

import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;

@Internal
public class LightnessBlockRenderView extends FilterBlockRenderView {
	private final BrightnessFunction function;

	private final float lightness;

	public LightnessBlockRenderView(final BlockRenderView delegate, final float lightness) {
		super(delegate);

		this.function = NoShadesClientMod.getBakedConfig().getProvider().getFunction(lightness);
		this.lightness = lightness;
	}

	@Override
	public float getBrightness(final Direction direction, final boolean shaded) {
		return this.function.getBrightness(super.getBrightness(direction, shaded), this.lightness);
	}
}
