cd ..
./update_jar
cd autobase
rm *.zip
rm -rf ~/.grails
grails clean
grails package-plugin
cd ../Demo
rm -rvf ./migrations/*
rm -rvf ./plugins/grails-autobase*
rm -rvf ./plugins/autobase*
grails install-plugin ../autobase/grails-auto*.zip
rm -rf ~/.grails
grails clean
cp -vf TestingMigration.groovy ./migrations/TestingMigration.groovy
cp -vf changelog.groovy ./migrations/changelog.groovy
grails run-app
