package com.github.startsmercury.noshades.functions;

public class LighteningBrightnessFunction implements BrightnessFunction {
	@Override
	public float getBrightness(final float brightness, final float lightness) {
		return brightness * (1.0F - lightness) + lightness;
	}
}
