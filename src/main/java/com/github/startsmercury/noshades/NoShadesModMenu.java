package com.github.startsmercury.noshades;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.github.startsmercury.noshades.NoShadesConfig.Shading;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.BooleanListEntry;
import me.shedaniel.clothconfig2.gui.entries.FloatListEntry;
import me.shedaniel.clothconfig2.gui.entries.SubCategoryListEntry;
import me.shedaniel.clothconfig2.impl.builders.BooleanToggleBuilder;
import me.shedaniel.clothconfig2.impl.builders.FloatFieldBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class NoShadesModMenu implements ModMenuApi {
	private boolean reloadConfig;

	private final FloatListEntry createDefaultLightnessEntry(final ConfigEntryBuilder entryBuilder, final Text title,
			final float value, final Consumer<Float> saveConsumer) {
		return createLightnessEntry(entryBuilder, title, value, () -> 0.0F, saveConsumer);
	}

	private final FloatListEntry createLightnessEntry(final ConfigEntryBuilder entryBuilder, final Text title,
			final float value, final Supplier<Float> defaultValue, final Consumer<Float> saveConsumer) {
		final FloatFieldBuilder lightnessBuilder = entryBuilder.startFloatField(title, value);

		lightnessBuilder.setDefaultValue(defaultValue);
		lightnessBuilder.setSaveConsumer(saveConsumer);
		lightnessBuilder.setTooltip(new TranslatableText("config.noshades.lightness.tooltip0"),
				new TranslatableText("config.noshades.lightness.tooltip1"),
				new TranslatableText("config.noshades.lightness.tooltip2"));

		return lightnessBuilder.build();
	}

	private final void createMainCategory(final ConfigBuilder builder, final ConfigEntryBuilder entryBuilder) {
		final ConfigCategory mainCategory = builder
				.getOrCreateCategory(new TranslatableText("config.noshades.main.title"));

		mainCategory.addEntry(createMainEnabledEntry(entryBuilder));
		mainCategory.addEntry(createMainReloadConfigEntry(entryBuilder));
		mainCategory.addEntry(createMainReloadOnSaveEntry(entryBuilder));
		mainCategory.addEntry(createMainDefaultLightnessEntry(entryBuilder));
		mainCategory
				.setDescription(new StringVisitable[] { new TranslatableText("config.noshades.main.description0") });
	}

	private final FloatListEntry createMainDefaultLightnessEntry(final ConfigEntryBuilder entryBuilder) {
		return createDefaultLightnessEntry(entryBuilder,
				new TranslatableText("config.noshades.main.default_lightness.title"),
				NoShadesClientMod.getConfig().getMain().getDefaultLightness(),
				NoShadesClientMod.getConfig().getMain()::setDefaultLightness);
	}

	private final BooleanListEntry createMainEnabledEntry(final ConfigEntryBuilder entryBuilder) {
		final BooleanToggleBuilder enabledBuilder = entryBuilder.startBooleanToggle(
				new TranslatableText("config.noshades.main.enabled.title"),
				NoShadesClientMod.getConfig().getMain().isEnabled());

		enabledBuilder.setDefaultValue(true);
		enabledBuilder.setSaveConsumer(NoShadesClientMod.getConfig().getMain()::setEnabled);
		enabledBuilder.setTooltipSupplier(enabled -> Optional
				.of(new Text[] { new TranslatableText(enabled ? "config.noshades.main.enabled.true.tooltip0"
						: "config.noshades.main.enabled.false.tooltip0") }));

		return enabledBuilder.build();
	}

	private final BooleanListEntry createMainReloadConfigEntry(final ConfigEntryBuilder entryBuilder) {
		final BooleanToggleBuilder reloadConfigBuilder = entryBuilder.startBooleanToggle(
				new TranslatableText("config.noshades.main.reload_config.title"), this.reloadConfig);

		reloadConfigBuilder.setDefaultValue(false);
		reloadConfigBuilder.setSaveConsumer(reloadConfig -> this.reloadConfig = reloadConfig);
		reloadConfigBuilder.setTooltipSupplier(reloadConfig -> Optional.ofNullable(
				reloadConfig ? new Text[] { new TranslatableText("config.noshades.main.reload_config.true.tooltip0") }
						: null));

		return reloadConfigBuilder.build();
	}

	private final BooleanListEntry createMainReloadOnSaveEntry(final ConfigEntryBuilder entryBuilder) {
		final BooleanToggleBuilder reloadOnSaveBuilder = entryBuilder.startBooleanToggle(
				new TranslatableText("config.noshades.main.reload_on_save.title"),
				NoShadesClientMod.getConfig().getMain().isReloadOnSave());

		reloadOnSaveBuilder.setDefaultValue(true);
		reloadOnSaveBuilder.setSaveConsumer(NoShadesClientMod.getConfig().getMain()::setReloadOnSave);
		reloadOnSaveBuilder.setTooltipSupplier(reloadOnSave -> Optional
				.of(new Text[] { new TranslatableText(reloadOnSave ? "config.noshades.main.reload_on_save.true.tooltip0"
						: "config.noshades.main.reload_on_save.false.tooltip0") }));

		return reloadOnSaveBuilder.build();
	}

	protected Screen createModConfigScreen(final Screen screen) {
		final ConfigBuilder builder = ConfigBuilder.create();
		final ConfigEntryBuilder entryBuilder = builder.entryBuilder();

		createMainCategory(builder, entryBuilder);
		createShadingCategory(builder, entryBuilder, NoShadesClientMod.getConfig().getBlockShading(),
				new TranslatableText("config.noshades.block_shading.title"));
		createShadingCategory(builder, entryBuilder, NoShadesClientMod.getConfig().getFluidShading(),
				new TranslatableText("config.noshades.fluid_shading.title"));

		builder.setSavingRunnable(this::save);
		builder.setTitle(new TranslatableText("config.noshades.title"));
		builder.setTransparentBackground(true);

		return builder.build();
	}

	private final void createShadingCategory(final ConfigBuilder builder, final ConfigEntryBuilder entryBuilder,
			final Shading shading, final Text title, final StringVisitable... description) {
		final ConfigCategory shadingCategory = builder.getOrCreateCategory(title);

		shadingCategory.addEntry(createShadingDefaultLightnessSeperatedEntry(entryBuilder, shading));
		shadingCategory.addEntry(createShadingDefaultLightnessEntry(entryBuilder, shading));
		shadingCategory.addEntry(createShadingEntriesEntry(entryBuilder, shading));
		shadingCategory.setDescription(description);
	}

	private final FloatListEntry createShadingDefaultLightnessEntry(final ConfigEntryBuilder entryBuilder,
			final Shading shading) {
		return createLightnessEntry(entryBuilder,
				new TranslatableText("config.noshades.shading.default_lightness.title"), shading.getDefaultLightness(),
				() -> 0.0F, shading::setDefaultLightness);
	}

	private final BooleanListEntry createShadingDefaultLightnessSeperatedEntry(final ConfigEntryBuilder entryBuilder,
			final Shading shading) {
		final BooleanToggleBuilder defaultLightnessSeperatedBuilder = entryBuilder.startBooleanToggle(
				new TranslatableText("config.noshades.shading.default_lightness_seperated.title"),
				shading.isDefaultLightnessSeperated());

		defaultLightnessSeperatedBuilder.setDefaultValue(false);
		defaultLightnessSeperatedBuilder.setSaveConsumer(shading::setDefaultLightnessSeperated);

		return defaultLightnessSeperatedBuilder.build();
	}

	private final SubCategoryListEntry createShadingEntriesEntry(final ConfigEntryBuilder entryBuilder,
			final Shading shading) {
		final SubCategoryBuilder shadingEntriesBuilder = entryBuilder
				.startSubCategory(new TranslatableText("config.noshades.shading.entries.title"));

		shadingEntriesBuilder.add(entryBuilder
				.startTextDescription(new TranslatableText("config.noshades.shading.entries.description0")).build());
		shading.getEntries().forEach((identifier, lightness) -> {
			shadingEntriesBuilder.add(createLightnessEntry(entryBuilder, Text.of(identifier), lightness,
					shading::getDefaultLightness, lightness2 -> shading.getEntries().put(identifier, lightness2)));
		});

		return shadingEntriesBuilder.build();
	}

	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return this::createModConfigScreen;
	}

	private final void save() {
		if (this.reloadConfig) {
			this.reloadConfig = false;

			NoShadesClientMod.setConfig(NoShadesConfig.load());
		} else {
			NoShadesClientMod.getConfig().save();
		}

		if (NoShadesClientMod.getConfig().getMain().isReloadOnSave()) {
			MinecraftClient.getInstance().worldRenderer.reload();
		}
	}
}
