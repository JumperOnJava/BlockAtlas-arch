package io.github.jumperonjava.blockatlas.gui.elements;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class NonTexturedButton extends ButtonWidget {
    public NonTexturedButton(int x, int y, int width, int height, Text message, PressAction onPress) {
        super(x, y, width, height, message, onPress, null);
    }
    public TextureWidget plusIcon;
    @Override
    public void render(MatrixStack context, int mouseX, int mouseY, float delta) {
        return;
    }
}
