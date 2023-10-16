package io.github.jumperonjava.blockatlas.api;

import net.minecraft.text.MutableText;

public interface Tag {
    MutableText getDisplayName();
    void setServersFromTag(ListHandler<Server> handler);
}
