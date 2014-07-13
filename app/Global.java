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
import play.Logger;
import play.db.jpa.JPA;
import play.i18n.Lang;
import play.i18n.Messages;
import play.libs.F;
import play.libs.F.Promise;
import play.libs.Yaml;
import play.mvc.Http.RequestHeader;
import play.mvc.Results;
import play.mvc.SimpleResult;
import eu.factorx.awac.AwacDummyDataCreator;
import eu.factorx.awac.InMemoryData;
import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;
import eu.factorx.awac.dto.myrmex.get.TranslationsDTO;
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

        JPA.withTransaction(new F.Callback0() {

            @Override
            public void invoke() throws Throwable {
                createInitialData(ctx);
            }
        });

        // ========================================
        // INTERNAL SPRING SERVICES
        // ========================================

        // read spring configuration and instanciate context
        ctx = new ClassPathXmlApplicationContext("components.xml");

        // ========================================
        // COMPILE ANGULAR APPLICATION
        // ========================================


        createInMemoryData();
    }

    private void createInMemoryData() {
        createInMemoryTranslations();
    }

    private void createInMemoryTranslations() {

        String language = "FR";

        TranslationsDTO dto = new TranslationsDTO(language);

        dto.put("ABCD", "Deux poules discutent:<br/>- Comment vas-tu ma cocotte?<br/>- Pas très bien. Je crois que je couve quelque chose !");
        dto.put("DOUBLE-CODE", "Un double: ");
        dto.put("INTEGER-CODE", "Un entier: ");
        dto.put("TEXT-CODE", "Un texte: ");

        dto.put("DOUBLE-UNIT-CODE", "Un double avec unité: ");

        dto.put("WITH_VAR",
                "Il n'y a personne dans la maison.",
                "Il y a une personne dans la maison.",
                "Il a {0} personnes dans la maison.");

        dto.put("HEATING-CODE", "Chauffage");

        dto.put("UNITS-KILOGRAM", "kg");
        dto.put("UNITS-GRAM", "g");
        dto.put("UNITS-TON", "ton");

        dto.put("FR", "Français");
        dto.put("NL", "Nederlands");
        dto.put("EN", "English");

        dto.put("SAVE_BUTTON", "Sauvegarder");

        InMemoryData.translations.put("FR", dto);

        language = "EN";
        dto = new TranslationsDTO(language);

        dto.put("ABCD", "Deux poules discutent:<br/>- Comment vas-tu ma cocotte?<br/>- Pas très bien. Je crois que je couve quelque chose !");
        dto.put("DOUBLE-CODE", "Un double: ");
        dto.put("INTEGER-CODE", "Un entier: ");
        dto.put("TEXT-CODE", "Un texte: ");

        dto.put("DOUBLE-UNIT-CODE", "Un double avec unité: ");

        dto.put("WITH_VAR",
                "There is nobody in the house.",
                "There is one person in the house",
                "There are {0} people in the house");

        dto.put("HEATING-CODE", "Heating");

        dto.put("UNITS-KILOGRAM", "kg");
        dto.put("UNITS-GRAM", "g");
        dto.put("UNITS-TON", "ton");

        dto.put("FR", "Français");
        dto.put("NL", "Nederlands");
        dto.put("EN", "English");

        dto.put("SAVE_BUTTON", "Save");


        InMemoryData.translations.put("EN", dto);


    }


    private void createInitialData(ApplicationContext ctx) {
        // Get Hibernate session
        Session session = JPA.em().unwrap(Session.class);

        // Check if the database is empty
        @SuppressWarnings("unchecked")
        List<Administrator> administrators = session.createCriteria(Person.class).list();
        if (administrators.isEmpty()) {
            @SuppressWarnings("unchecked")
			Map<String, List<Object>> all = (Map<String, List<Object>>) Yaml.load("initial-data-awac.yml");
            // save data into DB in relevant order.

            System.out.println(all);
            for (Object entity : all.get("organizations")) {
                session.saveOrUpdate(entity);
            }
            for (Object entity : all.get("administrators")) {
                session.saveOrUpdate(entity);
            }
            for (Object entity : all.get("accounts")) {
                session.saveOrUpdate(entity);
            }

            AwacDummyDataCreator.createAwacDummyData(ctx, session);
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

    @Override
    public F.Promise<play.mvc.SimpleResult> onError(RequestHeader request, Throwable t) {

        ExceptionsDTO exceptionsDTO= new ExceptionsDTO(t.getCause().getMessage());

        Logger.error("ERROR into global : " + exceptionsDTO.getMessage());

        return Promise.<SimpleResult>pure(Results.internalServerError(exceptionsDTO
        ));
    }

}

