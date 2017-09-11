package com.workshop

object FunctionsHigherOrder {
  // Higher-Order functions - function that return function
  // Functions are 1st class citizen - a val can be also a function

  // This is just a syntactic sugar, so we don't need to type the method definition each time
  type BinaryOperation = (Double, Double) => Double
  type UnaryOperation = (Int) => Int

  val binaryOperationFactory: (Double, Double, BinaryOperation) => Double =
    (a: Double, b: Double, operation: BinaryOperation) => operation(a, b)
  val plus: BinaryOperation = _ + _
  val minus: BinaryOperation = _ - _
  val multiplication: BinaryOperation = _ * _
  val sum: (Double, Double) => Double = binaryOperationFactory(_, _, plus)

  def minus(a: Double, b: Double): Double = binaryOperationFactory(a, b, minus)

  // create the multiply function using `binaryOperation`
  val multiply: (Double, Double) => Double = binaryOperationFactory(_, _, multiplication)

  // create the plusOne function (use sum?)
  val plusOne: Double => Double = binaryOperationFactory(_, 1, plus)


  val numbers = Seq(1, 2, 3, 4)

  //  val sumSeqFactory: (UnaryOperation, Seq[Int]) => Int = (operation, seq) => {
  //    seq.map(operation).sum
  //  }
  val sumSeqFactory: (UnaryOperation) => Seq[Int] => Int = operation => _.map(operation).sum


  // Could we do it better?
  // Create a sumSeqFactory that receives the operation and returns a function that will do this
  // operation on all elements of the Seq before summing
  // Change the implementation of sumSeq, sumDoubles, sumSquares to use `sumSeqFactory`
  val sumSeq: (Seq[Int]) => Int = sumSeqFactory(identity)

  val sumDoubles: (Seq[Int]) => Int = sumSeqFactory(_ * 2)

  val sumSquares: (Seq[Int]) => Int = sumSeqFactory(number => Math.pow(number, 2).toInt)
}
