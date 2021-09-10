package com.github.startsmercury.noshades.util;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;

public class MinecraftBlockRenderView extends FilterBlockRenderView {
	public MinecraftBlockRenderView(BlockRenderView delegate) {
		super(delegate);
	}

	@Override
	public float getBrightness(Direction direction, boolean shaded) {
		boolean isDarkened = super.delegate instanceof ClientWorld client && client.getSkyProperties().isDarkened();

		if (!shaded) {
			return isDarkened ? 0.9F : 1.0F;
		}

		return switch (direction) {
		case DOWN -> isDarkened ? 0.9F : 0.5F;
		case UP -> isDarkened ? 0.9F : 1.0F;
		case NORTH, SOUTH -> 0.8F;
		case WEST, EAST -> 0.6F;
		};
	}
}
