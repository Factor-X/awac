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

import play.*;
import play.libs.*;

import com.avaje.ebean.Ebean;

import eu.factorx.awac.models.*;

import java.util.*;

import play.i18n.*;

// Spring imports
import org.springframework.context.*;
import org.springframework.context.support.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Repository;

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

