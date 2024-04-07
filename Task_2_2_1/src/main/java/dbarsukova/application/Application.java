package dbarsukova.application;

import dbarsukova.pizzeria.Configuration;
import dbarsukova.pizzeria.Pizzeria;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;


/**
 * Application class represents application that runs pizzeria simulation.
 * it creates configuration file, initializes pizzeria with configuration,
 * and runs simulation for specified period of time.
 */

public class Application implements Runnable {
    private final Pizzeria pizzeria;
    private final Random rand;
    private final Path path;
    private final Charset CHARSET = StandardCharsets.UTF_8;

    /**
     * constructs Application object.
     * initializes random number generator, sets file path for configuration,
     * creates configuration file if it does not exist, and initializes pizzeria.
     */
    public Application() {
        this.rand = new Random();
        this.path = Paths.get("config.json");
        if (!Files.exists(this.path)) {
            createFile();
        }
        this.pizzeria = new Pizzeria(getConfig());
    }

    /**
     * creates configuration file with random values for baking time
     * and deliverers' capacity, and saves configuration to file.
     */
    public void createFile() {
        try {
            if (!Files.exists(this.path)) {
                Files.createFile(this.path);
            }
            Writer writer = Files.newBufferedWriter(this.path, this.CHARSET);
            int[] time = new int[10];
            int[] capacity = new int[10];
            Arrays.setAll(time, i -> this.rand.nextInt(1000) + 1);
            Arrays.setAll(capacity, i -> this.rand.nextInt(5) + 1);
            new Configuration(100, 10, time, 10, capacity).serialize(writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * retrieves configuration from configuration file.
     *
     * @return Configuration object with configuration values.
     */
    public Configuration getConfig() {
        Configuration config = new Configuration();
        try {
            Reader reader = Files.newBufferedReader(this.path, this.CHARSET);
            config.deserialize(reader);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }

    @Override
    public void run() {
        this.pizzeria.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
        this.pizzeria.stop();
    }
}
