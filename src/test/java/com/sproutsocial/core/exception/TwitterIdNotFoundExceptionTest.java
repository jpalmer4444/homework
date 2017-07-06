/*
 * Tests that TwitterIdNotFoundException displays message.
 * 
 */
package com.sproutsocial.core.exception;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TwitterIdNotFoundExceptionTest {

    private final String message = "Exception";
    private final TwitterIdNotFoundException twitterIdNotFoundException = new TwitterIdNotFoundException(message);

    @Test
    public void testTwitterIdNotFoundExceptionWithMessage() {
        assertThat(twitterIdNotFoundException.getMessage().equals(message));
    }

}
