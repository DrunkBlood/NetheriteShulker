package drunkblood.nethershulker.event;

import drunkblood.nethershulker.NetheriteShulker;
import drunkblood.nethershulker.block.ModBlocks;
import drunkblood.nethershulker.block.NetheriteShulkerBlock;
import drunkblood.nethershulker.data.EnglishLanguageProvider;
import drunkblood.nethershulker.data.GeneratorProviders;
import drunkblood.nethershulker.data.GermanLanguageProvider;
import net.minecraft.core.dispenser.ShulkerBoxDispenseBehavior;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = NetheriteShulker.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvent {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();
        if (event.includeServer()) {
            generator.addProvider(new GeneratorProviders.ModLootTables(generator));
        }
        if (event.includeClient()) {
            generator.addProvider(new GeneratorProviders.ModBlockStates(generator, event.getExistingFileHelper()));
            generator.addProvider(new GeneratorProviders.ModBlockTags(generator, event.getExistingFileHelper()));
            generator.addProvider(new GeneratorProviders.ModItemModels(generator, event.getExistingFileHelper()));
            generator.addProvider(new EnglishLanguageProvider(generator, "en_us"));
            generator.addProvider(new GermanLanguageProvider(generator, "de_de"));
        }
    }

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event){
        event.enqueueWork(() -> {
            DispenserBlock.registerBehavior(ModBlocks.NETHERITE_SHULKER_DEFAULT.get().asItem(), new ShulkerBoxDispenseBehavior());

            for(DyeColor dyecolor : DyeColor.values()) {
                DispenserBlock.registerBehavior(NetheriteShulkerBlock.getBlockByColor(dyecolor).asItem(), new ShulkerBoxDispenseBehavior());
            }
        });
    }
}
