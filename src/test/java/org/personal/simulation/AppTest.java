package org.personal.simulation;

import com.google.common.collect.Maps;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author taotaotu
 * Oct 6, 2019
 */
public class AppTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void test() {
        logger.info("log system.");
        Map<String, String> map = Maps.newHashMap();
        map.put("field1", "value1");
        map.put("field2", "value2");
        map.put("field3", "value3");

        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue().equals("value2")) {
                map.remove(entry.getKey());
            }
        }

        logger.info("map: {}", map);
    }
}
