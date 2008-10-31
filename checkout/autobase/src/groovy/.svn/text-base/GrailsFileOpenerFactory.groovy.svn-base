import liquibase.*
import org.codehaus.groovy.grails.commons.ApplicationHolder

class GrailsFileOpenerFactory {

	static FileOpener getFileOpener() {
		if (ApplicationHolder.application.isWarDeployed()) {
    	return new GrailsClassLoaderFileOpener()
    } else {
    	return new GrailsFileSystemFileOpener()
    }		
	}

	
}

