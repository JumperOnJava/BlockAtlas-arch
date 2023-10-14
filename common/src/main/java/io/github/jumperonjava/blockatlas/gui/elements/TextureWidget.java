package io.github.jumperonjava.blockatlas.gui.elements;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class TextureWidget implements Drawable {
    private Supplier<Identifier> texture;
    private int x;
    private int y;
    private int width;
    private int height;

    public TextureWidget(Supplier<Identifier> texture, int x, int y, int width, int height){
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawTexture(texture.get(),x,y,0,0,width,height,width,height);
    }
}
