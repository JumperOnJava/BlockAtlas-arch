package io.github.jumperonjava.blockatlas.api;

public class EmptyHandler implements ListHandler {

    @Override
    public void addElement(Object server) {

    }

    @Override
    public void tryLoadMoreCallback(Runnable loadMoreCallback) {

    }

    @Override
    public void clearElements(boolean clearScroll) {

    }

    @Override
    public void sendError(Throwable error) {

    }
}
