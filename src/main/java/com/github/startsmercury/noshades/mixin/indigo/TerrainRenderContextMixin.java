package com.github.startsmercury.noshades.mixin.indigo;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.startsmercury.noshades.util.NoShadesUtil;

import net.fabricmc.fabric.impl.client.indigo.renderer.render.TerrainBlockRenderInfo;
import net.fabricmc.fabric.impl.client.indigo.renderer.render.TerrainRenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;

@Mixin(TerrainRenderContext.class)
public abstract class TerrainRenderContextMixin {
	@Final
	@Shadow
	private TerrainBlockRenderInfo blockInfo;

	private BlockRenderView blockView;

	@Inject(method = "tesselateBlock", at = @At("HEAD"))
	private final void onTesselateBlockHead(final BlockState blockState, final BlockPos blockPos,
			final BakedModel model, final MatrixStack matrixStack, final CallbackInfoReturnable<Boolean> callback) {
		this.blockView = this.blockInfo.blockView;
		this.blockInfo.blockView = NoShadesUtil.blockLightnessDelegate(this.blockView, blockState);
	}

	@Inject(method = "tesselateBlock", at = @At("RETURN"))
	private final void onTesselateBlockReturn(final CallbackInfoReturnable<Boolean> callabck) {
		this.blockInfo.blockView = this.blockView;
	}
}
