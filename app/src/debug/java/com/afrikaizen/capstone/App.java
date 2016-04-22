package com.afrikaizen.capstone;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

/**
 * Created by Steve on 23/4/2016.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //initialize Stetho - Realm Debugger
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());
    }
}
