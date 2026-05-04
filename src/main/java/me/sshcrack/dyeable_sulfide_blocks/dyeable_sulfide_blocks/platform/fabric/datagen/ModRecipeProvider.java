package me.sshcrack.dyeable_sulfur_blocks.dyeable_sulfur_blocks.platform.fabric.datagen;

//? fabric && != 1.19.2 {

import me.sshcrack.dyeable_sulfur_blocks.dyeable_sulfur_blocks.DyedSulfurBlocksMod;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;

//? if 1.21.1
//import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {

	private final CompletableFuture<HolderLookup.Provider> registriesFuture;

	public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
		this.registriesFuture = registriesFuture;
	}

	@Override
	protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
		return new IntRecipeProvider(provider, recipeOutput);
	}


	@Override
	public @NotNull String getName() {
		return DyedSulfurBlocksMod.MOD_ID + ":recipe_provider";
	}

	static class IntRecipeProvider extends RecipeProvider {

		protected IntRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
			super(provider, recipeOutput);
		}

		@Override
		public void buildRecipes() {
			final var itemLookup = registries.lookupOrThrow(Registries.ITEM);
			buildLavaChickenRecipe(ShapelessRecipeBuilder.shapeless(itemLookup, RecipeCategory.FOOD, Items.COOKED_CHICKEN))
					.save(output, "lava_chicken_recipe");
		}

		private ShapelessRecipeBuilder buildLavaChickenRecipe(ShapelessRecipeBuilder builder) {
			return builder.requires(Items.LAVA_BUCKET)
					.requires(Items.CHICKEN)
					.unlockedBy("has_lava_bucket", has(Items.LAVA_BUCKET))
					.unlockedBy("has_chicken", has(Items.CHICKEN));
		}
	}
}
//?}
