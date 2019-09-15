package com.bjorkgren.nusen.model;

public interface PlacenameListener {
    void onFound(final String placename);
    void onError();
}
