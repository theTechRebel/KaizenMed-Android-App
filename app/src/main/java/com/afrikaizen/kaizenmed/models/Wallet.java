package com.afrikaizen.kaizenmed.models;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by Steve on 19/3/2016.
 */
public class Wallet extends RealmObject{
        @Required
        private String organisationName;
        private String ecocashWallet;
        private String telecashWallet;

        public Wallet(String organisationName, String ecocashWallet, String telecashWallet) {
            this.organisationName = organisationName;
            this.ecocashWallet = ecocashWallet;
            this.telecashWallet = telecashWallet;
        }

        public Wallet() {
        }

        public String getOrganisationName() {
            return organisationName;
        }

        public void setOrganisationName(String organisationName) {
            this.organisationName = organisationName;
        }

        public String getEcocashWallet() {
            return ecocashWallet;
        }

        public void setEcocashWallet(String ecocashWallet) {
            this.ecocashWallet = ecocashWallet;
        }

        public String getTelecashWallte() {
            return telecashWallet;
        }

        public void setTelecashWallte(String telecashWallte) {
            this.telecashWallet = telecashWallte;
        }
}
