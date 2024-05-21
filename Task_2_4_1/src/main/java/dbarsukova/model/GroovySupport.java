package dbarsukova.model;

import dbarsukova.App;
import groovy.lang.MetaClass;
import groovy.lang.Binding;
import groovy.lang.Closure;
import groovy.lang.GroovyObjectSupport;
import groovy.lang.GroovyShell;
import groovy.lang.MetaProperty;
import groovy.util.DelegatingScript;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.nio.file.Paths;
import java.util.Collection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.codehaus.groovy.control.CompilerConfiguration;


/**
 * configurable class for handling Groovy scripts.
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class GroovySupport extends GroovyObjectSupport {
    private URI scriptPath;

    /**
     * handles method calls not found in class.
     *
     * @param name name of method.
     * @param args arguments of method.
     * @return null
     * @throws IllegalArgumentException if no such field is found.
     */
    @SneakyThrows
    public Object methodMissing(String name, Object args) {
        MetaProperty metaProperty = getMetaClass().getMetaProperty(name);
        if (metaProperty != null) {
            var closure = (Closure<?>) ((Object[]) args)[0];
            Object propertyValue = initializePropertyValue(metaProperty, name);
            invokeClosureOnProperty(closure, propertyValue, name);
            return null;
        } else {
            throw new IllegalArgumentException("No such field: " + name);
        }
    }

    /**
     * includes and executes Groovy script from given relative path.
     *
     * @param relativePath relative path to script.
     */
    @SneakyThrows
    public void include(String relativePath) {
        URI resolvedUri = resolveScriptPath(relativePath);
        runFrom(resolvedUri);
        postProcess();
    }

    /**
     * runs Groovy script from given URI.
     *
     * @param uri URI of script.
     */
    @SneakyThrows
    public void runFrom(URI uri) {
        this.scriptPath = uri;
        GroovyShell shell = createGroovyShell();
        DelegatingScript script = parseScript(shell, uri);
        executeScript(script);
    }

    /**
     * performs post-processing after script execution.
     */
    @SneakyThrows
    public void postProcess() {
        MetaClass metaClass = getMetaClass();
        for (MetaProperty metaProperty : metaClass.getProperties()) {
            Object propertyValue = getProperty(metaProperty.getName());
            if (propertyValue instanceof Collection<?>) {
                Collection<?> collection = (Collection<?>) propertyValue;
                handleCollectionProperty(metaProperty, collection);
            }
        }
    }

    private Object initializePropertyValue(MetaProperty metaProperty, String name) throws ReflectiveOperationException {
        Object value = getProperty(name) == null
                ? metaProperty.getType().getConstructor().newInstance()
                : getProperty(name);
        if (value instanceof GroovySupport) {
            ((GroovySupport) value).scriptPath = scriptPath;
        }
        return value;
    }

    private void invokeClosureOnProperty(Closure<?> closure, Object propertyValue, String name) {
        closure.setDelegate(propertyValue);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
        setProperty(name, propertyValue);
    }

    private URI resolveScriptPath(String relativePath) {
        return Paths.get(scriptPath).getParent().resolve(relativePath).toUri();
    }

    private GroovyShell createGroovyShell() {
        CompilerConfiguration configuration = new CompilerConfiguration();
        configuration.setScriptBaseClass(DelegatingScript.class.getName());
        return new GroovyShell(App.class.getClassLoader(), new Binding(), configuration);
    }

    private DelegatingScript parseScript(GroovyShell shell, URI uri) throws IOException {
        return (DelegatingScript) shell.parse(uri);
    }

    private void executeScript(DelegatingScript script) {
        script.setDelegate(this);
        script.run();
    }

    private void handleCollectionProperty(MetaProperty metaProperty, Collection<?> collection) throws ReflectiveOperationException {
        ParameterizedType collectionType = (ParameterizedType) getClass()
                .getDeclaredField(metaProperty.getName()).getGenericType();
        Class<?> itemClass = (Class<?>) collectionType.getActualTypeArguments()[0];
        if (GroovySupport.class.isAssignableFrom(itemClass)) {
            Collection<Object> newCollection = createNewCollectionInstance(collection);
            populateNewCollection(collection, itemClass, newCollection);
            setProperty(metaProperty.getName(), newCollection);
        }
    }

    @SneakyThrows
    private Collection<Object> createNewCollectionInstance(Collection<?> originalCollection) {
        return (Collection<Object>) originalCollection.getClass().getConstructor().newInstance();
    }

    @SneakyThrows
    private void populateNewCollection(Collection<?> originalCollection, Class<?> itemClass, Collection<Object> newCollection) {
        for (Object item : originalCollection) {
            if (item instanceof Closure) {
                Object newItem = itemClass.getConstructor().newInstance();
                configureAndInvokeClosure((Closure<?>) item, newItem);
                newCollection.add(newItem);
            } else {
                newCollection.add(item);
            }
        }
    }

    @SneakyThrows
    private void configureAndInvokeClosure(Closure<?> closure, Object item) {
        if (item instanceof GroovySupport) {
            ((GroovySupport) item).setProperty("scriptPath", scriptPath);
            closure.setDelegate(item);
            closure.setResolveStrategy(Closure.DELEGATE_FIRST);
            closure.call();
            ((GroovySupport) item).postProcess();
        }
    }
}
