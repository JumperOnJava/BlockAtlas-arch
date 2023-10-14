package io.github.jumperonjava.blockatlasarch.forge;

import io.github.jumperonjava.blockatlas.BlockAtlasInit;
import net.minecraftforge.fml.common.Mod;

@Mod("blockatlas")
public class BlockAtlasForgeInit {
    public BlockAtlasForgeInit(){
        new BlockAtlasInit();
    }
}