package io.github.jumperonjava.blockatlas;

import net.fabricmc.api.ClientModInitializer;

class BlockAtlasFabricInit implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        new BlockAtlasInit();
    }
}