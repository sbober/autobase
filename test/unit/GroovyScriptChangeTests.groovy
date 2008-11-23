import autobase.change.*
import liquibase.*
import liquibase.change.*

class GroovyScriptChangeTest extends GroovyTestCase {
  
  void testSomething() { assertTrue true }

  void testGroovyScriptChangeFindAndBuild() {
    assertNotNull new GroovyScriptChange()
    assertNotNull GroovyScriptChange.newInstance()
    Class c = GroovyScriptChange
    String name = 'GroovyScriptChange'
    String pkg = 'autobase.change'
    assertEquals(name, c.simpleName)
    assertEquals(pkg, c.getPackage().name)
    assertEquals("${pkg}.${name}", c.name)
    assertNotNull Class.forName(c.name)
//    assertNotNull Class.forName("${pkg}.${name}")
  }
  
  void testGroovyScriptChangeIsAChange() {
    assertTrue("GroovyScriptChange is not a change", GroovyScriptChange instanceof liquibase.change.Change)
  }

}
