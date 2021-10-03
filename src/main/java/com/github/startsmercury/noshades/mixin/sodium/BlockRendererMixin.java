package com.github.startsmercury.noshades.mixin.sodium;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.startsmercury.noshades.util.NoShadesUtil;
import com.github.startsmercury.noshades.util.SodiumUtil;

import me.jellysquid.mods.sodium.client.model.light.LightPipeline;
import me.jellysquid.mods.sodium.client.model.light.data.LightDataAccess;
import me.jellysquid.mods.sodium.client.render.chunk.compile.buffers.ChunkModelBuilder;
import me.jellysquid.mods.sodium.client.render.pipeline.BlockRenderer;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;

@Mixin(BlockRenderer.class)
public abstract class BlockRendererMixin {
	private LightPipeline delegatedLighter;

	private BlockRendererMixin() {
	}

	@ModifyVariable(method = "renderModel", at = @At("STORE"), name = "lighter")
	private LightPipeline modifyLighterInRenderModel(final LightPipeline lighter, final BlockRenderView world,
			final BlockState state, final BlockPos pos, final BlockPos origin, final BakedModel model,
			final ChunkModelBuilder buffers, final boolean cull, final long seed) {
		final float lightness = NoShadesUtil.blockLightness(state);
		final LightDataAccess lightCache = SodiumUtil.getLightCache(lighter);
		final LightPipeline modifiedLighter = SodiumUtil.delegateLighter(lighter, lightCache, lightness, pos);

		this.delegatedLighter = modifiedLighter;

		return modifiedLighter;
	}

	@Inject(method = "renderModel", at = @At("RETURN"))
	private void onRenderModelReturn(final CallbackInfoReturnable<Boolean> callback) {
		NoShadesUtil.close(this.delegatedLighter);

		this.delegatedLighter = null;
	}
}
