package eu.factorx.awac.compilers

import java.io._

object AngularCompiler {

  private lazy val compiler = {

    (source: File) => {

      ""

    }

  }


  def compile(source: File, options: Seq[String]): String = {
    System.out.println(source.getAbsolutePath)

    //compiler(source)

    ""
  }

}
