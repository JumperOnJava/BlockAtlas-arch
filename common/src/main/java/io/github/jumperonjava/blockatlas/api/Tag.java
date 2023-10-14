package io.github.jumperonjava.blockatlas.api;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import java.util.List;

public interface Tag {
    Text getDisplayName();
    void setServersFromTag(ListHandler<Server> handler);
}
