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

package eu.factorx.awac.common.actions;

import play.libs.F.Promise;
import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Http.Response;
import play.mvc.SimpleResult;

public class CorsAction extends Action.Simple {

	@Override
	public Promise<SimpleResult> call(Context context) throws Throwable {
		Response response = context.response();
		response.setHeader("Access-Control-Allow-Origin", "*");

		//Handle preflight requests
		if (context.request().method().equals("OPTIONS")) {
			response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
			response.setHeader("Access-Control-Max-Age", "3600");
			response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization, X-Auth-Token");
			response.setHeader("Access-Control-Allow-Credentials", "true");

			return Promise.<SimpleResult>pure(ok());
		}

		response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, X-Auth-Token");
		return delegate.call(context);
	}

}
