rm stacktrace.log
cd $WKDIR/plugins/Autobase/trunk
rm *.zip
rm -rf ~/.grails
grails clean
grails package-plugin
cd $WKDIR/autobase/Demo
rm -rf ./migrations/*
rm -rf ./plugins/grails-autobase*
rm -rf ./plugins/autobase*
grails install-plugin $WKDIR/plugins/Autobase/trunk/grails-auto*.zip
rm -rf ~/.grails
grails clean
cp -vf TestingMigration.groovy ./migrations/TestingMigration.groovy
cp -vf changelog.groovy ./migrations/changelog.groovy
grails convert-xml-changelog common.tests.changelog.xml ./migrations/common.tests.changelog.groovy
grails run-war
