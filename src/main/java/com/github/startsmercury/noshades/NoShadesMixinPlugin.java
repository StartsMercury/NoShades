package com.github.startsmercury.noshades;

import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import net.minecraft.client.render.block.FluidRenderer;

public class NoShadesMixinPlugin implements IMixinConfigPlugin {
	private String fluidRendererName;

	public NoShadesMixinPlugin() {
		try {
			fluidRendererName = Class.forName("me.jellysquid.mods.sodium.client.render.pipeline.FluidRenderer")
					.getName();
		} catch (ClassNotFoundException cnfe) {
			fluidRendererName = FluidRenderer.class.getName();
		}
	}

	@Override
	public void acceptTargets(Set<String> arg0, Set<String> arg1) {
	}

	@Override
	public List<String> getMixins() {
		return null;
	}

	@Override
	public String getRefMapperConfig() {
		return null;
	}

	@Override
	public void onLoad(String arg0) {
	}

	@Override
	public void postApply(String arg0, ClassNode arg1, String arg2, IMixinInfo arg3) {
	}

	@Override
	public void preApply(String arg0, ClassNode arg1, String arg2, IMixinInfo arg3) {
	}

	@Override
	public boolean shouldApplyMixin(String arg0, String arg1) {
		return fluidRendererName.equals(arg0) || !arg0.endsWith("FluidRenderer");
	}
}
