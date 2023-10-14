package io.github.jumperonjava.blockatlas.gui.elements;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Supplier;

public class LazyUrlTexture implements Supplier<Identifier> {
    private static final Map<String, Identifier> downloaded = new HashMap<>();
    private static final Set<String> downloading = new HashSet<>();
    private static final Identifier NOT_DOWNLOADED = new Identifier("blockatlas","textures/pack.png");
    private final String iconUrl;
    private static ThreadPoolExecutor LAZY_DOWNLOAD_ASYNC = new ScheduledThreadPoolExecutor(4, (new ThreadFactoryBuilder()).setNameFormat("Icon download pool #%d").setDaemon(true).build());;
    public LazyUrlTexture(String iconUrl) {
        this.iconUrl = iconUrl;
        if(downloaded.containsKey(iconUrl))
            return;
        LAZY_DOWNLOAD_ASYNC.submit(()-> {
            try {
                var id = new Identifier("blockatlastemp", String.valueOf(iconUrl.hashCode()));

                if(downloaded.containsKey(iconUrl))
                    return;

                InputStream inputStream = new URL(this.iconUrl).openStream();

                NativeImage nativeImage = NativeImage.read(inputStream);
                inputStream.close();
                RenderSystem.recordRenderCall(() -> {
                    try {
                        var backedTestTexture = new NativeImageBackedTexture(nativeImage);
                        MinecraftClient.getInstance().getTextureManager().registerTexture(id, backedTestTexture);
                        downloaded.put(iconUrl, id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public Identifier get() {
        return downloaded.getOrDefault(iconUrl,NOT_DOWNLOADED);
    }
}
