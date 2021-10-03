package com.github.startsmercury.noshades.mixin.indigo;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.github.startsmercury.noshades.util.NoShadesUtil;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;

@Mixin(BlockRenderManager.class)
public abstract class BlockRendererManagerMixin {
	private BlockRendererManagerMixin() {
	}

	@ModifyVariable(method = "renderFluid", at = @At("HEAD"), name = "world", argsOnly = true)
	private final BlockRenderView modifyWorldInRenderFluid(final BlockRenderView $world, final BlockPos pos,
			final BlockRenderView world, final VertexConsumer vertexConsumer, final FluidState state) {
		return NoShadesUtil.fluidLightnessDelegate($world, state);
	}
}
