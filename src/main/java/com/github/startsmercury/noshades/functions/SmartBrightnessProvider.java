package com.github.startsmercury.noshades.functions;

public class SmartBrightnessProvider extends BasicBrightnessProvider {
	private final ConstantBrightnessFunction darkestBrightnessFunction = new ConstantBrightnessFunction(0.0F);

	private final ConstantBrightnessFunction lightestBrightnessFunction = new ConstantBrightnessFunction(1.0F);

	@Override
	public BrightnessFunction getFunction(final float lightness) {
		return lightness <= -1.0F ? this.darkestBrightnessFunction
				: lightness >= +1.0F ? this.lightestBrightnessFunction : super.getFunction(lightness);
	}
}
