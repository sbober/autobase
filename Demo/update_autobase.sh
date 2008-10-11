cd ..
./update_jar
cd autobase
rm *.zip
rm -rf ~/.grails
grails clean
grails package-plugin
cd ../Demo
rm -rvf ./grails-app/migrations/*
grails install-plugin ../autobase/autobase/grails-auto*.zip
rm -rf ~/.grails
grails clean
grails run-app
