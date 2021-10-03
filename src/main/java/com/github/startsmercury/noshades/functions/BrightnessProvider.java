package com.github.startsmercury.noshades.functions;

public interface BrightnessProvider extends BrightnessFunction {
	@Override
	default float getBrightness(final float brightness, final float lightness) {
		return getFunction(lightness).getBrightness(brightness, lightness);
	}

	BrightnessFunction getFunction(final float lightness);
}
