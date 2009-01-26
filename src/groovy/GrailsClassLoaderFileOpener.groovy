import liquibase.*;
import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

public class GrailsClassLoaderFileOpener extends ClassLoaderFileOpener implements FileOpener {

    public InputStream getResourceAsStream(String file) throws IOException {
				return super.getResourceAsStream('grails-app/migrations/' + file)
    }

    public Enumeration<URL> getResources(String packageName) throws IOException {
				return super.getResources('grails-app/migrations/' + packageName)
    }

}
