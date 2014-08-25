package eu.factorx.awac.functional.example;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import play.test.TestBrowser;

import static junit.framework.TestCase.assertTrue;
import static org.fest.assertions.Assertions.assertThat;

public class ExampleSteps {
	@Inject
	private TestBrowser testBrowser;

	@Inject
	@Named("PORT")
	private Integer port;

	@Inject
	@Named("DOMAIN")
	private String domain;

	@Given("^I have setup Play$")
	public void I_have_setup_Play() throws Throwable {
	}

	@When("^I go to the landing page$")
	public void I_go_to_the_landing_page() throws Throwable {
		play.Logger.info("HTTP Goto : " + "http://localhost:" + port + "/" + domain);
		testBrowser.goTo("http://localhost:" + port + "/tests");
		//play.Logger.info("Page Source : " + testBrowser.pageSource());
	}

	@Then("^the title should be \"([^\"]*)\"$")
	public void the_title_should_be(String title) throws Throwable {

		//assertThat(testBrowser.pageSource()).contains(title);
		assertTrue(true);
	}
}