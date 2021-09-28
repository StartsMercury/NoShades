package com.github.startsmercury.noshades.util;

import me.jellysquid.mods.sodium.client.model.light.data.LightDataAccess;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;

public class FilterLightDataAccess extends LightDataAccess implements AutoCloseable {
	private LightDataAccess delegate;

	protected FilterLightDataAccess(final LightDataAccess delegate) {
		this.delegate = delegate;
		this.world = delegate.getWorld();
	}

	@Override
	public void close() throws Exception {
		this.delegate = null;
	}

	@Override
	public long get(final BlockPos pos) {
		return this.delegate.get(pos);
	}

	@Override
	public long get(final BlockPos pos, final Direction dir) {
		return this.delegate.get(pos, dir);
	}

	@Override
	public long get(final int x, final int y, final int z) {
		return this.delegate.get(x, y, z);
	}

	@Override
	public long get(final int x, final int y, final int z, final Direction dir) {
		return this.delegate.get(x, y, z, dir);
	}

	@Override
	public long get(final int x, final int y, final int z, final Direction d1, final Direction d2) {
		return this.delegate.get(x, y, z, d1, d2);
	}

	@Override
	public BlockRenderView getWorld() {
		return this.delegate.getWorld();
	}
}
