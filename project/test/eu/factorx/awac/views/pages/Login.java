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
 
package eu.factorx.awac.views.pages;

import static org.fest.assertions.fluentlenium.FluentLeniumAssertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withText;

import org.fluentlenium.core.FluentPage;
//import components.*;

public class Login extends FluentPage {
    public String getUrl() {
		return eu.factorx.awac.controllers.routes.Authentication.login().url();
    }

    public void isAt() {
        assertThat(find("h1", withText("Authentication"))).hasSize(1);
    }

    public void login(String id, String password) {
        fill("identifier").with(id);
		fill("password").with(password);
        click("Login");
    }
}