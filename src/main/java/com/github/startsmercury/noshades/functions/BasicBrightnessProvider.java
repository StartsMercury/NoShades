package com.github.startsmercury.noshades.functions;

public class BasicBrightnessProvider extends LinearBrightnessProvider {
	private final DarkeningBrightnessFunction darkeningBrightnessFunction = new DarkeningBrightnessFunction();

	@Override
	public BrightnessFunction getFunction(final float lightness) {
		return lightness < -0.0F ? this.darkeningBrightnessFunction
				: lightness > +0.0F ? new ConstantLighteningBrightnessFunction(lightness)
						: super.getFunction(lightness);
	}
}
