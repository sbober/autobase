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

class AutobaseGrailsPlugin {
    def version = 0.1
    def dependsOn = [liquibase:"* > 1.7", hibernate: "* > 1.0"]
		def observe = ["hibernate", "liquibase"]
		def watchedResources = [ 
      "file:./grails-app/migration/*.groovy",
      "file:./grails-app/migration/*.xml",
      "file:./grails-app/migration/*/*.groovy"
    ]

    // TODO Fill in these fields
    def author = "Robert Fischer"
    def authorEmail = "autobase@smokejumperit.com"
    def title = "Automate your database work as much as possible"
    def description = '''\
This plugin marries the established Liquibase core with Grails development processes in order to to minimze the amount of database code you have to think about.

The approach to this plugin is to leave the database update mode ("hbm2ddl.auto" value) as "update", and to manage alterations to the database schema through checking in changesets to a folder.  The changesets are made up of Liquibase's many "refactorings": http://www.liquibase.org/manual/home#available_database_refactorings
'''

    // URL to the plugin's documentation
    def documentation = "http://github.com/RobertFischer/autobase/"
		//"http://grails.org/Autobase+Plugin"

		private static final def doMigrate = { ignored ->
			try {
				Autobase.migrate()
			} catch(Exception e) {
				GrailsUtil.sanitizeStackTrace(e)
				e.printStackTrace()
			}
		}

    def doWithSpring = {
        // TODO Implement runtime spring config (optional)
    }
   
    def doWithApplicationContext = doMigrate

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional)
    }
	                                      
    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    // Implements code that is executed when any artefact that this plugin is
    // watching is modified and reloaded. The event contains: event.source,
    // event.application, event.manager, event.ctx, and event.plugin.
    def onChange = doMigrate

		// Implements code that is executed when the project configuration changes.
   	// The event is the same as for 'onChange'.
    def onConfigChange = doMigrate 
}
