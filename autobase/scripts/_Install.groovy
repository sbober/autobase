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
//includeTargets << new File ( "${grailsHome}/scripts/Init.groovy" )
//event("StatusUpdate", "Installing Autobase")
Ant.mkdir(dir:"${grailsHome}/migration/changesets")
//event("StatusFinal", "Finished installing Autobase")
//event("PluginInstalled", "Autobase")
