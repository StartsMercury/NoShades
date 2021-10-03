package com.github.startsmercury.noshades.mixin.minecraft;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.github.startsmercury.noshades.util.NoShadesUtil;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;

@Mixin(BlockRenderManager.class)
public abstract class BlockRendererManagerMixin {
	private BlockRendererManagerMixin() {
	}

	@ModifyVariable(method = "renderBlock", at = @At("HEAD"), argsOnly = true)
	private final BlockRenderView modifyWorldInRenderBlock(final BlockRenderView world, final BlockState state,
			final BlockPos pos, final BlockRenderView worldArg, final MatrixStack matrix,
			final VertexConsumer vertexConsumer, final boolean cull, final Random random) {
		return NoShadesUtil.blockLightnessDelegate(world, state);
	}

	@ModifyVariable(method = "renderFluid", at = @At("HEAD"), argsOnly = true)
	private final BlockRenderView modifyWorldInRenderFluid(final BlockRenderView world, final BlockPos pos,
			final BlockRenderView worldArg, final VertexConsumer vertexConsumer, final FluidState state) {
		return NoShadesUtil.fluidLightnessDelegate(world, state);
	}
}
