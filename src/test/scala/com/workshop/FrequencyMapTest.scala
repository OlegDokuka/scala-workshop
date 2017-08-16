package com.workshop

import org.specs2.mutable.Specification
import org.specs2.specification.Scope

import scala.util.Random

class FrequencyMapTest extends Specification {

  class Context extends Scope {
    val frequency = new FrequencyMap

    val frequencies = Seq('a' -> 10, 'b' -> 5)

    val dummyText: String =
      Random.shuffle(frequencies.flatMap { case (char, count) => Seq.fill(count)(char) }).mkString("")
  }

  "generate" should {
    "create a frequencies map" in new Context {
      frequency.generate(dummyText) must havePairs(frequencies: _*)
    }
  }
}
