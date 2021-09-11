package com.github.startsmercury.noshades.util;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.BlockStateRaycastContext;
import net.minecraft.world.LightType;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.chunk.light.LightingProvider;
import net.minecraft.world.level.ColorResolver;

public class FilterBlockRenderView implements BlockRenderView {
	protected final BlockRenderView delegate;

	protected FilterBlockRenderView(final BlockRenderView delegate) {
		this.delegate = delegate;
	}

	@Override
	public int countVerticalSections() {
		return this.delegate.countVerticalSections();
	}

	@Override
	public int getBaseLightLevel(final BlockPos pos, final int ambientDarkness) {
		return this.delegate.getBaseLightLevel(pos, ambientDarkness);
	}

	@Override
	public BlockEntity getBlockEntity(final BlockPos paramBlockPos) {
		return this.delegate.getBlockEntity(paramBlockPos);
	}

	@Override
	public <T extends BlockEntity> Optional<T> getBlockEntity(final BlockPos pos, final BlockEntityType<T> type) {
		return this.delegate.getBlockEntity(pos, type);
	}

	@Override
	public BlockState getBlockState(final BlockPos paramBlockPos) {
		return this.delegate.getBlockState(paramBlockPos);
	}

	@Override
	public int getBottomSectionCoord() {
		return this.delegate.getBottomSectionCoord();
	}

	@Override
	public int getBottomY() {
		return this.delegate.getBottomY();
	}

	@Override
	public float getBrightness(final Direction paramDirection, final boolean paramBoolean) {
		return this.delegate.getBrightness(paramDirection, paramBoolean);
	}

	@Override
	public int getColor(final BlockPos paramBlockPos, final ColorResolver paramColorResolver) {
		return this.delegate.getColor(paramBlockPos, paramColorResolver);
	}

	@Override
	public double getDismountHeight(final BlockPos pos) {
		return this.delegate.getDismountHeight(pos);
	}

	@Override
	public double getDismountHeight(final VoxelShape blockCollisionShape,
			Supplier<VoxelShape> belowBlockCollisionShapeGetter) {
		return this.delegate.getDismountHeight(blockCollisionShape, belowBlockCollisionShapeGetter);
	}

	@Override
	public FluidState getFluidState(final BlockPos paramBlockPos) {
		return this.delegate.getFluidState(paramBlockPos);
	}

	@Override
	public int getHeight() {
		return this.delegate.getHeight();
	}

	@Override
	public LightingProvider getLightingProvider() {
		return this.delegate.getLightingProvider();
	}

	@Override
	public int getLightLevel(final LightType type, final BlockPos pos) {
		return this.delegate.getLightLevel(type, pos);
	}

	@Override
	public int getLuminance(final BlockPos pos) {
		return this.delegate.getLuminance(pos);
	}

	@Override
	public int getMaxLightLevel() {
		return this.delegate.getMaxLightLevel();
	}

	@Override
	public int getSectionIndex(final int y) {
		return this.delegate.getSectionIndex(y);
	}

	@Override
	public Stream<BlockState> getStatesInBox(final Box box) {
		return this.delegate.getStatesInBox(box);
	}

	@Override
	public int getTopSectionCoord() {
		return this.delegate.getTopSectionCoord();
	}

	@Override
	public int getTopY() {
		return this.delegate.getTopY();
	}

	@Override
	public boolean isOutOfHeightLimit(final BlockPos pos) {
		return this.delegate.isOutOfHeightLimit(pos);
	}

	@Override
	public boolean isOutOfHeightLimit(final int y) {
		return this.delegate.isOutOfHeightLimit(y);
	}

	@Override
	public boolean isSkyVisible(final BlockPos pos) {
		return this.delegate.isSkyVisible(pos);
	}

	@Override
	public BlockHitResult raycast(final BlockStateRaycastContext context) {
		return this.delegate.raycast(context);
	}

	@Override
	public BlockHitResult raycast(final RaycastContext context) {
		return this.delegate.raycast(context);
	}

	@Override
	public BlockHitResult raycastBlock(final Vec3d start, final Vec3d end, final BlockPos pos, final VoxelShape shape,
			final BlockState state) {
		return this.delegate.raycastBlock(start, end, pos, shape, state);
	}

	@Override
	public int sectionCoordToIndex(final int coord) {
		return this.delegate.sectionCoordToIndex(coord);
	}

	@Override
	public int sectionIndexToCoord(final int index) {
		return this.delegate.sectionIndexToCoord(index);
	}
}
