package io.github.jumperonjava.blockatlas.gui.elements;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.util.math.MatrixStack;
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
    public void render(MatrixStack context, int mouseX, int mouseY, float delta) {
        RenderSystem.setShaderTexture(0,texture.get());
        DrawableHelper.drawTexture(context,x,y,0,0,width,height,width,height);
    }
}
