import java.io.FileWriter
import java.util

import com.fasterxml.jackson.databind.ObjectMapper
import org.jcoffeescript._
import scalax.file.{Path, PathSet}

class AngularCompiler {


    def setContentIfDifferent(path: String, content: String) {

        var same = false
        try {
            val existingContent = scala.io.Source.fromFile(path).getLines().mkString("\n")
            same = (existingContent == content)
        } catch {
            case e: Exception =>
        }

        if (!same) {
            val fw = new FileWriter(path)
            fw.write(content)
            fw.close()
        }
    }

    def validatorsToDirectives(ps: Iterable[Path], folder: Path) = {

        val template =
            """
              |angular.module('app.directives').directive('mm__NAME__Validator', function(){
              |    return {
              |        restrict: 'A',
              |        require: 'ngModel',
              |        link: function(scope, elm, attrs, ctrl) {
              |            console.log(attrs);
              |
              |            ctrl.$parsers.unshift(function(viewValue) {
              |
              |                var o = {};
              |
              |                for(k in attrs) {
              |                    if( k.substring(0, 'mm__NAME__Validator'.length) == 'mm__NAME__Validator' && k.length > 'mm__NAME__Validator'.length) {
              |                        arg = k.substring('mm__NAME__Validator'.length);
              |                        o[arg.toLowerCase()] = attrs[k];
              |                    }
              |                }
              |
              |                ;
              |
              |                __SCRIPT__
              |
              |                ;
              |
              |                var result = validate(viewValue, o);
              |
              |                if (result) {
              |                  ctrl.$setValidity('__DNAME__', true);
              |                  return viewValue;
              |                } else {
              |                  ctrl.$setValidity('__DNAME__', false);
              |                  return undefined;
              |                }
              |
              |            });
              |        }
              |    };
              |});
            """.stripMargin

        for (f <- ps) {
            val tempFile = new java.io.File((folder / f.simpleName).path + ".js")
            tempFile.getParentFile.mkdirs()

            if (tempFile.exists() && tempFile.lastModified >= f.lastModified) {
                // nothing to do, tempfile is up-to-date
                println("[UP-TO-DATE] " + f.path)
            } else {
                println("[BUILDING] " + f.path)

                val script = scala.io.Source.fromFile(f.path).getLines().mkString("\n")
                val name: String = f.simpleName.replaceAll("\\.js$ ", "")
                val dname: String = name.replaceAll("([a-z])([A-Z])", "$1-$2").toLowerCase
                val result = template.replaceAll("__NAME__", name).replaceAll("__DNAME__", dname).replaceAll("__SCRIPT__", script)

                setContentIfDifferent(tempFile.getPath, result)

            }

        }


    }

    def compile(path: String): String = {

        val angular = Path.fromString(path)
        val tmp = Path.fromString(".tmp")

        val roots: PathSet[Path] = angular * "*.coffee"
        val routes: PathSet[Path] = angular / "routes" ** "*.coffee"
        val services: PathSet[Path] = angular / "services" ** "*.coffee"
        val filters: PathSet[Path] = angular / "filters" ** "*.coffee"
        val directives: PathSet[Path] = angular / "directives" ** "*.coffee"
        val directivesTemplates: PathSet[Path] = (angular / "directives" ** "*.jade") ++ (angular / "directives" ** "*.html")
        val controllers: PathSet[Path] = angular / "controllers" ** "*.coffee"
        val views: PathSet[Path] = (angular / "views" ** "*.jade") ++ (angular / "views" ** "*.html")

        val validators = Path.fromString("public/javascripts") / "scripts" * "*.js"

        val m: List[Thread] = List(routes, roots, services, filters, validators, directives, directivesTemplates, views, controllers).map { v =>
            val t = new Thread(new Runnable {
                def run() {
                    compileFiles(v, Path.fromString(".tmp/sources/"))
                }
            })
            t.start()
            t
        }
        m.foreach { t => t.join()}

        validatorsToDirectives(Path.fromString(".tmp/validators/") ** "*.js", Path.fromString(".tmp/validators-directives/"))

        assembleTemplates(Path.fromString(".tmp/sources/") ** "*.html", Path.fromString(".tmp/templates/"), angular)

        concatenate(
            (Path.fromString(".tmp/sources/app/eu/factorx/awac/angular/routes") * "*.js"
                ++ Path.fromString(".tmp/sources/app/eu/factorx/awac/angular/") * "*.js")
                ++ (Path.fromString(".tmp/sources/app/eu/factorx/awac/angular/services") ** "*.js")
                ++ (Path.fromString(".tmp/sources/app/eu/factorx/awac/angular/filters") ** "*.js")
                ++ (Path.fromString(".tmp/validators-directives") ** "*.js")
                ++ (Path.fromString(".tmp/sources/app/eu/factorx/awac/angular/directives") ** "*.js")
                ++ (Path.fromString(".tmp/sources/app/eu/factorx/awac/angular/controllers") ** "*.js")
                ++ (Path.fromString(".tmp/templates/") ** "*.js"),
            Path.fromString(".tmp/concatenated/")
        )

        // compiledMain + compiledServices + compiledFilters + compiledDirectives + compiledControllers
        val res = scala.io.Source.fromFile(".tmp/concatenated/concatenated.js", "utf-8").getLines().mkString("\n")

        println("ANGULAR APP COMPILED")
        res
    }

    private def compileFile(f: Path, folder: Path) {

        var targetExtension = ""
        val sourceExtension = f.path.split("\\.").last.toLowerCase

        if (sourceExtension == "coffee") {
            targetExtension = "js"
        }
        if (sourceExtension == "js") {
            targetExtension = "js"
        }
        if (sourceExtension == "jade") {
            targetExtension = "html"
        }
        if (sourceExtension == "html") {
            targetExtension = "html"
        }

        val tempFile = new java.io.File((folder / f).path.replaceAll("\\." + sourceExtension + "$", "." + targetExtension))
        tempFile.getParentFile.mkdirs()

        if (tempFile.exists() && tempFile.lastModified >= f.lastModified) {
            // nothing to do, tempfile is up-to-date
            println("[UP-TO-DATE] " + f.path)
        } else {
            var result = ""
            println("[COMPILING] " + f.path)

            try {
                if (sourceExtension == "coffee") {
                    val source = scala.io.Source.fromFile(f.path).getLines().mkString("\n")
                    result = new JCoffeeScriptCompiler(util.Arrays.asList(Option.BARE)).compile(source)
                }

                if (sourceExtension == "js") {
                    val source = scala.io.Source.fromFile(f.path).getLines().mkString("\n")
                    result = source
                }

                if (sourceExtension == "jade") {
                    result = de.neuland.jade4j.Jade4J.render(f.path, null)
                }

                if (sourceExtension == "html") {
                    val source = scala.io.Source.fromFile(f.path).getLines().mkString("\n")
                    result = source
                }

                setContentIfDifferent(tempFile.getPath, result)
            } catch {
                case e: Exception =>
                    println("[ERROR] " + f.path + " : " + e.getMessage + "\n" + e.getStackTraceString)
            }
        }
    }

    private def compileFiles(files: Iterable[Path], folder: Path) {
        val m: List[Thread] = files.toList.map { v =>
            var t = new Thread(new Runnable {
                def run() {
                    compileFile(v, folder)
                }
            })
            t.start()
            t
        }
        m.foreach { t => t.join()}
    }

    private def assembleTemplates(files: Iterable[Path], folder: Path, angular: Path) {
        val tempFile = new java.io.File((folder / "templates.js").path)
        tempFile.getParentFile.mkdirs()

        var mustRemake = false
        for (f <- files) {
            if (!tempFile.exists || tempFile.lastModified < f.lastModified) {
                mustRemake = true
            }
        }

        if (mustRemake) {
            println("[ASSEMBLING] " + (folder / "templates.js").path)

            var result = "angular.module('app.directives').run(function($templateCache) {"
            for (f <- files) {
                // now, escape string so that it can be embedded as a variable
                val mapper: ObjectMapper = new ObjectMapper()

                var url = ""

                if (f.path.startsWith((Path.fromString(".tmp") / "sources" / angular / "directives").path)) {
                    // compute a decent url for the template
                    var usefulPath = f.path.substring((Path.fromString(".tmp") / "sources" / angular / "directives").path.length)

                    // to dashed
                    val regex = "([a-z])([A-Z])"
                    val replacement = "$1-$2"
                    usefulPath = usefulPath.replaceAll(regex, replacement).toLowerCase

                    // now split and format it correctly
                    var usefulPathParts = usefulPath.split("[/\\\\]")
                    usefulPathParts = usefulPathParts.slice(0, usefulPathParts.length - 1)
                    usefulPath = usefulPathParts.slice(usefulPathParts.length - 1, usefulPathParts.length).mkString

                    url = "$/angular/templates/" + usefulPath + ".html"
                }

                if (f.path.startsWith((Path.fromString(".tmp") / "sources" / angular / "views").path)) {
                    val usefulPath = f.path.substring((Path.fromString(".tmp") / "sources" / angular / "views").path.length)
                    url = "$/angular/views" + usefulPath
                }

                val content = mapper.writeValueAsString(scala.io.Source.fromFile(f.path, "utf-8").getLines().mkString("\n"))
                result += "$templateCache.put('" + url + "', " + content + ");"
            }
            result += "});"

            setContentIfDifferent(tempFile.getPath, result)

        } else {
            println("[UP-TO-DATE] " + (folder / "templates.js").path)
        }
    }

    private def concatenate(files: Iterable[Path], folder: Path) {
        val tempFile = new java.io.File((folder / "concatenated.js").path)
        tempFile.getParentFile.mkdirs()

        var mustRemake = false
        for (f <- files) {
            if (!tempFile.exists || tempFile.lastModified < f.lastModified) {
                mustRemake = true
            }
        }

        if (mustRemake) {
            println("[CONCATENATING] " + (folder / "concatenated.js").path)
            var result = ""
            for (f <- files) {
                result += scala.io.Source.fromFile(f.path, "utf-8").getLines().mkString("\n")
            }
            setContentIfDifferent(tempFile.getPath, result)
        } else {
            println("[UP-TO-DATE] " + (folder / "concatenated.js").path)
        }
    }


}


