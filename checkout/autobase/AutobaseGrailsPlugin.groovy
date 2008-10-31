/*
  Copyright 2008 Robert Fischer

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/
import liquibase.LiquibaseDsl;
import liquibase.log.LogFactory;
import java.util.logging.Level as JavaLogLevel;
import grails.util.GrailsUtil;
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.apache.log4j.*;


class AutobaseGrailsPlugin {

    private static final Logger log = Logger.getLogger(AutobaseGrailsPlugin);

    def version = 0.2
    def dependsOn = [hibernate:GrailsUtil.grailsVersion]
		//def observe = ['hibernate']
    //def dependsOn = [:]
		def observe = []
		def watchedResources = []

    // TODO Fill in these fields
    def author = "Robert Fischer"
    def authorEmail = "robert.fischer@smokejumperit.com"
    def title = "Automate your database work as much as possible"
    def description = '''\
This plugin marries the established Liquibase core with Grails development processes in order to to minimze the amount of database code you have to think about.

The approach to this plugin is to leave the database update mode ("hbm2ddl.auto" value) as "update", and to manage alterations to the database schema through checking in changesets to a folder.  The changesets are made up of Liquibase's many "refactorings": http://www.liquibase.org/manual/home#available_database_refactorings
'''

    // URL to the plugin's documentation
    def documentation = "http://github.com/RobertFischer/autobase/wikis"
		//"http://grails.org/Autobase+Plugin"

		private static final Closure doMigrate = { 
			try {
        log.info("Beginning Autobase migration") 
				Autobase.migrate()
        log.info("Successfully completed Autobase migration") 
			} catch(Exception e) {
				GrailsUtil.deepSanitize(e)
				log.error("Error during Autobase migration", e)
			}
		}

    private static final Closure doAssignLogLevel = {
      try {
        def level = ConfigurationHolder.config.liquibase?.logLevel
        if(!level) {
          level = JavaLogLevel.INFO
          log.warn("Defaulting to Liquibase logging level of ${level}: please set 'liquibase.logLevel' to a java.util.logging.Level value")
        } else if(level instanceof String) {
          level = JavaLogLevel.parse(level)
        } else if(level instanceof JavaLogLevel) {
          // Just fall through
        } else {
          throw new RuntimeException("No idea how to assign logLevel of ${level} (${level?.class})")
        }
        LogFactory.logger.level = level
			} catch(Exception e) {
				GrailsUtil.deepSanitize(e)
				log.error("Error assigning Liquibase log level", e)
			}
    }

    def doWithSpring = { }
   
    def doWithApplicationContext = {}

    def doWithWebDescriptor = {}
	                                      
    // Do at the very last moment of app start-up
    def doWithDynamicMethods = {
      doAssignLogLevel()
      doMigrate()
    }

    // Implements code that is executed when any artefact that this plugin is
    // watching is modified and reloaded. The event contains: event.source,
    // event.application, event.manager, event.ctx, and event.plugin.
    def onChange = {}

		// Implements code that is executed when the project configuration changes.
   	// The event is the same as for 'onChange'.
    def onConfigChange = {}
}
