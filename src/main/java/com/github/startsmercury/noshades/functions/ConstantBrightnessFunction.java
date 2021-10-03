package com.github.startsmercury.noshades.functions;

public class ConstantBrightnessFunction implements BrightnessFunction {
	private final float constant;

	public ConstantBrightnessFunction(final float constant) {
		this.constant = constant;
	}

	@Override
	public float getBrightness(final float brightness, final float lightness) {
		return getConstant();
	}

	public float getConstant() {
		return this.constant;
	}
}
