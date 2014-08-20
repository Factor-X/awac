import java.io.PrintWriter

class AngularCompileTask {

    def execute() {
        val compiler: AngularCompiler = new AngularCompiler
        val result: String = compiler.compile("app/eu/factorx/awac/angular")
        try {
            val writer: PrintWriter = new PrintWriter("public/javascripts/app.js", "UTF-8")
            writer.print(result)
            writer.close
        }
        catch {
            case ex: Exception => {
                println("Error while watching for angular changes but resuming watching.", ex)
            }
        }
    }

}
