package com.github.startsmercury.noshades;

import java.util.Optional;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.BooleanToggleBuilder;
import me.shedaniel.clothconfig2.impl.builders.EnumSelectorBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class NoShadesModMenu implements ModMenuApi {
	private static Screen genModConfigScreen(Screen parent) {
		ConfigBuilder builder = ConfigBuilder.create();

		ConfigEntryBuilder entryBuilder = builder.entryBuilder();

		ConfigCategory generalCategory = builder.getOrCreateCategory(Text.of("General"));
		EnumSelectorBuilder<ShadeOverride> blockShadeOverrideSelector = entryBuilder
				.startEnumSelector(Text.of("Block Shade"), ShadeOverride.class, NoShadesMod.getBlockShadeOverride());
		EnumSelectorBuilder<ShadeOverride> fluidShadeOverrideSelector = entryBuilder
				.startEnumSelector(Text.of("Fluid Shade"), ShadeOverride.class, NoShadesMod.getFluidShadeOverride());
		EnumSelectorBuilder<ShadeOverride> globalShadeOverrideSelector = entryBuilder
				.startEnumSelector(Text.of("Global Shade"), ShadeOverride.class, NoShadesMod.getGlobalShadeOverride());
		BooleanToggleBuilder reloadOnSaveToggle = entryBuilder.startBooleanToggle(Text.of("Reload on Save"),
				NoShadesMod.isReloadOnSave());

		blockShadeOverrideSelector.setSaveConsumer(NoShadesMod::setBlockShadeOverride);
		blockShadeOverrideSelector.setDefaultValue(ShadeOverride.DEFAULT);
		blockShadeOverrideSelector.setTooltipSupplier(blockShadeOverride -> {
			String string = null;

			switch (blockShadeOverride) {
			case OFF:
				string = "Blocks won't be shaded.";
				break;
			case DEFAULT:
				string = "Blocks remains unaffected.";
				break;
			case ON:
				string = "Blocks will be shaded.";
				break;
			}

			return Optional.of(new Text[] { Text.of(string) });
		});

		fluidShadeOverrideSelector.setSaveConsumer(NoShadesMod::setBlockShadeOverride);
		fluidShadeOverrideSelector.setDefaultValue(ShadeOverride.DEFAULT);
		fluidShadeOverrideSelector.setTooltipSupplier(blockShadeOverride -> {
			String string = null;

			if (!FabricLoader.getInstance().isModLoaded("sodium")) {
				switch (blockShadeOverride) {
				case OFF:
					string = "Fluids won't be shaded.";
					break;
				case DEFAULT:
					string = "Fluids remains unaffected.";
					break;
				case ON:
					string = "Fluids will be shaded.";
					break;
				}
			} else {
				string = "Sodium compatibility for fluids is not yet implemented.";
			}

			return Optional.of(new Text[] { Text.of(string) });
		});

		globalShadeOverrideSelector.setSaveConsumer(NoShadesMod::setGlobalShadeOverride);
		globalShadeOverrideSelector.setDefaultValue(ShadeOverride.DEFAULT);
		globalShadeOverrideSelector.setTooltipSupplier(globalShadeOverride -> {
			String string = null;

			switch (globalShadeOverride) {
			case OFF:
				string = "Objects won't be shaded.";
				break;
			case DEFAULT:
				string = "Objects are unaffected by this option.";
				break;
			case ON:
				string = "Objects will be shaded.";
				break;
			}

			return Optional.of(new Text[] { Text.of(string) });
		});

		reloadOnSaveToggle.setSaveConsumer(NoShadesMod::setReloadOnSave);
		reloadOnSaveToggle.setDefaultValue(true);
		reloadOnSaveToggle.setTooltipSupplier(reloadOnSave -> Optional
				.of(new Text[] { Text.of(reloadOnSave ? "Reloads rendered chunks to apply changes."
						: "To apply changes press F3 + A in-game.") }));

		generalCategory.addEntry(reloadOnSaveToggle.build());
		generalCategory.addEntry(globalShadeOverrideSelector.build());
		generalCategory.addEntry(blockShadeOverrideSelector.build());
		generalCategory.addEntry(fluidShadeOverrideSelector.build());

		builder.setParentScreen(parent);
		builder.setTitle(Text.of("NoShades Options"));
		builder.setSavingRunnable(NoShadesModMenu::save);
		builder.setTransparentBackground(true);
		return builder.build();
	}

	private static void save() {
		NoShadesMod.getConfig().save();

		if (NoShadesMod.isReloadOnSave()) {
			MinecraftClient.getInstance().worldRenderer.reload();
		}
	}

	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return NoShadesModMenu::genModConfigScreen;
	}
}
