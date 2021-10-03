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
import me.jellysquid.mods.sodium.client.render.pipeline.FluidRenderer;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;

@Mixin(FluidRenderer.class)
public abstract class FluidRendererMixin {
	private LightPipeline delegatedLighter;

	private FluidRendererMixin() {
	}

	@ModifyVariable(method = "calculateQuadColors", at = @At("HEAD"), argsOnly = true)
	private float modifyBrightnessInCalculateQuadColors(final float brightness) {
		return 1.0F;
	}

	@ModifyVariable(method = "render", at = @At("STORE"), name = "lighter")
	private LightPipeline modifyLighterInRender(final LightPipeline lighter, final BlockRenderView world,
			final FluidState fluidState, final BlockPos pos, final BlockPos offset, final ChunkModelBuilder buffers) {
		final float lightness = NoShadesUtil.fluidLightness(fluidState);
		final LightDataAccess lightCache = SodiumUtil.getLightCache(lighter);
		final LightPipeline modifiedLighter = SodiumUtil.delegateLighter(lighter, lightCache, lightness, pos);

		this.delegatedLighter = modifiedLighter;

		return modifiedLighter;
	}

	@Inject(method = "render", at = @At("RETURN"))
	private void onRenderReturn(final CallbackInfoReturnable<Boolean> callback) {
		NoShadesUtil.close(this.delegatedLighter);

		this.delegatedLighter = null;
	}
}
