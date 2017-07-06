/*
 * Representation class for Tweet
 */
package com.sproutsocial.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sproutsocial.core.mapping.DateTimeToUnixTimestampSerializer;
import java.util.Objects;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

public class Tweet {

    @NotEmpty
    private String screenName;

    @NotEmpty
    private String text;

    private DateTime date;

    private String profileImage;
    
    public Tweet() {
    }

    public Tweet(String screenName, String text, DateTime date, String profileImage) {
        this.screenName = screenName;
        this.text = text;
        this.date = date;
        this.profileImage = profileImage;
    }

    @JsonProperty
    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    @JsonProperty
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @JsonProperty
    @JsonSerialize(using = DateTimeToUnixTimestampSerializer.class)
    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.screenName);
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
        final Tweet other = (Tweet) obj;
        if (!Objects.equals(this.screenName, other.screenName)) {
            return false;
        }
        if (!Objects.equals(this.text, other.text)) {
            return false;
        }
        if (!Objects.equals(this.profileImage, other.profileImage)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return true;
    }

}

