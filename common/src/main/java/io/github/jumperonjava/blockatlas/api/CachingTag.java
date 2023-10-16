package io.github.jumperonjava.blockatlas.api;

import net.minecraft.text.MutableText;

import java.util.LinkedList;
import java.util.List;

public class CachingTag implements Tag {
    private final Tag tag;
    private final ProxyServerHandler proxy = new ProxyServerHandler();
    public CachingTag(Tag tag){
        this.tag = tag;
        tag.setServersFromTag(proxy);
    }
    @Override
    public MutableText getDisplayName() {
        return tag.getDisplayName();
    }
    @Override
    public void setServersFromTag(ListHandler<Server> handler) {
        handler.clearElements(true);
        proxy.setServersFromTag(handler);
    }
    static class ProxyServerHandler implements ListHandler<Server> {
        private ListHandler<Server> realHandler = null;
        private final List<Server> queue = new LinkedList<>();
        private Runnable callback;

        private void setServersFromTag(ListHandler<Server> h){
            realHandler = h;
            clearElements(false);
            queue.forEach(realHandler::addElement);
            realHandler.tryLoadMoreCallback(callback);
        }

        @Override
        public void addElement(Server server) {
            this.queue.add(server);
            if(realHandler!=null)
                setServersFromTag(realHandler);
        }

        @Override
        public void tryLoadMoreCallback(Runnable loadMoreCallback) {
            if(realHandler == null)
            {
                this.callback = loadMoreCallback;
                return;
            }
            realHandler.tryLoadMoreCallback(loadMoreCallback);
        }

        @Override
        public void clearElements(boolean clearScroll) {
            if(realHandler == null){
                return;
            }
            realHandler.clearElements(clearScroll);
        }

        @Override
        public void sendError(Throwable error) {
            if(realHandler ==null)
                return;
            realHandler.sendError(error);
        }
    }
}
