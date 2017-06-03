package com.rolrence.bulletscreen.util;

import java.util.function.Function;

/**
 * Created by Rolrence on 2017/6/2.
 *
 */
public class StreamDelegate {
    private Function<String, Boolean> callback;

    public StreamDelegate(Function<String, Boolean> callback) {
        this.callback = callback;
    }

    public boolean invoke(String s) {
        return this.callback.apply(s);
    }
}