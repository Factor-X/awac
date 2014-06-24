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

// Spring imports
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import play.Application;
import play.GlobalSettings;
import play.i18n.Lang;
import play.i18n.Messages;
import play.libs.Yaml;

import com.avaje.ebean.Ebean;

import eu.factorx.awac.models.Administrator;

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

        // Check if the database is empty
        if (Administrator.find.findRowCount() == 0) {
            Map<String, List<Object>> all = (Map<String, List<Object>>) Yaml.load("initial-data-awac.yml");
            // save data into DB in relevant order.
            // -> no Ebean.save(all.get("adresses"));

            System.out.println(all);

            Ebean.save(all.get("administrators"));
            Ebean.save(all.get("accounts"));

            AwacDummyDataCreator.createAwacDummyData();
        }

        // ========================================
        // INTERNAL SPRING SERVICES
        // ========================================

        // read spring configuration and instanciate context
        ctx = new ClassPathXmlApplicationContext("components.xml");

        // ========================================
        // COMPILE ANGULAR APPLICATION
        // ========================================





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

