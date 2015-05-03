package wgrm.resolver


import org.scalatest._

import wgrm.resolver.db._



abstract class ResolverSpec extends FreeSpec with Matchers

class SampleTest extends ResolverSpec {
  "A wgrm.resolver.Main object" - {
    "when calling for_test method" - {
      "should get `for test` string" in {
        info("this test info")
        assert(Main.for_test === "for test")
      }
      "should fail when compare `for_test` but we ignore this" ignore {
        assert(Main.for_test === "for_test")
      }
      "should produce TestFailedException for assertion" in {
        intercept [TestFailedException] {
          assert(Main.for_test === "for_test")
        }
      }
    }
  }
}

class YamlTest extends ResolverSpec {
  "A YamlDb object" - {
    "on realm ru" - {
      "try get spa version" in {
        info("Create new object")
        val yamlDb = new YamlDb
        info("set realm to ru")
        yamlDb.setRealm("wgt1")
        info("get spa version")
        assert(yamlDb.getCurrentVersion("spa") != Some("ok"))
        info("we get version instead default")
        assert(yamlDb.getCurrentVersion("wot", "aap2").getOrElse("no") === "no")
        assert(yamlDb.getCurrentVersion("wot", "aap") !== "ok")
      }
    }
  }
}
/*class ExampleSpec extends FlatSpec with Matchers {

  "A Stack" should "pop values in last-in-first-out order" in {
    val stack = new Stack[Int]
    stack.push(1)
    stack.push(2)
    stack.pop() should be (2)
    stack.pop() should be (1)
  }

  it should "throw NoSuchElementException if an empty stack is popped" in {
    val emptyStack = new Stack[Int]
    a [NoSuchElementException] should be thrownBy {
      emptyStack.pop()
    }
  }
}
 */
