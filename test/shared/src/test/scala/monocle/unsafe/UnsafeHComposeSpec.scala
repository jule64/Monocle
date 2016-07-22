package monocle.unsafe

import monocle._
import monocle.law.discipline.TraversalTests
import monocle.macros.GenLens

/**
  *
  */
class UnsafeHComposeSpec extends MonocleSuite {

  case class Point(x: Int, y: Int, z: Int, name: String)

  object Point {
    val x: Lens[Point, Int] = GenLens[Point](_.x)
    val y: Lens[Point, Int] = GenLens[Point](_.y)
    val z: Lens[Point, Int] = GenLens[Point](_.z)

  }

  test("should equal"){
    import Point._
    val t = UnsafeHCompose.unsafeHCompose(x, y, z)
    t.modify(_ + 1)(Point(0,1,2, "plop")) shouldEqual Point(1,2,3, "plop")
  }




}
