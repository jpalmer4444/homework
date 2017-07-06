/*
 * Account Model
 */
package com.sproutsocial.db;

import java.util.Objects;

public class Account {
    
    private String oauthToken;
    
    private String oauthSecret;
    
    private int twitterId;
    
    private int id;
    
    public Account() {
    }

    public Account(int id, String oauthToken, String oauthSecret, int twitterId) {
        this.oauthToken = oauthToken;
        this.oauthSecret = oauthSecret;
        this.twitterId = twitterId;
        this.id = id;
    }

    public String getOauthToken() {
        return oauthToken;
    }

    public void setOauthToken(String oauthToken) {
        this.oauthToken = oauthToken;
    }

    public String getOauthSecret() {
        return oauthSecret;
    }

    public void setOauthSecret(String oauthSecret) {
        this.oauthSecret = oauthSecret;
    }

    public int getTwitterId() {
        return twitterId;
    }

    public void setTwitterId(int twitterId) {
        this.twitterId = twitterId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.twitterId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Account other = (Account) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.oauthToken, other.oauthToken)) {
            return false;
        }
        if (!Objects.equals(this.oauthSecret, other.oauthSecret)) {
            return false;
        }
        if (!Objects.equals(this.twitterId, other.twitterId)) {
            return false;
        }
        return true;
    }
    
}
