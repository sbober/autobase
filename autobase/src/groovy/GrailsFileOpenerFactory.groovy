import liquibase.ClassLoaderFileOpener
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.liquibase.grails.GrailsFileOpener

class GrailsFileOpenerFactory {

	static FileOpener getFileOpener() {
		if (ApplicationHolder.application.isWarDeployed()) {
    	return new ClassLoaderFileOpener()
    } else {
    	return new GrailsFileOpener()
    }		
	}

	
}

