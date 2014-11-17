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

public class Home extends FluentPage {
    public String getUrl() {
        return "/easyschool/";
    }

    public void isAt() {
        assertThat(find("h1", withText("Welcome to EasySchool"))).hasSize(1);
    }
}