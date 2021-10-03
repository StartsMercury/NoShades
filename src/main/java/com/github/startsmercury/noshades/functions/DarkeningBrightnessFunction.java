package com.github.startsmercury.noshades.functions;

public class DarkeningBrightnessFunction implements BrightnessFunction {
	@Override
	public float getBrightness(final float brightness, final float lightness) {
		return lightness * brightness + brightness;
	}
}
