/*
 *
 * Instant Play Framework
 * AWAC
 *                       
 *
 * Copyright (c) 2014 Factor-X.
 * Author Gaston Hollands
 *
 */

package eu.factorx.awac.models;

import java.io.IOException;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import play.db.jpa.JPA;
import play.test.FakeApplication;
import play.test.Helpers;
import play.test.WithApplication;

public class BaseModelTest extends WithApplication {

	public static FakeApplication app;
	public static String createDdl = "";
	public static String dropDdl = "";
	private EntityManager em;

	@BeforeClass
	public static void startApp() throws IOException {
		app = Helpers.fakeApplication(Helpers.inMemoryDatabase());
		Helpers.start(app);

		// Reading the evolution file
		// String evolutionContent = FileUtils.readFileToString(app.getWrappedApplication().getFile("conf/evolutions/default/1.sql"));
		// Splitting the String to get Create & Drop DDL
		// String[] splittedEvolutionContent = evolutionContent.split("# --- !Ups");
		// String[] upsDowns = splittedEvolutionContent[1].split("# --- !Downs");
		// createDdl = upsDowns[0];
		// dropDdl = upsDowns[1];
	}

	@AfterClass
	public static void stopApp() {
		Helpers.stop(app);
	}

	@Before
	public void setUp() {
		EntityManager em = JPA.em();
		JPA.bindForCurrentThread(em);
	}

	@After
	public void tearDown() {
		JPA.bindForCurrentThread(null);
		em.close();
	}

}
