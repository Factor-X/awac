package eu.factorx.awac.compilers

import java.io.FileWriter
import java.util

import com.fasterxml.jackson.databind.ObjectMapper
import org.jcoffeescript._
import play.Logger

import scalax.file.Path

class AngularCompiler {

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
                Logger.debug("[UP-TO-DATE] " + f.path)
            } else {
                Logger.debug("[BUILDING] " + f.path)

                val script = scala.io.Source.fromFile(f.path).getLines().mkString("\n")
                val name: String = f.simpleName.replaceAll("\\.js$ ", "")
                val dname: String = name.replaceAll("([a-z])([A-Z])", "$1-$2").toLowerCase
                val result = template.replaceAll("__NAME__", name).replaceAll("__DNAME__", dname).replaceAll("__SCRIPT__", script)

                val fw = new FileWriter(tempFile)
                fw.write(result)
                fw.close()

            }

        }


    }

    def compile(path: String): String = {

        val angular = Path.fromString(path)
        val tmp = Path.fromString("tmp")

        val roots = angular * "*.coffee"
        val services = angular / "services" ** "*.coffee"
        val filters = angular / "filters" ** "*.coffee"
        val directives = angular / "directives" ** "*.coffee"
        val directivesTemplates = (angular / "directives" ** "*.jade") ++ (angular / "directives" ** "*.html")
        val controllers = angular / "controllers" ** "*.coffee"
        val views = (angular / "views" ** "*.jade") ++ (angular / "views" ** "*.html")

        val validators = Path.fromString("app/eu/factorx/awac") / "dto" / "validation" / "scripts" * "*.js"

        compileFiles(roots, Path.fromString("tmp/sources/"))
        compileFiles(services, Path.fromString("tmp/sources/"))
        compileFiles(filters, Path.fromString("tmp/sources/"))
        compileFiles(directives, Path.fromString("tmp/sources/"))
        compileFiles(directivesTemplates, Path.fromString("tmp/sources/"))
        compileFiles(views, Path.fromString("tmp/sources/"))
        compileFiles(controllers, Path.fromString("tmp/sources/"))


        compileFiles(validators, Path.fromString("tmp/validators/"))

        validatorsToDirectives(Path.fromString("tmp/validators/") ** "*.js", Path.fromString("tmp/validators-directives/"))

        assembleTemplates(Path.fromString("tmp/sources/") ** "*.html", Path.fromString("tmp/templates/"), angular)

        concatenate(
            (Path.fromString("tmp/sources/app/eu/factorx/awac/angular/") * "*.js")
                ++ (Path.fromString("tmp/sources/app/eu/factorx/awac/angular/services") ** "*.js")
                ++ (Path.fromString("tmp/sources/app/eu/factorx/awac/angular/filters") ** "*.js")
                ++ (Path.fromString("tmp/validators-directives") ** "*.js")
                ++ (Path.fromString("tmp/sources/app/eu/factorx/awac/angular/directives") ** "*.js")
                ++ (Path.fromString("tmp/sources/app/eu/factorx/awac/angular/controllers") ** "*.js")
                ++ (Path.fromString("tmp/templates/") ** "*.js"),
            Path.fromString("tmp/concatenated/"))

        // compiledMain + compiledServices + compiledFilters + compiledDirectives + compiledControllers

        scala.io.Source.fromFile("tmp/concatenated/concatenated.js", "utf-8").getLines().mkString("\n")
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
            Logger.debug("[UP-TO-DATE] " + f.path)
        } else {
            var result = ""
            Logger.info("[COMPILING] " + f.path)

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

            val fw = new FileWriter(tempFile)
            fw.write(result)
            fw.close()

        }
    }

    private def compileFiles(files: Iterable[Path], folder: Path) {
        for (f <- files) {
            compileFile(f, folder)
        }
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
            Logger.info("[ASSEMBLING] " + (folder / "templates.js").path)
            Logger.info(angular.path)

            var result = "angular.module('app.directives').run(function($templateCache) {"
            for (f <- files) {

                Logger.info(f.path)

                // now, escape string so that it can be embedded as a variable
                val mapper: ObjectMapper = new ObjectMapper()

                var url = ""

                if (f.path.startsWith((Path.fromString("tmp/sources") / angular / "directives").path)) {
                    Logger.info("DIRECTIVE")
                    // compute a decent url for the template
                    var usefulPath = f.path.substring((Path.fromString("tmp/sources") / angular / "directives").path.length)

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

                if (f.path.startsWith((Path.fromString("tmp/sources") / angular / "views").path)) {
                    Logger.info("VIEW")
                    val usefulPath = f.path.substring((Path.fromString("tmp/sources") / angular / "views").path.length)
                    url = "$/angular/views" + usefulPath
                }

                val content = mapper.writeValueAsString(scala.io.Source.fromFile(f.path, "utf-8").getLines().mkString("\n"))
                result += "$templateCache.put('" + url + "', " + content + ");"
            }
            result += "});"

            val fw = new FileWriter(tempFile)
            fw.write(result)
            fw.close()
        } else {
            Logger.debug("[UP-TO-DATE] " + (folder / "templates.js").path)
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
            Logger.info("[CONCATENATING] " + (folder / "concatenated.js").path)
            var result = ""
            for (f <- files) {
                result += scala.io.Source.fromFile(f.path, "utf-8").getLines().mkString("\n")
            }

            val fw = new FileWriter(tempFile)
            fw.write(result)
            fw.close()
        } else {
            Logger.debug("[UP-TO-DATE] " + (folder / "concatenated.js").path)
        }
    }


}
