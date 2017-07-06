/*
 * Representation class for Update.
 */
package com.sproutsocial.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import org.hibernate.validator.constraints.NotEmpty;

public class Update {
    
    @NotEmpty
    private String twitterId;
    
    @NotEmpty
    private String content;

    public Update() {
    }

    public Update(String twitterId, String content) {
        this.twitterId = twitterId;
        this.content = content;
    }

    public String getTwitterId() {
        return twitterId;
    }

    @JsonProperty
    public void setTwitterId(String twitter_id) {
        this.twitterId = twitter_id;
    }

    
    public String getContent() {
        return content;
    }

    @JsonProperty
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.content);
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
        final Update other = (Update) obj;
        if (!Objects.equals(this.twitterId, other.twitterId)) {
            return false;
        }
        if (!Objects.equals(this.content, other.content)) {
            return false;
        }
        return true;
    }
    
}

