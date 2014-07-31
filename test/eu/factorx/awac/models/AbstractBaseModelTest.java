package eu.factorx.awac.models;

import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import play.db.jpa.JPA;
import play.db.jpa.JPAPlugin;
import play.test.FakeApplication;
import play.test.Helpers;
import scala.Option;

/**
 * Created by gaston on 7/31/14.
 */
public abstract class AbstractBaseModelTest {

    protected static EntityManager em;

    @BeforeClass
    public static void setUp() {

        FakeApplication app = Helpers.fakeApplication();
        Helpers.start(app);
        Option<JPAPlugin> jpaPlugin = app.getWrappedApplication().plugin(JPAPlugin.class);
        em = jpaPlugin.get().em("default");
        JPA.bindForCurrentThread(em);
    }

    @AfterClass
    public static void tearDown() {
        JPA.bindForCurrentThread(null);
        em.close();
    }
}
