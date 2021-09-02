package com.github.startsmercury.noshades.mixin.minecraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import com.github.startsmercury.noshades.NoShadesMod;
import com.github.startsmercury.noshades.ShadeOverride;

import net.minecraft.client.render.block.FluidRenderer;

@Mixin(FluidRenderer.class)
public class FluidRendererMixin {
	@ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/BlockRenderView;getBrightness(Lnet/minecraft/util/math/Direction;Z)F"), index = 1)
	private boolean renderInvokeGetBrightness(boolean shaded) {
		return ShadeOverride.compose(NoShadesMod.getGlobalShadeOverride(), NoShadesMod.getFluidShadeOverride())
				.reshade(shaded);
	}
}
