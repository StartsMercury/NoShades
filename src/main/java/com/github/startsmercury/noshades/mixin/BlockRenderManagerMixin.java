package com.github.startsmercury.noshades.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.github.startsmercury.noshades.util.FluidBlockRenderView;

import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.world.BlockRenderView;

@Mixin(BlockRenderManager.class)
public class BlockRenderManagerMixin {
	@ModifyVariable(method = "renderFluid", at = @At("HEAD"), argsOnly = true)
	private BlockRenderView modifyWorldInRenderFluid(BlockRenderView world) {
		return new FluidBlockRenderView(world);
	}
}
