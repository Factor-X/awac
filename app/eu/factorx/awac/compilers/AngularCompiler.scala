package eu.factorx.awac.compilers

import java.io._
import com.fasterxml.jackson.databind.ObjectMapper

import scalax.file.{PathSet, Path}
import org.jcoffeescript._
import java.util.Arrays

class AngularCompiler {

    /**
     * Get a recursive listing of all files underneath the given directory.
     * from stackoverflow.com/questions/2637643/how-do-i-list-all-files-in-a-subdirectory-in-scala
     */
    private def getRecursiveListOfFiles(dir: File): Array[File] = {
        val these = dir.listFiles
        these ++ these.filter(_.isDirectory).flatMap(getRecursiveListOfFiles)
    }


    def compile(path: String): String = {
        val angular = Path.fromString(path)

        val roots = angular * "*.coffee"
        val controllers = angular / "controllers" ** "*.coffee"
        val directives = angular / "directives" ** "*.coffee"
        val services = angular / "services" ** "*.coffee"
        val filters = angular / "filters" ** "*.coffee"

        println(directives.head.sibling("template.jade").path)

        val compiledMain = simpleCompile(roots)
        val compiledServices = simpleCompile(services)
        val compiledFilters = simpleCompile(filters)
        val compiledDirectives = compileDirectives(directives, angular)
        val compiledControllers = simpleCompile(controllers)

        compiledMain + compiledServices + compiledFilters + compiledDirectives + compiledControllers

    }

    private def simpleCompile(files: Iterable[Path]): String = {
        var res = List[String]()
        for (f <- files) {
            // read the source
            val coffee = scala.io.Source.fromFile(f.path).getLines.mkString("\n")

            // turn it into a javascript
            println("compiling " + f.path + " ...")
            val js = new JCoffeeScriptCompiler(Arrays.asList(Option.BARE)).compile(coffee)

            // append it to the list
            res :::= List(js)
        }
        res.mkString(";")
    }

    private def compileDirectives(files: Iterable[Path], angular: Path): String = {

        var names = List[String]()
        var contents = List[String]()

        for (f <- files) {
            // turn it into an HTML
            val jadePath = f.sibling("template.jade")
            println("compiling " + jadePath.path + " ...")
            val html = de.neuland.jade4j.Jade4J.render(jadePath.path, null);

            println(html)

            // now, escape string so that it can be embedded as a variable
            val mapper: ObjectMapper = new ObjectMapper();
            val escapedHtml = mapper.writeValueAsString(html)

            // compute a decent url for the template
            var usefulPath = f.path.substring((angular / "directives").path.length)

            // to dashed
            val regex = "([a-z])([A-Z])";
            val replacement = "$1-$2";
            usefulPath = usefulPath.replaceAll(regex, replacement).toLowerCase();

            // now split and format it correctly
            var usefulPathParts = usefulPath.split("/")
            usefulPathParts = usefulPathParts.slice(0, usefulPathParts.length - 1)
            usefulPath = usefulPathParts.mkString("/")

            val url = "$/angular/templates" + usefulPath + ".html"

            // append it to the list
            names = names :+ url
            contents = contents :+ escapedHtml
        }


        val begin = "angular.module('app.components').run(function($templateCache) {"

        val end = "});";

        var res = List[String]()
        res = res :+ begin
        for((url,content) <- (names,contents).zipped) {
            res = res :+ "$templateCache.put('" + url + "', " + content + ");"
        }
        res = res :+ end


        simpleCompile(files) + res.mkString("\n")

    }

}
