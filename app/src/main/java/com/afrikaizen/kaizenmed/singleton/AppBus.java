package com.afrikaizen.kaizenmed.singleton;

import com.squareup.otto.Bus;

/**
 * Created by Steve on 07/08/2015.
 */
public class AppBus {
    private static final Bus BUS = new Bus();

    public static Bus getInstance(){
        return BUS;
    }

    private AppBus() {
        // No instances.
    }
}
