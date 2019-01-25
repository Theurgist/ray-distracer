package engine.system

import com.typesafe.scalalogging.StrictLogging

import scala.collection.mutable.ArrayBuffer

/**
  * Benchmarking log utility
  *
  * @param name report name
  * @param consoleOutputOnly if true, use stdout instead logger
  */
class PerformanceTimer(name: String, enabled: Boolean = true, consoleOutputOnly: Boolean = false) extends StrictLogging {
  //TODO Hierarchy

  private var currentLapNumber = 0
  private var lastTimestamp: Long = 0
  private var laps: ArrayBuffer[Lap] = new ArrayBuffer[Lap](1024)



  def restart(): Unit = {
    currentLapNumber = 0
    lastTimestamp = 0
    laps.clear()
  }


  def startLap(): Unit = {
    currentLapNumber += 1
    lastTimestamp = System.nanoTime()
  }
  def finishLap(description: String = "", printImmediately: Boolean = false): Unit = {
    val currentTimestamp = System.nanoTime()
    val delta = currentTimestamp - lastTimestamp
    lastTimestamp = currentTimestamp

    val lap = Lap(currentLapNumber, delta, description)
    laps += lap

    if (printImmediately)
      log(lap.toString)
  }



  def finish(): Vector[Lap] = {
    val totalNanos = laps.map(_.nanos).sum
    val report = f"Performance analysis for '$name' has been finished\n" +
      f"############################################################\n" +
      laps.map(lap => lap.toString).mkString("\n") + "\n" +
      f"------\n" +
      f" Total ⌚${totalNanos / 1000000000}%8ds${totalNanos / 1000000 % 1000}%-3dms\n" +
      f"############################################################"

    log(report)
    laps.toVector
  }

  private def log(msg: String): Unit =
    if (enabled)
      if (consoleOutputOnly)
        System.out.println(msg)
      else
        logger.info(msg)

}

case class Lap(number: Int, nanos: Long, description: String) {
  override def toString: String =
    f" ⚡$number%3d: ⌚${nanos / 1000000000}%8ds${nanos / 1000000 % 1000}%-3dms 🛈 $description"

}

