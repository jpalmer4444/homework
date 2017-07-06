/*
 * Tests AppExceptionEntity class
 */
package com.sproutsocial.core.exception;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AppExceptionEntityTest {
    
    private final String message = "My Message";
    private final int status = 500;
    private final AppExceptionEntity appExceptionEntity = new AppExceptionEntity(status, message);
    
    @Test
    public void appExceptionMeetsEqualsHashcodeRequirements(){
        AppExceptionEntity appExceptionEntityLocal = new AppExceptionEntity(status, message);
        int hashcode = appExceptionEntity.hashCode();
        int hashcodeLocal = appExceptionEntityLocal.hashCode();
        assertThat(appExceptionEntity.equals(appExceptionEntityLocal) && hashcode == hashcodeLocal);
    }
    
    @Test
    public void appExceptionAcceptsStatusAndMessage(){
        assertThat(status == appExceptionEntity.getStatus());
        assertThat(message.equals(appExceptionEntity.getMessage()));
    }
    
}
