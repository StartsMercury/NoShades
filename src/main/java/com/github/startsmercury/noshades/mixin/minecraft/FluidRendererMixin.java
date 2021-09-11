package com.github.startsmercury.noshades.mixin.minecraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.github.startsmercury.noshades.util.Factory;
import com.github.startsmercury.noshades.util.FluidBlockRenderView;

import net.minecraft.client.render.block.FluidRenderer;
import net.minecraft.world.BlockRenderView;

@Mixin(FluidRenderer.class)
public abstract class FluidRendererMixin {
	private static final Factory.Filter FILTER = new Factory.WeakFilter();

	private FluidRendererMixin() {
	}

	@ModifyVariable(method = "render", at = @At("HEAD"), argsOnly = true)
	private BlockRenderView modifyWorldInRender(final BlockRenderView world) {
		return FILTER.of(world, FluidBlockRenderView::new);
	}
}
