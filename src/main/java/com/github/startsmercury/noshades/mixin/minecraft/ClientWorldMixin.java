package com.github.startsmercury.noshades.mixin.minecraft;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.startsmercury.noshades.NoShadesMod;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin extends World {
	protected ClientWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef,
			DimensionType dimensionType, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed) {
		super(properties, registryRef, dimensionType, profiler, isClient, debugWorld, seed);
	}

	@Inject(method = "getBrightness", at = @At("RETURN"), cancellable = true)
	private void onGetBrightnessReturn(CallbackInfoReturnable<Float> callback) {
		callback.setReturnValue(NoShadesMod.getMultipliedBlockShade(callback.getReturnValueF()));
	}
}
