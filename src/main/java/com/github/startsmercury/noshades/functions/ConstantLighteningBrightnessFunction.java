package com.github.startsmercury.noshades.functions;

public class ConstantLighteningBrightnessFunction extends LighteningBrightnessFunction {
	private final float darkness;

	private final float lightness;

	public ConstantLighteningBrightnessFunction(final float lightness) {
		this.lightness = lightness;
		this.darkness = 1.0F - lightness;
	}

	@Override
	public float getBrightness(final float brightness, final float lightness) {
		return brightness * this.darkness + this.lightness;
	}
}
