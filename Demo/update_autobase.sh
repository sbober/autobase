cd ..
./update_jar
cd autobase
rm *.zip
rm -rf ~/.grails
grails clean
grails package-plugin
cd ../Demo
rm -rvf ./migrations/*
rm -rvf ./plugins/*
grails install-plugin ../autobase/grails-auto*.zip
rm -rf ~/.grails
grails clean
grails create-migration testing
grails run-app
