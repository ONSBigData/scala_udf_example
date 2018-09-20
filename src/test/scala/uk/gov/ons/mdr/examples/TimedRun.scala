/**
  * Timed run of JaroWinklerDistance
  */
package uk.gov.ons.mdr.examples

import java.io.InputStream

import scala.collection.mutable

object TimedRun extends App {

  /** Read in the csv of word pairs
    *
    * @return Pairs of words to pass to JaroWinklerDistance
    */
  def fetchPairs(): List[(String, String)] = {

    val wordPairsBuffer = mutable.ListBuffer[(String, String)]()

    val stream: InputStream = getClass.getResourceAsStream("/word_pairs.csv")
    val bufferedSource = scala.io.Source.fromInputStream( stream )

    for (line <- bufferedSource.getLines) {
      val cols = line.split(",").map(_.trim)
      val pair = (cols(0), cols(1))
      wordPairsBuffer += (pair)

    }
    bufferedSource.close

    wordPairsBuffer.result()
  }

  println("Starting TimedRun")

  println("Fetching word pairs")
  val wordPairs = fetchPairs()

  val distance = JaroWinklerDistance()

  def run_similarity(wordPairs: List[(String, String)],
                     distance: JaroWinklerDistance): Unit = {

    for ((left, right) <- wordPairs) {
      distance.call(left, right)
    }

  }

  def trial(): Double = {
    val startTime = System.nanoTime()
    (1 to 100).foreach(_ => run_similarity(wordPairs, distance))

    val duration = System.nanoTime() - startTime
    duration / 1000000000.0
  }

  // print results
  (1 to 3).foreach(_ => {
    println("Running tests")
    (1 to 10).foreach(_ => println(trial()))
  }
  )
}
