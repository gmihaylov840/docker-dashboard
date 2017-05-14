package com.mercator.docker.dashboard.domain;

/**
 * Created by Joro on 23.04.2017.
 */
public class HeaderElement {
    private int startIndex;
    private int endIndex;

    public HeaderElement(int startIndex, int endIndex) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }
}
