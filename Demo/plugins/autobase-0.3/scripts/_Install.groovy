
Ant.property(environment:"env")

def pbd = pluginBasedir
if(!pbd) { throw new IllegalStateException("No plugin base directory") }
def appName = grailsAppName
if(!appName) { throw new IllegalStateException("No application name provided") }

def migDir = "${basedir}/migrations"
def fileName = "${migDir}/changelog.groovy" 

Ant.mkdir(dir:migDir)
Ant.sequential {
  copy(verbose:"true", file:"${pbd}/src/templates/artifacts/DatabaseChangeLog.groovy", tofile:"${fileName}")
  replace(file:"${fileName}", token:'@APPNAME@', value:"${appName}")
}
