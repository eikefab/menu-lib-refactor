package com.eikefab.menulib;

public class PageMenuHolder<T> extends MenuHolder {

    private final int maxIndex;
    private final int currentIndex;

    public PageMenuHolder(PageMenuContext<T> menuContext, int maxIndex, int currentIndex) {
        super(menuContext);

        this.maxIndex = maxIndex;
        this.currentIndex = currentIndex;
    }

    public int getMaxIndex() {
        return maxIndex;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    @Override
    public PageMenuContext<T> getMenuContext() {
        return (PageMenuContext<T>) super.getMenuContext();
    }

}
