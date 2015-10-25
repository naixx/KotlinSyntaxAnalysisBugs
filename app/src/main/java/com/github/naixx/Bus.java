package com.github.naixx;

import de.greenrobot.event.EventBus;

/**
 * Created by astra on 25.08.2015.
 */
public class Bus {

    public static void post(Object event) {
        EventBus.getDefault().post(event);
    }

    public static void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }
}
