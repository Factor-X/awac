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

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
// Spring imports
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import play.Application;
import play.GlobalSettings;
import play.db.jpa.JPA;
import play.i18n.Lang;
import play.i18n.Messages;
import play.libs.F;
import play.libs.Yaml;
import eu.factorx.awac.models.account.Administrator;
import eu.factorx.awac.models.account.Person;

public class Global extends GlobalSettings {

    // Spring global context
    private ApplicationContext ctx;

    @Override
    public void onStart(Application app) {
        play.Logger.info("Starting AWAC");
        // force default language to FR - test purpose
        play.Logger.info(Messages.get(new Lang(Lang.forCode("fr")), "main.welcome"));
        //play.Logger.info(Lang.getLocale().getDisplayName());
        //changeLang("fr");
        //Lang.change("fr");

     
        // ========================================
        // INTERNAL SPRING SERVICES
        // ========================================

        // read spring configuration and instanciate context
        ctx = new ClassPathXmlApplicationContext("components.xml");

        // ========================================
        // COMPILE ANGULAR APPLICATION
        // ========================================


        JPA.withTransaction(new F.Callback0() {
			
			@Override
			public void invoke() throws Throwable {
				createInitialData();			
			}
		});
    


    }


	private void createInitialData() {
		// Get Hibernate session
		Session session = JPA.em().unwrap(Session.class);

		// Check if the database is empty
        @SuppressWarnings("unchecked")
		List<Administrator> administrators = session.createCriteria(Person.class).list();
        if (administrators.isEmpty()) {
            Map<String, List<Object>> all = (Map<String, List<Object>>) Yaml.load("initial-data-awac.yml");
            // save data into DB in relevant order.
 
            System.out.println(all);
            for (Object entity : all.get("administrators")) {
            	session.saveOrUpdate(entity);
			}
            for (Object entity : all.get("accounts")) {
            	session.saveOrUpdate(entity);
			}

            AwacDummyDataCreator.createAwacDummyData(session);
        }
	}

    @Override
    public void onStop(Application app) {
        play.Logger.info("Stopping AWAC");
    }

    // Spring beans instanciation
    @Override
    public <A> A getControllerInstance(Class<A> clazz) {

        // filter clazz annotation to avoid messing win non Spring annotation
        if (clazz.isAnnotationPresent(Component.class)
                || clazz.isAnnotationPresent(Controller.class)
                || clazz.isAnnotationPresent(Service.class)
                || clazz.isAnnotationPresent(Repository.class))
            return ctx.getBean(clazz);
        else

            return null;
    }
}

