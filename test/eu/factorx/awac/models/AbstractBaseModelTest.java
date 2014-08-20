package eu.factorx.awac.models;

import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import play.Logger;
import play.db.jpa.JPA;
import play.db.jpa.JPAPlugin;
import play.mvc.Result;
import play.test.FakeApplication;
import play.test.Helpers;
import scala.Option;

import static java.lang.Thread.sleep;

public abstract class AbstractBaseModelTest implements ApplicationContextAware {

    protected static EntityManager em;
	protected static ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	//before class
    @BeforeClass
    public static void setUp() {

        FakeApplication app = Helpers.fakeApplication();
        Helpers.start(app);
		// TODO - this needs to be tunned
		// wait 2 seconds to be sure app is started on all environments
		try {
			sleep(2000);
		} catch (Exception e) {
			// do nothing
		}
        Option<JPAPlugin> jpaPlugin = app.getWrappedApplication().plugin(JPAPlugin.class);
        em = jpaPlugin.get().em("default");
        JPA.bindForCurrentThread(em);
	}

    // after class
    @AfterClass
    public static void tearDown() {
        JPA.bindForCurrentThread(null);
		if (em.isOpen()) em.close();
    }
}
