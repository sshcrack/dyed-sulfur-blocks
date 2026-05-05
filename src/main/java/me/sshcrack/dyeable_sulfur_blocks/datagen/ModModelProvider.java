package me.sshcrack.dyeable_sulfur_blocks.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.sshcrack.dyeable_sulfur_blocks.DyeableSulfurBlocks;
import me.sshcrack.dyeable_sulfur_blocks.ModBlocks;
import me.sshcrack.dyeable_sulfur_blocks.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Map;

public class ModModelProvider extends FabricModelProvider {

    private static final Identifier DYED_POTENT_SULFUR_MODEL =
            Identifier.fromNamespaceAndPath(DyeableSulfurBlocks.MOD_ID, "block/dyed_potent_sulfur");

    public ModModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generators) {
        // Emit the shared tinted cube model once.
        generators.modelOutput.accept(DYED_POTENT_SULFUR_MODEL, ModModelProvider::buildTintedCubeModel);

        // One blockstate per dyed block – all variants point to the shared model.
        for (Block block : ModBlocks.DYED_POTENT_SULFUR.values()) {
            generators.blockStateOutput.accept(
                    MultiVariantGenerator.dispatch(block, generators.plainVariant(DYED_POTENT_SULFUR_MODEL))
            );
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerators generators) {
        // Each item uses the tinted model with a constant color baked per-item so
        // the inventory icon matches the placed block without any extra runtime cost.
        for (Map.Entry<DyeColor, Item> entry : ModItems.DYED_POTENT_SULFUR_ITEMS.entrySet()) {
            int color = entry.getKey().getTextureDiffuseColor();
            generators.itemModelOutput.accept(
                    entry.getValue(),
                    ItemModelUtils.tintedModel(DYED_POTENT_SULFUR_MODEL, ItemModelUtils.constantTint(color)),
                    ClientItem.Properties.DEFAULT
            );
        }
    }

    /** Builds a cube_all-style JSON block model whose faces carry tintindex 0. */
    private static JsonElement buildTintedCubeModel() {
        JsonObject json = new JsonObject();
        json.addProperty("parent", "minecraft:block/block");

        JsonObject textures = new JsonObject();
        textures.addProperty("all", "minecraft:block/potent_sulfur");
        textures.addProperty("particle", "minecraft:block/potent_sulfur");
        json.add("textures", textures);

        JsonObject element = new JsonObject();
        element.add("from", intArray(0, 0, 0));
        element.add("to", intArray(16, 16, 16));

        JsonObject faces = new JsonObject();
        for (String dir : new String[]{"down", "up", "north", "south", "west", "east"}) {
            JsonObject face = new JsonObject();
            face.addProperty("texture", "#all");
            face.addProperty("cullface", dir);
            face.addProperty("tintindex", 0);
            faces.add(dir, face);
        }
        element.add("faces", faces);

        JsonArray elements = new JsonArray();
        elements.add(element);
        json.add("elements", elements);
        return json;
    }

    private static JsonArray intArray(int... values) {
        JsonArray arr = new JsonArray();
        for (int v : values) arr.add(v);
        return arr;
    }
}
