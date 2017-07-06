/*
 * Test for Update class
 */
package com.sproutsocial.api;

import static io.dropwizard.testing.FixtureHelpers.*;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;

public class UpdateTest {
    
    private final ObjectMapper MAPPER;

    public UpdateTest() {
        this.MAPPER = Jackson.newObjectMapper();
    }

    @Test
    public void deserializesFromJSON() throws Exception {
        final Update up = new Update();
        up.setTwitterId("2305278770");
        up.setContent("Just watching Guardians of the Galaxy.");
        assertThat(MAPPER.readValue(fixture("fixtures/Update.json"), Update.class))
                .isEqualTo(up);
    }
    
}