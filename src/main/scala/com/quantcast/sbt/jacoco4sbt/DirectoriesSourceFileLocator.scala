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

import org.jacoco.report.DirectorySourceFileLocator
import org.jacoco.report.ISourceFileLocator

import java.io.File

class DirectoriesSourceFileLocator(directories: Seq[File], sourceEncoding: String, tabWidth: Int) 
    extends ISourceFileLocator {
  
  override def getSourceFile(packageName: String, fileName: String) = {
    def findInDirectory(dir: File, pkg: String) = Option(dirSourceLocator(dir).getSourceFile(pkg, fileName))
    def dirSourceLocator(dir: File) = new DirectorySourceFileLocator(dir, sourceEncoding, tabWidth)
    def findInRootOrPackageDirectory(dir: File) =
      findInDirectory(dir, packageName) orElse findInDirectory(dir, "")
    (directories flatMap findInRootOrPackageDirectory).headOption getOrElse null
  }
  
  override def getTabWidth = tabWidth
}