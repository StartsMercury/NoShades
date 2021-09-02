package com.github.startsmercury.noshades.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.startsmercury.noshades.NoShadesMod;
import com.github.startsmercury.noshades.ShadeOverride;

import net.minecraft.client.render.model.BakedQuad;

@Mixin(BakedQuad.class)
public class BakedQuadMixin {
	@Inject(method = { "hasShade" }, at = @At("RETURN"), cancellable = true)
	private void hasShadeReturn(CallbackInfoReturnable<Boolean> callback) {
		callback.setReturnValue(
				ShadeOverride.compose(NoShadesMod.getGlobalShadeOverride(), NoShadesMod.getFluidShadeOverride())
						.reshade(callback.getReturnValueZ()));
	}
}
