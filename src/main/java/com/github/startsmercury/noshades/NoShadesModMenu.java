package com.github.startsmercury.noshades;

import java.util.Optional;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.BooleanListEntry;
import me.shedaniel.clothconfig2.gui.entries.IntegerSliderEntry;
import me.shedaniel.clothconfig2.gui.entries.SubCategoryListEntry;
import me.shedaniel.clothconfig2.impl.builders.BooleanToggleBuilder;
import me.shedaniel.clothconfig2.impl.builders.IntSliderBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class NoShadesModMenu implements ModMenuApi {
	private static boolean isReloadOnSave() {
		return NoShadesMod.getConfig().isReloadOnSave();
	}

	private static void save() {
		NoShadesMod.getConfig().save();

		if (NoShadesMod.getConfig().isReloadOnSave()) {
			NoShadesMod.getLogger().info(() -> "Reloading chunks...");
			MinecraftClient.getInstance().worldRenderer.reload();
		}
	}

	private static void setReloadOnSave(boolean reloadOnSave) {
		NoShadesMod.getConfig().setReloadOnSave(reloadOnSave);
	}

	private Screen genModConfigScreen(Screen parent) {
		ConfigBuilder builder = ConfigBuilder.create();
		ConfigEntryBuilder entryBuilder = builder.entryBuilder();

		builder.setParentScreen(parent);
		getMainCategory(builder, entryBuilder);
		builder.setTitle(new TranslatableText("config.noshades.title"));
		builder.setSavingRunnable(NoShadesModMenu::save);
		builder.setTransparentBackground(true);
		return builder.build();
	}

	private IntegerSliderEntry getBlockShadeMultiplier(ConfigEntryBuilder entryBuilder) {
		final IntSliderBuilder blockShadeMultiplierBuilder = entryBuilder.startIntSlider(
				new TranslatableText("entry.noshades.main.shade_multiplier.block.title"),
				NoShadesMod.getBlockShadeMultiplier(), -100, 100);

		blockShadeMultiplierBuilder.setSaveConsumer(NoShadesMod::setBlockShadeMultiplier);
		blockShadeMultiplierBuilder.setDefaultValue(0);
		blockShadeMultiplierBuilder
				.setTextGetter(blockShadeMultiplier -> new TranslatableText(switch (blockShadeMultiplier) {
				case -100 -> "entry.noshades.main.shade_multiplier.block.tooltip.bright";
				case 0 -> "entry.noshades.main.shade_multiplier.block.tooltip.default";
				case 100 -> "entry.noshades.main.shade_multiplier.block.tooltip.moody";
				default -> String.valueOf(blockShadeMultiplier);
				}));

		return blockShadeMultiplierBuilder.build();
	}

	private IntegerSliderEntry getFluidShadeMultiplier(ConfigEntryBuilder entryBuilder) {
		final IntSliderBuilder fluidShadeMultiplierBuilder = entryBuilder.startIntSlider(
				new TranslatableText("entry.noshades.main.shade_multiplier.fluid.title"),
				NoShadesMod.getFluidShadeMultiplier(), -100, 100);

		fluidShadeMultiplierBuilder.setSaveConsumer(NoShadesMod::setFluidShadeMultiplier);
		fluidShadeMultiplierBuilder.setDefaultValue(0);
		fluidShadeMultiplierBuilder
				.setTextGetter(fluidShadeMultiplier -> new TranslatableText(switch (fluidShadeMultiplier) {
				case -100 -> "entry.noshades.main.shade_multiplier.fluid.tooltip.bright";
				case 0 -> "entry.noshades.main.shade_multiplier.fluid.tooltip.default";
				case 100 -> "entry.noshades.main.shade_multiplier.fluid.tooltip.moody";
				default -> String.valueOf(fluidShadeMultiplier);
				}));

		return fluidShadeMultiplierBuilder.build();
	}

	private ConfigCategory getMainCategory(ConfigBuilder builder, ConfigEntryBuilder entryBuilder) {
		final ConfigCategory mainCategory = builder
				.getOrCreateCategory(new TranslatableText("category.noshades.main.title"));

		mainCategory.addEntry(getReloadOnSaveToggle(entryBuilder));
		mainCategory.addEntry(getShadeMultiplierSubCategory(entryBuilder));

		return mainCategory;
	}

	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return this::genModConfigScreen;
	}

	private BooleanListEntry getReloadOnSaveToggle(ConfigEntryBuilder entryBuilder) {
		final BooleanToggleBuilder reloadOnSaveTogglebuilder = entryBuilder
				.startBooleanToggle(new TranslatableText("entry.noshades.main.reload_on_save.title"), isReloadOnSave());

		reloadOnSaveTogglebuilder.setSaveConsumer(NoShadesModMenu::setReloadOnSave);
		reloadOnSaveTogglebuilder.setDefaultValue(true);
		reloadOnSaveTogglebuilder.setTooltipSupplier(reloadOnSave -> Optional
				.of(new Text[] { new TranslatableText(reloadOnSave ? "entry.noshades.main.reload_on_save.tooltip.yes"
						: "entry.noshades.main.reload_on_save.tooltip.no") }));

		return reloadOnSaveTogglebuilder.build();
	}

	private SubCategoryListEntry getShadeMultiplierSubCategory(ConfigEntryBuilder entryBuilder) {
		final SubCategoryBuilder shadeMultiplierCategory = entryBuilder
				.startSubCategory(new TranslatableText("category.noshades.main.shade_multiplier.title"));

		shadeMultiplierCategory.setTooltip(
				Optional.of(new Text[] { new TranslatableText("category.noshades.main.shade_multiplier.tooltip0"),
						new TranslatableText("category.noshades.main.shade_multiplier.tooltip1"),
						new TranslatableText("category.noshades.main.shade_multiplier.tooltip2"),
						new TranslatableText("category.noshades.main.shade_multiplier.tooltip3") }));
		shadeMultiplierCategory.setExpanded(true);
		shadeMultiplierCategory.add(getBlockShadeMultiplier(entryBuilder));
		shadeMultiplierCategory.add(getFluidShadeMultiplier(entryBuilder));

		return shadeMultiplierCategory.build();
	}
}
