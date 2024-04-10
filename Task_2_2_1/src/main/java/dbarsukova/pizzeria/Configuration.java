package dbarsukova.pizzeria;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.io.Writer;


/**
 * Configuration class represents configuration settings for pizzeria,
 * including queue capacity, number of restaurants,cooking times for
 * each restaurant, number of deliverers, and delivery capacities.
 */

public class Configuration {
    private int queueCapacity;
    private int numRests;
    private int[] time;
    private int numDeliverers;
    private int[] delivererCapacity;
    private transient final Gson gson;

    /**
     * constructs new Configuration object with default Gson settings
     * for serialization/deserialization.
     */
    public Configuration() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        this.gson = builder.create();
    }

    /**
     * constructs new Configuration object with provided configuration settings.
     *
     * @param queueCapacity     capacity of order queue.
     * @param numRests          number of restaurants.
     * @param time              array of cooking times for each restaurant.
     * @param numDeliverers     number of deliverers.
     * @param delivererCapacity array of delivery capacities for each deliverer.
     */
    public Configuration(int queueCapacity, int numRests, int[] time, int numDeliverers, int[] delivererCapacity) {
        this();
        this.queueCapacity = queueCapacity;
        this.numRests = numRests;
        this.time = time;
        this.numDeliverers = numDeliverers;
        this.delivererCapacity = delivererCapacity;
    }

    /**
     * returns queue capacity.
     *
     * @return queue capacity.
     */
    public int getQueueCapacity() {
        return this.queueCapacity;
    }

    /**
     * returns number of restaurants.
     *
     * @return number of restaurants.
     */
    public int getNumRests() {
        return this.numRests;
    }

    /**
     * returns array of cooking times for each restaurant.
     *
     * @return array of cooking times for each restaurant.
     */
    public int[] getTime() {
        return this.time;
    }

    /**
     * returns number of deliverers.
     *
     * @return number of deliverers.
     */
    public int getNumDeliverers() {
        return this.numDeliverers;
    }

    /**
     * returns array of delivery capacities for each deliverer.
     *
     * @return array of delivery capacities for each deliverer.
     */
    public int[] getDelivererCapacity() {
        return this.delivererCapacity;
    }

    /**
     * serializes Configuration object to JSON and writes it to provided Writer.
     *
     * @param writer Writer to write serialized JSON to.
     */
    public void serialize(Writer writer) {
        gson.toJson(this, writer);
    }

    /**
     * deserializes JSON representation of Configuration from provided Reader
     * and updates current object.
     *
     * @param reader Reader containing JSON representation of Configuration.
     */
    public void deserialize(Reader reader) {
        Configuration config = gson.fromJson(reader, Configuration.class);
        this.queueCapacity = config.queueCapacity;
        this.numRests = config.numRests;
        this.time = config.time;
        this.numDeliverers = config.numDeliverers;
        this.delivererCapacity = config.delivererCapacity;
    }
}
