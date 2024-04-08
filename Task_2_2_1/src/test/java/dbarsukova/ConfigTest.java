package dbarsukova;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import dbarsukova.pizzeria.Configuration;
import dbarsukova.pizzeria.Pizzeria;
import java.io.StringReader;
import java.io.StringWriter;
import org.junit.jupiter.api.Test;


/**
 * task-2-2-1 configuration test.
 */

public class ConfigTest {

    @Test
    void test1() {
        int[] time = {20, 25};
        int[] capacity = {5, 7, 10};
        Pizzeria pizzeria = new Pizzeria(new Configuration(10, 2, time, 3, capacity));
        assertNotNull(pizzeria);
    }

    @Test
    void test2() {
        int[] time = {20, 25};
        int[] capacity = {5, 7, 10};
        Pizzeria pizzeria = new Pizzeria(new Configuration(10, 2, time, 3, capacity));
        pizzeria.start();
        pizzeria.stop();
    }

    @Test
    void test3() {
        int queueCapacity = 10;
        int numRests = 2;
        int[] time = {20, 25};
        int numDeliverers = 3;
        int[] deliverersCapacity = {5, 7, 10};
        Configuration config = new Configuration(queueCapacity,
                numRests,
                time,
                numDeliverers,
                deliverersCapacity);
        assertEquals(queueCapacity, config.getQueueCapacity());
        assertEquals(numRests, config.getNumRests());
        assertArrayEquals(time, config.getTime());
        assertEquals(numDeliverers, config.getNumDeliverers());
        assertArrayEquals(deliverersCapacity, config.getDelivererCapacity());
    }

    @Test
    void test4() {
        int queueCapacity = 10;
        int numRests = 2;
        int[] time = {20, 25};
        int numDeliverers = 3;
        int[] deliverersCapacity = {5, 7, 10};
        Configuration config = new Configuration(queueCapacity,
                numRests,
                time,
                numDeliverers,
                deliverersCapacity);
        StringWriter writer = new StringWriter();
        config.serialize(writer);
        Configuration deserializedConfig = new Configuration();
        deserializedConfig.deserialize(new StringReader(writer.toString()));
        assertEquals(queueCapacity, deserializedConfig.getQueueCapacity());
        assertEquals(numRests, deserializedConfig.getNumRests());
        assertArrayEquals(time, deserializedConfig.getTime());
        assertEquals(numDeliverers, deserializedConfig.getNumDeliverers());
        assertArrayEquals(deliverersCapacity, deserializedConfig.getDelivererCapacity());
    }
}
