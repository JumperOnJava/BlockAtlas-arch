package io.github.jumperonjava.blockatlas.gui.backport;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class ButtonWidgetBuilder {
    private Text message;
    private ButtonWidget.PressAction onPress;
    private int x;
    private int y;
    private int width = 150;
    private int height = 20;
    public ButtonWidgetBuilder(Text var1, ButtonWidget.PressAction var2) {
        this.message = var1;
        this.onPress = var2;
    }
    public ButtonWidgetBuilder position(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }
    public ButtonWidgetBuilder width(int width) {
        this.width = width;
        return this;
    }
    public ButtonWidgetBuilder size(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }
    public ButtonWidgetBuilder dimensions(int x, int y, int width, int height) {
        return this.position(x, y).size(width, height);
    }
    public ButtonWidget build() {
        ButtonWidget var1 = new ButtonWidget(this.x, this.y, this.width, this.height, this.message, this.onPress);
        return var1;
    }
}
