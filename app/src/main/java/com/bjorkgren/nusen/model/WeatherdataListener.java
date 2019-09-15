package com.bjorkgren.nusen.model;

import java.util.List;

public interface WeatherdataListener {
    void onComplete(String s);
    void onError();
}
