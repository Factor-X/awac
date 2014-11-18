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
import eu.factorx.awac.util.MyrmexRuntimeException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
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
import play.libs.Akka;
import play.libs.F;
import play.libs.F.Promise;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Http.RequestHeader;
import play.mvc.Results;
import play.mvc.SimpleResult;
import scala.concurrent.duration.Duration;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

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
		play.Logger.info("version 1.0");
		// force default language to FR - test purpose
		play.Logger.info(Messages.get(new Lang(Lang.forCode("fr")), "main.welcome"));
		//play.Logger.info(Lang.getLocale().getDisplayName());
		//changeLang("fr");
		//Lang.change("fr");


		// ========================================
		// INTERNAL SPRING SERVICES
		// ========================================

		final String configLocation = Play.application().configuration().getString("spring.context.location");
		applicationContext = new ClassPathXmlApplicationContext(configLocation);
		play.Logger.info("Spring Startup @" + new Date(applicationContext.getStartupDate()));

//		if (app.isTest()) {
//			// read spring configuration and instanciate context for test purposes
//			ctx = new ClassPathXmlApplicationContext(configLocation);
//		} else {
//			// read spring configuration and instanciate context
//			ctx = new ClassPathXmlApplicationContext("components.xml");
//		}

		// run keepalive only in prod environment to avoid calls during test and dev targets
		if (app.isProd()) {
			final String awacHostname = System.getenv().get("AwacHostname");
			if (awacHostname != null) {

				Akka.system().scheduler().schedule(
					Duration.create(10, TimeUnit.SECONDS),
					Duration.create(1, TimeUnit.MINUTES),
					new Runnable() {
						public void run() {
							try {
								play.Logger.info("Getting " + awacHostname + " for keep-alive ...");
								HttpClient httpClient = new DefaultHttpClient();
								HttpGet httpGet = new HttpGet(awacHostname);
								HttpResponse response = httpClient.execute(httpGet);
								play.Logger.info("Got " + awacHostname + " for keep-alive.");
							} catch (Exception e) {
								play.Logger.info("Getting " + awacHostname + " for keep-alive ended with an exception", e);
							}
						}
					},
					Akka.system().dispatchers().defaultGlobalDispatcher()
				);
				play.Logger.info("Akka keep-alive now runs.");
			} else {
				play.Logger.info("Akka keep-alive won't run because the environment variable 'AwacHostname' does not exist.");
			}
		} // end of app.isProd()


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


	@Override
	public Action onRequest(Http.Request request, Method actionMethod) {
		// Analytics analytics = AnalyticsUtil.start(request);
		Action action;

		// ENFORCE HTTPS on production
		if (Play.isProd() && !isHttpsRequest(request)) {
			action = new Action() {
				@Override
				public Promise<SimpleResult> call(Http.Context ctx) throws Throwable {
					return Promise.<SimpleResult>pure(Results.redirect("https://" + ctx.request().host() + ctx.request().uri()));
				}
			};
		} else {
			if (!Play.isDev() && !thread.isInitialized()) {
				action = new Action() {
					@Override
					public Promise<SimpleResult> call(Http.Context ctx) throws Throwable {
						return Promise.<SimpleResult>pure(Results.ok("Application is starting... Please try again in a moment."));
					}
				};
			} else {
				action = super.onRequest(request, actionMethod);
			}
		}

		//AnalyticsUtil.end(analytics);
		return action;
	}

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

		final ExceptionsDTO exceptionsDTO;

		Throwable cause = t.getCause();
		if (cause instanceof MyrmexRuntimeException) {
			if (((MyrmexRuntimeException) cause).getBusinessErrorType() != null) {
				exceptionsDTO = new ExceptionsDTO(((MyrmexRuntimeException) cause));
			} else {
				exceptionsDTO = new ExceptionsDTO(cause.getMessage());
			}
		} else {
			exceptionsDTO = new ExceptionsDTO(cause.getMessage());
		}

		return Promise.<SimpleResult>pure(Results.internalServerError(exceptionsDTO));
	}


	private static final String SSL_HEADER = "x-forwarded-proto";

	private static boolean isHttpsRequest(Http.Request request) {
		// heroku passes header on
		return request.getHeader(SSL_HEADER) != null
			&& request.getHeader(SSL_HEADER)
			.contains("https");
	}

}

