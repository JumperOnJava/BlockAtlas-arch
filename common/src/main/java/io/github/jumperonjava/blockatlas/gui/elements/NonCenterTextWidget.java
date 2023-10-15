package io.github.jumperonjava.blockatlas.gui.elements;

import io.github.jumperonjava.blockatlas.api.Server;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class NonCenterTextWidget implements Drawable {
    private final int x;
    private final int y;
    private final Text t;
    private final TextRenderer tr;
    public NonCenterTextWidget(int x, int y, Text text, TextRenderer textRenderer){
        this.x=x;
        this.y=y;
        this.t=text;
        this.tr=textRenderer;
    }
    @Override
    public void render(MatrixStack context, int mouseX, int mouseY, float delta) {
        DrawableHelper.drawTextWithShadow(context,tr,t,x,y,0xFFFFFFFF);
    }
}
