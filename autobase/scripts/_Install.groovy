//
// This script is executed by Grails after plugin was installed to project.
// This script is a Gant script so you can use all special variables provided
// by Gant (such as 'baseDir' which points on project base dir). You can
// use 'Ant' to access a global instance of AntBuilder
//
// For example you can create directory under project tree:
// Ant.mkdir(dir:"/Users/robert/dev/workspace/autobase/autobase/grails-app/jobs")
//

Ant.property(environment:"env")
grailsHome = Ant.antProject.properties."env.GRAILS_HOME"

def migDir = "${basedir}/migration"
def fileName = "${migDir}/DatabaseChangeLog.groovy" 

Ant.mkdir(dir:migDir)
Ant.sequential {
  copy(file:"${pluginBaseDir}/src/templates/artifacts/DatabaseChangeLog.groovy",
     tofile:fileName,
     overwrite:false
  )
  replace(file:fileName,
      token:"@APPNAME@", 
      value:org.codehaus.groovy.grails.commons.ApplicationHolder.application.metadata['app.name'])
}

