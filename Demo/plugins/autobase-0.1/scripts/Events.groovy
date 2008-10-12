// Create Autobase.zip whenever we compile the app.
// It'd be nice to be more conservative so we don't waste time, but there's
// no better event to hook into.
eventCompileEnd = { compiling ->
  if("$projectName".toString().equalsIgnoreCase('Autobase')) { return }
  println("Executing Autobase.zip creation")
  Ant.sequential { 
    delete(
      file:'${basedir}/web-app/WEB-INF/Autobase.zip',
      failonerror:false,
      quiet:true,
      verbose:true
    )
    zip(
      destfile:'${basedir}/web-app/WEB-INF/Autobase.zip', 
      basedir:'${basedir}/migrations', 
      whenempty:'fail', 
      duplicate:'fail'
    )
  }
}

def foo = "Loaded the Autobase event script"
println foo
