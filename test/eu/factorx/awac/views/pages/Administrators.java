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

public class Administrators extends FluentPage {
    public String getUrl() {
		return eu.factorx.awac.controllers.routes.Administrators.list(0, "identifier", "asc", "").url();
    }

    public void isAt() {
        assertThat(find("h1", withText("Administrator List"))).hasSize(1);
    }
}