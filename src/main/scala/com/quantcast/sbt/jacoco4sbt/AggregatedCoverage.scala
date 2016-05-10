package com.quantcast.sbt.jacoco4sbt

import java.io._

import org.jacoco.core.data.ExecutionDataWriter
import org.jacoco.core.tools.ExecFileLoader
import sbt._
import sbt.Keys._
import sbt.ResourcesException

class AggregatedCoverage (executionDataFiles: Seq[File],
                          reportDirectory: File,
                          streams: TaskStreams) {

  def generate() {
    streams.log.info("Running aggregate-coverage: aggregates submodules jacoco.exec into jacoco-merged.exec")

    val loader = new ExecFileLoader
    executionDataFiles foreach { f => {
        streams.log.info("Found file: " + f)
        if (f.exists) loader.load(f)
      }
    }

    reportDirectory.mkdirs()
    val mergedFile = new File(reportDirectory, "jacoco-merged.exec")
    streams.log.info("Output file: " + mergedFile)
    writeToFile(mergedFile) { outputStream =>
      val dataWriter = new ExecutionDataWriter(outputStream)
      loader.getSessionInfoStore accept dataWriter
      loader.getExecutionDataStore accept dataWriter
    }
  }

  private def writeToFile(f: File)(writeFn: OutputStream => Unit) = {
    try {
      val out = new BufferedOutputStream(new FileOutputStream(f))
      try writeFn(out)
      catch {
        case e: IOException => throw new ResourcesException("Error merging Jacoco files: %s" format e.getMessage)
      } finally out.close()
    } catch {
      case e: IOException =>
        throw new ResourcesException("Unable to write out Jacoco file during merge: %s" format e.getMessage)
    }
  }
}
