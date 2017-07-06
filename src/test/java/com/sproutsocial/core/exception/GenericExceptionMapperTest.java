/*
 * Tests GenericExceptionMapper
 */
package com.sproutsocial.core.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GenericExceptionMapperTest {
    
    private final String message = "Message";
    private final Throwable ex = new NullPointerException(message);
    
    @Test
    public void testGenericExceptionMapperEntity(){
        ExceptionMapper mapper = new GenericExceptionMapper();
        Response toResponse = mapper.toResponse(ex);
        assertThat(toResponse.getEntity()).isInstanceOf(AppExceptionEntity.class);
    }
    
    @Test
    public void testGenericExceptionMapper500Status(){
        ExceptionMapper mapper = new GenericExceptionMapper();
        Response toResponse = mapper.toResponse(ex);
        assertThat(toResponse.getStatus()).isEqualTo(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
    }
    
}
