package com.bjorkgren.nusen.model;

import java.util.List;

public interface WeatherdataListener {
    void onResult(int nowTemp, int laterTemp, Weather now, Weather later);
    void onError();
}
