package io.github.jumperonjava.blockatlas.gui.elements;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * Scroll list widget for general use.
 */
public class ScrollListWidget extends AlwaysSelectedEntryListWidget<ScrollListWidget.ScrollListEntry> {
    public ScrollListWidget(MinecraftClient client, int width, int height, int x, int y, int itemHeight) {
        super(client,width+6,height,y,height,itemHeight);
        setLeftPos(x-6);
    }
    @Override
    public int getRowWidth() {
        return this.width;
    }
    public int addEntry(ScrollListEntry entry){
        entry.activationConsumer = this::setSelectedEntry;
        entry.isHoveredFunction = this::isMouseOver;
        entry.currentX+=6;
        return super.addEntry(entry);
    }
    @Override
    protected int getScrollbarPositionX() {
        return left;
    }
    protected ScrollListEntry getEntryAtPosition(double x, double y) {
        int i = this.getRowWidth() / 2;
        int j = this.left + this.width / 2;
        int k = j - i;
        int l = j + i;
        int m = MathHelper.floor(y - (double)this.top) - this.headerHeight + (int)this.getScrollAmount() - 4;
        int n = m / this.itemHeight;
        return (x < (double)this.getScrollbarPositionX() || x > (double)this.getScrollbarPositionX() +1) && x >= (double)k && x <= (double)l && n >= 0 && m >= 0 && n < this.getEntryCount() ? this.children().get(n) : null;
    }
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return super.mouseClicked(mouseX, mouseY, button);
    }
    private ScrollListEntry selectedEntry=new ScrollListEntry();
    public void setSelectedEntry(ScrollListEntry listEntry) {
        selectedEntry.setSelected(false);
        listEntry.setSelected(true);
        selectedEntry=listEntry;
    }

    @Override
    public void render(MatrixStack context, int mouseX, int mouseY, float delta) {
        beginScissor(left,top,left+width,height);
        super.render(context, mouseX, mouseY, delta);
        RenderSystem.disableScissor();
    }
    public void beginScissor(double x, double y, double endX, double endY)
    {
        double width = endX - x;
        double height = endY - y;
        width = Math.max(0, width);
        height = Math.max(0, height);
        float d = (float) client.getWindow().getScaleFactor();
        int ay = (int) ((client.getWindow().getScaledHeight() - (y + height)) * d);
        RenderSystem.enableScissor((int) (x * d), ay, (int) (width * d), (int) (height * d));
    }
    /**
     * Scroll list entry. Out of box does nothing but using addDrawableChild method you can add widgets for custom behaviour.
     */
    public static class ScrollListEntry extends AlwaysSelectedEntryListWidget.Entry<ScrollListEntry> {
        private final List<Drawable> drawables = Lists.newArrayList();
        private final List<Element> children = Lists.newArrayList();
        private boolean isSelected = false;
        private Consumer<ScrollListEntry> activationConsumer;
        private BiFunction<Integer,Integer,Boolean> isHoveredFunction;
        private List<Element> deactivate = Lists.newArrayList();

        @Override
        public Text getNarration() {
            return Text.empty();
        }
        int currentX, currentY;
        private void setSelected(boolean selected) {
            this.isSelected = selected;
            for (var d : deactivate) {
                if (d instanceof PressableWidget pw) {
                    pw.active = !isSelected;
                }
            }
        }
        @Override
        public void render(MatrixStack context,
                           int index,
                           int y, int x,
                           int entryWidth,
                           int entryHeight,
                           int mouseX, int mouseY,
                           boolean hovered,
                           float delta) {
            context.push();
            context.translate(x+6, y, 0);
            entryWidth-=6;
            entryHeight+=2;
            entryWidth-=4;
            if(isSelected){
                DrawableHelper.fill(context,-1,-1,entryWidth+1,entryHeight+1,0xFFAAAAAA);
                DrawableHelper.fill(context,0,0,entryWidth,entryHeight,0xFF000000);
            }
            for (var d : drawables) {
                if(!isHoveredFunction.apply(mouseX,mouseY)){
                    mouseX+=100000;
                    mouseY+=100000;
                }
                d.render(context, mouseX - x - 6, mouseY - y, delta);
                currentX = x + 6;
                currentY = y;
            }
            context.pop();
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (!isMouseOver(mouseX, mouseY))
                return false;
            for (var c : children) {
                c.mouseClicked((mouseX) - (currentX), mouseY - currentY, button);
            }
            return false;
        }

        @Override
        public boolean isMouseOver(double mouseX, double mouseY) {
            return super.isMouseOver((mouseX), mouseY) && isHoveredFunction.apply((int) mouseX, (int) mouseY);
        }

        /**
         * adds widget element to this entry.
         * @param drawableElement
         * @param deactivateOnSelect should be this element deactivated when selected. Works only when widget is instance of PressableWidget
         * @return
         * @param <T>
         */
        public <T extends Element & Drawable> T addDrawableChild(T drawableElement, boolean deactivateOnSelect) {
            this.drawables.add(drawableElement);
            this.children.add(drawableElement);
            if (deactivateOnSelect)
                this.deactivate.add(drawableElement);
            return drawableElement;
        }
        public <T extends Drawable> T addDrawable(T drawable){
            this.drawables.add(drawable);
            return drawable;
        }
        protected boolean selectable(){
            return true;
        }

        public void setMeActive() {
            if(!selectable())
                return;
            if (activationConsumer == null)
                return;
            activationConsumer.accept(this);
        }
    }
}