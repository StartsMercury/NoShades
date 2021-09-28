package com.github.startsmercury.noshades.util;

import me.jellysquid.mods.sodium.client.model.light.data.LightDataAccess;
import net.minecraft.world.BlockRenderView;

public class LightnessLightDataAccess extends FilterLightDataAccess {
	private final float lightness;

	public LightnessLightDataAccess(final LightDataAccess delegate, final float lightness) {
		super(delegate);

		this.lightness = lightness;
	}

	@Override
	public BlockRenderView getWorld() {
		return new LightnessBlockRenderView(super.getWorld(), this.lightness);
	}
}
