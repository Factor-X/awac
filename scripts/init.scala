import play.core._
import eu.factorx.awac.controllers._
import eu.factorx.awac.models._
import eu.factorx.awac.service._
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io._
import org.springframework.context.support.ClassPathXmlApplicationContext
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence
import play.db.jpa.JPA

println("== Run the application")
new StaticApplication(new java.io.File("."))

println("== Pretty print (for DTOs, don't try on entities ...)")
def pretty(data: Object) = {
  val mapper = new ObjectMapper();
  mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
  val str = mapper.writeValueAsString(data);
  val runtime = Runtime.getRuntime();
  val process = runtime.exec("underscore print --color");
  var line = "";
  val out = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
  out.write(str);
  out.close();
  System.out.println(scala.io.Source.fromInputStream(process.getInputStream()).getLines().mkString("\n"))
}

implicit class RichObject(i: Object) {
  def j = pretty(i)
}

println("== Spring context")
var ctx = new ClassPathXmlApplicationContext("components.xml")

println("== Entity Manager")
var emf:EntityManagerFactory = Persistence.createEntityManagerFactory("defaultPersistenceUnit")
var em:EntityManager = emf.createEntityManager()
play.db.jpa.JPA.bindForCurrentThread(em)
