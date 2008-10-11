// Create Autobase.zip whenever we compile the app.
// It'd be nice to be more conservative so we don't waste time, but there's
// no better event to hook into.
def eventCompileEnd = { compiling ->
  println("Executing Autobase.zip creation")
  Ant.remove(
    file:'${basedir}/web-app/WEB-INF/Autobase.zip',
    failonerror:false,
    quiet:true,
    verbose:true
  )
  Ant.zip(
    destfile:'${basedir}/web-app/WEB-INF/Autobase.zip', 
    basedir:'${basedir}/grails-app/migrations', 
    whenempty:'fail', 
    duplicate:'fail'
  )
}

def foo = "Loaded the Autobase event script"
println foo
