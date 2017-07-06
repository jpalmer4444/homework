/*
 * Exception for no twitter_id found.
 */
package com.sproutsocial.core.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class TwitterIdNotFoundException extends Exception implements
                ExceptionMapper<TwitterIdNotFoundException> 
{
    private static final long serialVersionUID = 1L;
    
    public int getStatus(){
        return Response.Status.NOT_FOUND.getStatusCode();
    }
 
    public TwitterIdNotFoundException() {
        super("No Account found with twitter_id passed!");
    }
 
    public TwitterIdNotFoundException(String string) {
        super(string);
    }
 
    @Override
    public Response toResponse(TwitterIdNotFoundException e) 
    {
        AppExceptionEntity entity = new AppExceptionEntity(getStatus(), e.getMessage());
        return Response.status(Response.Status.NOT_FOUND)
                .entity(entity)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
    
    
}