/*
 * This file is part of jacoco4sbt.
 *
 * Copyright (c) 2011-2013 Joachim Hofer & contributors
 * All rights reserved.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.quantcast.sbt.jacoco4sbt

import org.jacoco.report.FileMultiReportOutput
import org.jacoco.report.IReportVisitor
import org.jacoco.report.xml.XMLFormatter
import org.jacoco.report.html.HTMLFormatter
import org.jacoco.report.csv.CSVFormatter
import java.io.File
import java.io.FileOutputStream

sealed abstract class FormattedReport {
  val encoding : String
  def visitor(directory: File) : IReportVisitor
}

case class HTMLReport(encoding: String = "utf-8") extends FormattedReport {
  def visitor(directory: File) = {
    val formatter = new HTMLFormatter
    formatter setOutputEncoding encoding
    formatter createVisitor new FileMultiReportOutput(new File(directory, "html"))
  }
}

case class ScalaHTMLReport(encoding: String = "utf-8", withBranchCoverage: Boolean = false) extends FormattedReport {
  def visitor(directory: File) = {
    val formatter = new ScalaHtmlFormatter(withBranchCoverage)
    formatter setOutputEncoding encoding
    formatter createVisitor new FileMultiReportOutput(new File(directory, "html"))
  }
}

case class XMLReport(encoding: String = "utf-8") extends FormattedReport {
  def visitor(directory: File) = {
    val formatter = new XMLFormatter
    formatter setOutputEncoding encoding
    formatter createVisitor new FileOutputStream(new File(directory, "jacoco.xml"))
  }
}

case class CSVReport(encoding: String = "utf-8") extends FormattedReport {
  def visitor(directory: File) = {
    val formatter = new CSVFormatter
    formatter setOutputEncoding encoding
    formatter createVisitor new FileOutputStream(new File(directory, "jacoco.csv"))
  }
}
