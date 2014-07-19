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

import eu.factorx.awac.compilers.RecompilerThread;
import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import play.Application;
import play.GlobalSettings;
import play.Logger;
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

// Spring imports

public class Global extends GlobalSettings {

	// Spring global context
	private ApplicationContext ctx;
	private InitializationThread thread;

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

		thread = new InitializationThread(ctx);
		thread.start();

		if (app.isDev()) {
			RecompilerThread recompilerThread = new RecompilerThread();
			recompilerThread.start();
		}

	}

	@Override
	public void onStop(Application app) {
		play.Logger.info("Stopping AWAC");
	}

	@Override
	public Action onRequest(Http.Request request, Method actionMethod) {

		if (!thread.isInitialized()) {
			return new Action() {
				@Override
				public Promise<SimpleResult> call(Http.Context ctx) throws Throwable {
					return Promise.<SimpleResult>pure(Results.ok("Application is starting... Please try again in a moment."));
				}
			};
		}

		return super.onRequest(request, actionMethod);
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

		ExceptionsDTO exceptionsDTO = new ExceptionsDTO(t.getCause().getMessage());

		Logger.error("ERROR into global : " + exceptionsDTO.getMessage());

		return Promise.<SimpleResult>pure(Results.internalServerError(exceptionsDTO
		));
	}

}

