package com.github.startsmercury.noshades.functions;

public class LinearBrightnessProvider implements BrightnessProvider {
	private final LinearBrightnessFunction linearBrightnessFunction = new LinearBrightnessFunction();

	@Override
	public BrightnessFunction getFunction(final float lightness) {
		return this.linearBrightnessFunction;
	}
}
