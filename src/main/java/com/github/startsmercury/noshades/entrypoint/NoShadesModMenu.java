package com.github.startsmercury.noshades.entrypoint;

import java.util.ArrayList;
import java.util.Optional;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.BooleanListEntry;
import me.shedaniel.clothconfig2.gui.entries.FloatListEntry;
import me.shedaniel.clothconfig2.gui.entries.StringListListEntry;
import me.shedaniel.clothconfig2.gui.entries.SubCategoryListEntry;
import me.shedaniel.clothconfig2.impl.builders.BooleanToggleBuilder;
import me.shedaniel.clothconfig2.impl.builders.FloatFieldBuilder;
import me.shedaniel.clothconfig2.impl.builders.StringListBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class NoShadesModMenu implements ModMenuApi {
	private boolean reloadConfig;

	protected SubCategoryListEntry createMainBlockLightnessEntry(final ConfigEntryBuilder configEntryBuilder) {
		final SubCategoryBuilder blockLightnessEntryBuilder = configEntryBuilder
				.startSubCategory(new TranslatableText("config.noshades.main.block_lightness"));

		blockLightnessEntryBuilder.add(createMainBlockLightnessSeparateEntry(configEntryBuilder));
		blockLightnessEntryBuilder.add(createMainBlockLightnessLightnessEntry(configEntryBuilder));
		blockLightnessEntryBuilder.add(createMainBlockLightnessMappingsEntry(configEntryBuilder));

		return blockLightnessEntryBuilder.build();
	}

	protected FloatListEntry createMainBlockLightnessLightnessEntry(final ConfigEntryBuilder configEntryBuilder) {
		final FloatFieldBuilder lightnessEntryBuilder = configEntryBuilder.startFloatField(
				new TranslatableText("config.noshades.main.lightness"),
				NoShadesClientMod.getLocalConfig().getBlockLightness().getLightness());

		lightnessEntryBuilder.setDefaultValue(0.0F);
		lightnessEntryBuilder.setSaveConsumer(NoShadesClientMod.getLocalConfig().getBlockLightness()::setLightness);

		return lightnessEntryBuilder.build();
	}

	protected StringListListEntry createMainBlockLightnessMappingsEntry(final ConfigEntryBuilder configEntryBuilder) {
		final StringListBuilder mappingsEntryBuilder = configEntryBuilder.startStrList(
				new TranslatableText("config.noshades.main.block_lightness.mappings"),
				NoShadesClientMod.getLocalConfig().getBlockLightness().getMappings());

		mappingsEntryBuilder.setDefaultValue(new ArrayList<>());
		mappingsEntryBuilder.setExpanded(true);

		return mappingsEntryBuilder.build();
	}

	protected BooleanListEntry createMainBlockLightnessSeparateEntry(final ConfigEntryBuilder configEntryBuilder) {
		final BooleanToggleBuilder separateEntryBuilder = configEntryBuilder.startBooleanToggle(
				new TranslatableText("config.noshades.main.block_lightness.separate"),
				NoShadesClientMod.getLocalConfig().getBlockLightness().isSeparate());

		separateEntryBuilder.setDefaultValue(false);
		separateEntryBuilder.setSaveConsumer(NoShadesClientMod.getLocalConfig().getBlockLightness()::setSeparate);

		return separateEntryBuilder.build();
	}

	protected ConfigCategory createMainCategory(final ConfigBuilder configBuilder,
			final ConfigEntryBuilder configEntryBuilder) {
		final ConfigCategory mainCategory = configBuilder
				.getOrCreateCategory(new TranslatableText("config.noshades.main"));

		mainCategory.addEntry(createMainEnabledEntry(configEntryBuilder));
		mainCategory.addEntry(createMainReloadConfigEntry(configEntryBuilder));
		mainCategory.addEntry(createMainReloadChunksEntry(configEntryBuilder));
		mainCategory.addEntry(createMainLightnessEntry(configEntryBuilder));
		mainCategory.addEntry(createMainBlockLightnessEntry(configEntryBuilder));
		mainCategory.addEntry(createMainFluidLightnessEntry(configEntryBuilder));
		mainCategory.setDescription(new StringVisitable[] { new TranslatableText("config.noshades.main.description") });

		return mainCategory;
	}

	protected BooleanListEntry createMainEnabledEntry(final ConfigEntryBuilder configEntryBuilder) {
		final BooleanToggleBuilder enabledEntryBuilder = configEntryBuilder.startBooleanToggle(
				new TranslatableText("config.noshades.main.enabled"), NoShadesClientMod.getLocalConfig().isEnabled());

		enabledEntryBuilder.setDefaultValue(true);
		enabledEntryBuilder.setSaveConsumer(NoShadesClientMod.getLocalConfig()::setEnabled);
		enabledEntryBuilder.setTooltipSupplier(enabled -> {
			final Text[] tooltip;

			if (enabled) {
				tooltip = new Text[] { new TranslatableText("config.noshades.main.enabled.true.tooltip") };
			} else {
				tooltip = new Text[] { new TranslatableText("config.noshades.main.enabled.false.tooltip") };
			}

			return Optional.of(tooltip);
		});

		return enabledEntryBuilder.build();
	}

	protected SubCategoryListEntry createMainFluidLightnessEntry(final ConfigEntryBuilder configEntryBuilder) {
		final SubCategoryBuilder fluidLightnessEntryBuilder = configEntryBuilder
				.startSubCategory(new TranslatableText("config.noshades.main.fluid_lightness"));

		fluidLightnessEntryBuilder.add(createMainFluidLightnessSeparateEntry(configEntryBuilder));
		fluidLightnessEntryBuilder.add(createMainFluidLightnessLightnessEntry(configEntryBuilder));
		fluidLightnessEntryBuilder.add(createMainFluidLightnessMappingsEntry(configEntryBuilder));

		return fluidLightnessEntryBuilder.build();
	}

	protected FloatListEntry createMainFluidLightnessLightnessEntry(final ConfigEntryBuilder configEntryBuilder) {
		final FloatFieldBuilder lightnessEntryBuilder = configEntryBuilder.startFloatField(
				new TranslatableText("config.noshades.main.lightness"),
				NoShadesClientMod.getLocalConfig().getFluidLightness().getLightness());

		lightnessEntryBuilder.setDefaultValue(0.0F);
		lightnessEntryBuilder.setSaveConsumer(NoShadesClientMod.getLocalConfig().getFluidLightness()::setLightness);

		return lightnessEntryBuilder.build();
	}

	protected StringListListEntry createMainFluidLightnessMappingsEntry(final ConfigEntryBuilder configEntryBuilder) {
		final StringListBuilder mappingsEntryBuilder = configEntryBuilder.startStrList(
				new TranslatableText("config.noshades.main.block_lightness.mappings"),
				NoShadesClientMod.getLocalConfig().getFluidLightness().getMappings());

		mappingsEntryBuilder.setDefaultValue(new ArrayList<>());
		mappingsEntryBuilder.setExpanded(true);

		return mappingsEntryBuilder.build();
	}

	protected BooleanListEntry createMainFluidLightnessSeparateEntry(final ConfigEntryBuilder configEntryBuilder) {
		final BooleanToggleBuilder separateEntryBuilder = configEntryBuilder.startBooleanToggle(
				new TranslatableText("config.noshades.main.fluid_lightness.separate"),
				NoShadesClientMod.getLocalConfig().getFluidLightness().isSeparate());

		separateEntryBuilder.setDefaultValue(false);
		separateEntryBuilder.setSaveConsumer(NoShadesClientMod.getLocalConfig().getFluidLightness()::setSeparate);

		return separateEntryBuilder.build();
	}

	protected FloatListEntry createMainLightnessEntry(final ConfigEntryBuilder configEntryBuilder) {
		final FloatFieldBuilder lightnessEntryBuilder = configEntryBuilder.startFloatField(
				new TranslatableText("config.noshades.main.lightness"),
				NoShadesClientMod.getLocalConfig().getLightness());

		lightnessEntryBuilder.setDefaultValue(0.0F);
		lightnessEntryBuilder.setSaveConsumer(NoShadesClientMod.getLocalConfig()::setLightness);
		lightnessEntryBuilder.setTooltip(new TranslatableText("config.noshades.main.lightness.tooltip.0"),
				new TranslatableText("config.noshades.main.lightness.tooltip.1"),
				new TranslatableText("config.noshades.main.lightness.tooltip.2"),
				new TranslatableText("config.noshades.main.lightness.tooltip.3"),
				new TranslatableText("config.noshades.main.lightness.tooltip.4"));

		return lightnessEntryBuilder.build();
	}

	protected BooleanListEntry createMainReloadChunksEntry(final ConfigEntryBuilder configEntryBuilder) {
		final BooleanToggleBuilder enabledEntryBuilder = configEntryBuilder.startBooleanToggle(
				new TranslatableText("config.noshades.main.reload_chunks"),
				NoShadesClientMod.getLocalConfig().isReloadChunks());

		enabledEntryBuilder.setDefaultValue(true);
		enabledEntryBuilder.setSaveConsumer(NoShadesClientMod.getLocalConfig()::setReloadChunks);
		enabledEntryBuilder.setTooltipSupplier(reloadChunks -> {
			final Text[] tooltip;

			if (reloadChunks) {
				tooltip = new Text[] { new TranslatableText("config.noshades.main.reload_chunks.true.tooltip") };
			} else {
				tooltip = new Text[] { new TranslatableText("config.noshades.main.reload_chunks.false.tooltip") };
			}

			return Optional.of(tooltip);
		});

		return enabledEntryBuilder.build();
	}

	protected BooleanListEntry createMainReloadConfigEntry(final ConfigEntryBuilder configEntryBuilder) {
		final BooleanToggleBuilder reloadConfigEntryBuilder = configEntryBuilder
				.startBooleanToggle(new TranslatableText("config.noshades.main.reload_config"), isReloadConfig());

		reloadConfigEntryBuilder.setDefaultValue(false);
		reloadConfigEntryBuilder.setSaveConsumer(this::setReloadConfig);
		reloadConfigEntryBuilder.setTooltipSupplier(reloadConfig -> {
			final Text[] tooltip;

			if (reloadConfig) {
				tooltip = new Text[] { new TranslatableText("config.noshades.main.reload_config.true.tooltip") };
			} else {
				tooltip = null;
			}

			return Optional.ofNullable(tooltip);
		});

		return reloadConfigEntryBuilder.build();
	}

	protected Screen createModConfigScreen(final Screen parent) {
		final ConfigBuilder configBuilder = ConfigBuilder.create();
		final ConfigEntryBuilder configEntryBuilder = configBuilder.entryBuilder();

		createMainCategory(configBuilder, configEntryBuilder);

		configBuilder.setParentScreen(parent);
		configBuilder.setSavingRunnable(this::save);
		configBuilder.setTransparentBackground(true);

		return configBuilder.build();
	}

	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return this::createModConfigScreen;
	}

	private boolean isReloadConfig() {
		return this.reloadConfig;
	}

	@SuppressWarnings("resource")
	protected final void save() {
		if (isReloadConfig()) {
			setReloadConfig(false);
		} else {
			NoShadesClientMod.saveLocalConfig();
		}

		NoShadesClientMod.bakeLocalConfig();

		if (NoShadesClientMod.getLocalConfig().isReloadChunks()) {
			MinecraftClient.getInstance().worldRenderer.reload();
		}
	}

	private final void setReloadConfig(final boolean reloadConfig) {
		this.reloadConfig = reloadConfig;
	}
}
