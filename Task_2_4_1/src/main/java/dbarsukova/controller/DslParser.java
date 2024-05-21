package dbarsukova.controller;

import dbarsukova.App;
import dbarsukova.model.TestConfiguration;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.DelegatingScript;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Objects;
import org.codehaus.groovy.control.CompilerConfiguration;


/**
 * utility class for parsing DSL configurations using Groovy.
 */

public class DslParser {

    /**
     * parses DSL configuration file and returns corresponding TestConfig object.
     *
     * @param path path to DSL configuration file.
     * @return TestConfiguration object parsed from DSL configuration.
     * @throws URISyntaxException if URI of configuration file is invalid.
     */
    public static TestConfiguration parseConfig(String path) throws URISyntaxException {
        CompilerConfiguration compilerConfiguration = createCompilerConfiguration();
        GroovyShell groovyShell = createGroovyShell(compilerConfiguration);
        DelegatingScript script = parseScript(path, groovyShell);
        TestConfiguration configuration = createTestConfiguration(path);
        executeScript(script, configuration);
        configuration.postProcess();
        return configuration;
    }

    /**
     * creates compiler configuration for Groovy.
     *
     * @return CompilerConfiguration object.
     */
    private static CompilerConfiguration createCompilerConfiguration() {
        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        compilerConfiguration.setScriptBaseClass(DelegatingScript.class.getName());
        return compilerConfiguration;
    }

    /**
     * creates GroovyShell instance with given compiler configuration.
     *
     * @param compilerConfig CompilerConfiguration to be used by GroovyShell.
     * @return GroovyShell instance.
     */
    private static GroovyShell createGroovyShell(CompilerConfiguration compilerConfig) {
        return new GroovyShell(App.class.getClassLoader(), new Binding(), compilerConfig);
    }

    /**
     * parses DSL script using GroovyShell.
     *
     * @param path        path to DSL script.
     * @param groovyShell GroovyShell instance.
     * @return parsed DelegatingScript object.
     */
    private static DelegatingScript parseScript(String path, GroovyShell groovyShell) {
        try (InputStreamReader reader = new InputStreamReader(
                Objects.requireNonNull(App.class.getResourceAsStream(path)))) {
            return (DelegatingScript) groovyShell.parse(reader);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing script: " + path, e);
        }
    }

    /**
     * creates TestConfiguration object with given script path.
     *
     * @param path path to DSL script.
     * @return TestConfiguration object.
     * @throws URISyntaxException if URI of configuration file is invalid.
     */
    private static TestConfiguration createTestConfiguration(String path) throws URISyntaxException {
        TestConfiguration config = new TestConfiguration();
        config.setScriptPath(Objects.requireNonNull(App.class.getResource(path)).toURI());
        return config;
    }

    /**
     * executes parsed script with given TestConfiguration as delegate.
     *
     * @param script parsed DelegatingScript object.
     * @param config TestConfiguration object.
     */
    private static void executeScript(DelegatingScript script, TestConfiguration config) {
        script.setDelegate(config);
        script.run();
    }
}
