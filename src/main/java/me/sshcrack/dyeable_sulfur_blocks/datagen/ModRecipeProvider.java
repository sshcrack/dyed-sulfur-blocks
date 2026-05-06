package me.sshcrack.dyeable_sulfur_blocks.datagen;

import me.sshcrack.dyeable_sulfur_blocks.DyeableSulfurBlocks;
import me.sshcrack.dyeable_sulfur_blocks.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {

    public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        return new RecipeProvider(registries, output) {
            @Override
            public void buildRecipes() {
                for (Map.Entry<DyeColor, Block> entry : ModBlocks.DYED_POTENT_SULFUR.entrySet()) {
                    DyeColor color = entry.getKey();
                    Block result = entry.getValue();
                    ResourceKey<Recipe<?>> recipeKey = ResourceKey.create(
                            Registries.RECIPE,
                            Identifier.fromNamespaceAndPath(
                                    DyeableSulfurBlocks.MOD_ID,
                                    color.getName() + "_potent_sulfur"
                            )
                    );
                    ShapelessRecipeBuilder.shapeless(registries.lookup(Registries.ITEM).get(), RecipeCategory.BUILDING_BLOCKS, result)
                            .requires(Blocks.POTENT_SULFUR)
                            .requires(Items.DYE.pick(color))
                            .unlockedBy(getHasName(Blocks.POTENT_SULFUR), has(Blocks.POTENT_SULFUR))
                            .save(output, recipeKey);
                }
            }
        };
    }

    @Override
    public String getName() {
        return "Dyeable Sulfur Blocks Recipes";
    }
}
