/*
 * Test for Tweet class
 */
package com.sproutsocial.api;

import static io.dropwizard.testing.FixtureHelpers.*;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;
import org.joda.time.DateTime;

public class TweetTest {

    private final ObjectMapper MAPPER;

    public TweetTest() {
        this.MAPPER = Jackson.newObjectMapper();
    }

    @Test
    public void serializesToJSON() throws Exception {
        
        final DateTime createdAt = new DateTime(Long.parseLong("1498934085000"));
        final Tweet tweet = new Tweet(
                "2305278770", 
                "Just watching Guardians of the Galaxy.", 
                createdAt, 
                "http://abs.twimg.com/sticky/default_profile_images/default_profile_normal.png"
        );

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/tweet.json"), Tweet.class));

        assertThat(MAPPER.writeValueAsString(tweet)).isEqualTo(expected);
    }
}

