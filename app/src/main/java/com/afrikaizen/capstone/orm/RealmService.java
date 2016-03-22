package com.afrikaizen.capstone.orm;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Steve on 19/3/2016.
 */
public class RealmService {
    private static RealmService REALM_INSTANCE;
    private Realm realm;

    public static RealmService getInstance(Application app){
        if(REALM_INSTANCE == null){
            REALM_INSTANCE = new RealmService(app);
        }
        return REALM_INSTANCE;
    }

    public RealmService(Application app){
        //create realm configuration
        RealmConfiguration config = new RealmConfiguration.Builder(app).build();
        Realm.setDefaultConfiguration(config);
        // Create a RealmConfiguration which is to locate Realm file in package's "files" directory.
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(app).build();
        // Get a Realm instance for this thread
        this.realm = Realm.getInstance(realmConfig);
    }

    public Realm getRealm(){
        return realm;
    }
}
