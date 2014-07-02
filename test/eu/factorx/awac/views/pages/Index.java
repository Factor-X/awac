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

import org.fluentlenium.core.*;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.fluentlenium.FluentLeniumAssertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;

//import components.*;
import eu.factorx.awac.controllers.*;

public class Index extends FluentPage {
    public String getUrl() {
		return eu.factorx.awac.controllers.routes.Application.index().url();
    }

    public void isAt() {
        assertThat(find("h1", withText("Welcome to EasySchool"))).hasSize(1);
    }
}