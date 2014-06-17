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
package eu.factorx.awac.views;

import org.junit.*;

import play.test.*;
import static play.test.Helpers.*;

import org.fluentlenium.core.*;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.fluentlenium.FluentLeniumAssertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import com.google.common.base.*;

import eu.factorx.awac.views.pages.*;
//import components.*;

public class LoginTest extends WithBrowser {

    //public Drawer drawer;

    @Before
    public void setUp() {
        start();
        Login login = browser.createPage(Login.class);
        login.go();
        login.login("admin", "password");
        //drawer = browser.createPage(Home.class).drawer();
    }

	@Test
	public void checkLogged() {
		//login.isAt();
		//assertThat(find("h1", withText("Authentication"))).hasSize(1);
		assertThat(browser.$("h1").first().getText()).isEqualTo("Welcome to AWAC");
	}
}