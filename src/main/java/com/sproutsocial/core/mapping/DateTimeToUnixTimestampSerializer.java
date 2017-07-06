/*
 * Serializes Joda DateTime to unix timestamp.
 */
package com.sproutsocial.core.mapping;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import org.joda.time.DateTime;

public class DateTimeToUnixTimestampSerializer extends JsonSerializer<DateTime> {

        @Override
        public void serialize(DateTime value, JsonGenerator gen,
                SerializerProvider arg2)
                throws IOException, JsonProcessingException {

            gen.writeNumber(value.getMillis() / (1000L));
        }
    }

