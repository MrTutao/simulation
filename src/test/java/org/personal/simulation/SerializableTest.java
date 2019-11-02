package org.personal.simulation;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.personal.simulation.entity.RedisInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;

/**
 * @author taotaotu
 * Nov 1, 2019
 */
public class SerializableTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void test() throws Exception {
        RedisInfo info = new RedisInfo("127.0.0.1", 6379);
        ObjectMapper mapper = new ObjectMapper();
        StringWriter writer = new StringWriter();
        JsonGenerator generator = new JsonFactory().createGenerator(writer);
        mapper.writeValue(generator, info);

        generator.close();
        logger.info("redis: {}", writer.toString());

        RedisInfo deserializableInstance = mapper.readValue(writer.toString(), RedisInfo.class);

        logger.info("de serializable instance: {}", deserializableInstance);
    }
}
