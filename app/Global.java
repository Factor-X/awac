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

import eu.factorx.awac.InitializationThread;
import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;
import org.joda.time.DateTime;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.Play;
import play.i18n.Lang;
import play.i18n.Messages;
import play.libs.F;
import play.libs.F.Promise;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Http.RequestHeader;
import play.mvc.Results;
import play.mvc.SimpleResult;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.Semaphore;

// Spring imports

public class Global extends GlobalSettings {



	private static InitializationThread thread;
	private static Semaphore semaphore = new Semaphore(1);

	// Spring global context
	private ApplicationContext applicationContext;


	@Override
	public void onStart(Application app) {

		play.Logger.info("Starting AWAC");
		// should be defined at compile/deploy time
		play.Logger.info("version 0.9");
		// force default language to FR - test purpose
		play.Logger.info(Messages.get(new Lang(Lang.forCode("fr")), "main.welcome"));
		//play.Logger.info(Lang.getLocale().getDisplayName());
		//changeLang("fr");
		//Lang.change("fr");


		// ========================================
		// INTERNAL SPRING SERVICES
		// ========================================

		String configLocation = Play.application().configuration().getString("spring.context.location");
		applicationContext = new ClassPathXmlApplicationContext(configLocation);
		play.Logger.info("Spring Startup @" + new Date(applicationContext.getStartupDate()));

//		if (app.isTest()) {
//			// read spring configuration and instanciate context for test purposes
//			ctx = new ClassPathXmlApplicationContext(configLocation);
//		} else {
//			// read spring configuration and instanciate context
//			ctx = new ClassPathXmlApplicationContext("components.xml");
//		}


		try {
			semaphore.acquire();

			if (thread == null) {
				thread = applicationContext.getBean(InitializationThread.class);
				thread.start();

				if (app.isDev()) {
					thread.join();
				}
			}

			semaphore.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onStop(Application app) {
		play.Logger.info("Stopping AWAC");
	}


//	@Override
//	public Action onRequest(Http.Request request, Method actionMethod) {
//		// Analytics analytics = AnalyticsUtil.start(request);
//		Action action;
//
//		// ENFORCE HTTPS on production
//		if (Play.isProd() && !isHttpsRequest(request)) {
//			action = new Action() {
//				@Override
//				public Promise<SimpleResult> call(Http.Context ctx) throws Throwable {
//					return Promise.<SimpleResult>pure(Results.redirect("https://" + ctx.request().host() + ctx.request().uri()));
//				}
//			};
//		} else {
//			if (!Play.isDev() && !thread.isInitialized()) {
//				action = new Action() {
//					@Override
//					public Promise<SimpleResult> call(Http.Context ctx) throws Throwable {
//						return Promise.<SimpleResult>pure(Results.ok("Application is starting... Please try again in a moment."));
//					}
//				};
//			} else {
//				action = super.onRequest(request, actionMethod);
//			}
//		}
//
//		//AnalyticsUtil.end(analytics);
//		return action;
//	}

	// Spring beans instanciation
	@Override
	public <A> A getControllerInstance(Class<A> clazz) {

		play.Logger.debug("Spring getControllerInstance called @" + new Date(applicationContext.getStartupDate()));
		//return applicationContext.getBean(clazz);

		// filter clazz annotation to avoid messing win non Spring annotation
		if (clazz.isAnnotationPresent(Component.class)
			|| clazz.isAnnotationPresent(Controller.class)
			|| clazz.isAnnotationPresent(Service.class)
			|| clazz.isAnnotationPresent(Repository.class)) {
			Logger.debug("getControllerInstance <clazz> " + clazz + " getBean : " + applicationContext.getBean(clazz));
			return applicationContext.getBean(clazz);
		} else {
			Logger.debug("getControllerInstance <clazz>" + clazz + " returning null to Play instance controller");
			return null;
		}
	}

	@Override
	public F.Promise<play.mvc.SimpleResult> onError(RequestHeader request, Throwable t) {

		ExceptionsDTO exceptionsDTO = new ExceptionsDTO(t.getCause().getMessage());

		Logger.error("ERROR into global : " + exceptionsDTO.getMessage());

		return Promise.<SimpleResult>pure(Results.internalServerError(exceptionsDTO
		));
	}


	private static final String SSL_HEADER = "x-forwarded-proto";

	private static boolean isHttpsRequest(Http.Request request) {
		// heroku passes header on
		return request.getHeader(SSL_HEADER) != null
			&& request.getHeader(SSL_HEADER)
			.contains("https");
	}

}

