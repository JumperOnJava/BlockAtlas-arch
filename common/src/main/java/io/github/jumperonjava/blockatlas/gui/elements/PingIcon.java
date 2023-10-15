package io.github.jumperonjava.blockatlas.gui.elements;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.jumperonjava.blockatlas.api.motd.PingWithCache;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PingIcon implements Drawable, Element {
    private final String address;
    static final Identifier INCOMPATIBLE_TEXTURE = new Identifier("server_list/incompatible");
    static final Identifier UNREACHABLE_TEXTURE = new Identifier("server_list/unreachable");
    static final Identifier PING_1_TEXTURE = new Identifier("server_list/ping_1");
    static final Identifier PING_2_TEXTURE = new Identifier("server_list/ping_2");
    static final Identifier PING_3_TEXTURE = new Identifier("server_list/ping_3");
    static final Identifier PING_4_TEXTURE = new Identifier("server_list/ping_4");
    static final Identifier PING_5_TEXTURE = new Identifier("server_list/ping_5");
    static final Identifier PINGING_1_TEXTURE = new Identifier("server_list/pinging_1");
    static final Identifier PINGING_2_TEXTURE = new Identifier("server_list/pinging_2");
    static final Identifier PINGING_3_TEXTURE = new Identifier("server_list/pinging_3");
    static final Identifier PINGING_4_TEXTURE = new Identifier("server_list/pinging_4");
    static final Identifier PINGING_5_TEXTURE = new Identifier("server_list/pinging_5");

    static final Text INCOMPATIBLE_TEXT = Text.translatable("multiplayer.status.incompatible");
    static final Text NO_CONNECTION_TEXT = Text.translatable("multiplayer.status.no_connection");
    static final Text PINGING_TEXT = Text.translatable("multiplayer.status.pinging");
    static final Identifier ICONS_TEXTURE = new Identifier("textures/gui/icons.png");
    private final int x,y;
    private final MinecraftClient client;

    public PingIcon(String server, int x,int y){
        this.address = server;
        this.x = x;
        this.y = y;
        this.client = MinecraftClient.getInstance();
    }
    @Override
    public void render(MatrixStack context, int mouseX, int mouseY, float delta) {
        var server = PingWithCache.getServer(this.address).orElse(null);

        Text text = server == null ? Text.literal("").formatted(Formatting.RED) : Text.translatable(server.playerCountLabel.getString()).setStyle(Style.EMPTY.withColor(0xFFAAAAAA));
        //Text text = bl && false ? server.version.copy().formatted(Formatting.RED) : server.playerCountLabel;
        int j = this.client.textRenderer.getWidth((StringVisitable) text);
        DrawableHelper.drawTextWithShadow(context, this.client.textRenderer, (Text) text, x - j - 10 - 2, y + 1, 8421504);
        int k = 0;
        int l = 0;
        List list2;
        Object text2;
        if (server != null && server.ping != -1L) {
            text2 = Text.translatable("multiplayer.status.ping", new Object[]{server.ping});
            list2 = server.playerListSummary;
            if (server.ping < 150L) {
                l = 0;
            } else if (server.ping < 300L) {
                l = 1;
            } else if (server.ping < 600L) {
                l = 2;
            } else if (server.ping < 1000L) {
                l = 3;
            } else {
                l = 4;
            }
        } else if (PingWithCache.getting.containsKey(address)) {
            k = 1;
            l = (int) (Util.getMeasuringTimeMs() / 100L) % 8;
            if (l > 4) {
                l = 8 - l;
            }

            text2 = PINGING_TEXT;
            list2 = Collections.emptyList();
        } else if (PingWithCache.failed.contains(address)){
            {
                l = 5;
                text2 = NO_CONNECTION_TEXT;
                list2 = Collections.emptyList();
            }
        }
        RenderSystem.setShaderTexture(0,ICONS_TEXTURE);
        DrawableHelper.drawTexture(context,x - 10, y, (float) (k * 10), (float) (176 + l * 8), 10, 8, 256, 256);
        //context.fill(x,y,x-10,y+8,0xFFFFFF00);
        if(isMouseOver(mouseX,mouseY)){
            var pingtext = Text.literal(String.valueOf(server == null ? "Failed to get ping" : server.ping+" ms"));
            //DrawableHelper.too(client.textRenderer,pingtext,mouseX-20-client.textRenderer.getWidth(pingtext.getString()),mouseY+12);
        }

    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= x-10 && mouseY >= y && mouseX < (double)x && mouseY < y+8;
    }


    public void setFocused(boolean focused) {
    }

    public boolean isFocused() {
        return false;
    }
}
