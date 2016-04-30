package com.afrikaizen.capstone.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Steve on 19/3/2016.
 */
public class Wallet extends RealmObject{
        @Required
        private String organisationName;
        @PrimaryKey
        private String walletName;
        private String sim;
        private Double balance;

        public Wallet(String organisationName, String walletName, String sim) {
            this.organisationName = organisationName;
            this.walletName = walletName;
            this.sim = sim;
        }

        public Wallet() {
        }

        public String getOrganisationName() {
            return organisationName;
        }

        public void setOrganisationName(String organisationName) {
            this.organisationName = organisationName;
        }

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
