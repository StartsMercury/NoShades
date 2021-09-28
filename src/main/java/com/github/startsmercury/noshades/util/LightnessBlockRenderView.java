package com.github.startsmercury.noshades.util;

import org.jetbrains.annotations.ApiStatus.Internal;

import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;

@Internal
public class LightnessBlockRenderView extends FilterBlockRenderView {
	private final float lightness;

	public LightnessBlockRenderView(final BlockRenderView delegate, final float lightness) {
		super(delegate);

		this.lightness = lightness;
	}

	@Override
	public float getBrightness(final Direction paramDirection, final boolean paramBoolean) {
		final float brightness = super.getBrightness(paramDirection, paramBoolean);

		return switch (Float.floatToIntBits(this.lightness)) {
		case -1082130432 -> 0.0F;
		case -2147483648, 0 -> brightness;
		case 1065353216 -> 1.0F;
		default -> this.lightness < 0.0F ? this.lightness * brightness + brightness
				: 1.0F - (this.lightness - 1.0F) * (brightness - 1.0F);
		};
	}
}
