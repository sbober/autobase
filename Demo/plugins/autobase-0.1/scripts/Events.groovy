eventWarStart = { 
    try {
      if(!(new File(".", 'staging').exists())) {
        Ant.fail("Due to limitations with Grails, we can only package Autobase if you use the default 'staging' directory location")
      }
      Ant.zip(
        destfile: new File('./staging/WEB-INF/classes', 'autobase.zip').absolutePath,
        basedir: '.',
        includes: 'migrations/**,**/*.csv,**/*.properties',
        whenempty: 'fail',
        duplicate: 'fail',
        comment: 'The Autobase migrations to execute'
      )
  } catch(Exception e) {
    Ant.fail(message: "Could not store WAR: " + e.class.simpleName + ": " + e.message)
  }
}
