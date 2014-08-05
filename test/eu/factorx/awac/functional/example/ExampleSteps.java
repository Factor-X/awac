package eu.factorx.awac.functional.example;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import eu.factorx.awac.functional.hooks.GlobalHooks;


public class ExampleSteps {
	@Given("^I have setup Play$")
	public void I_have_setup_Play() throws Throwable {
	}

	@When("^I go to the landing page$")
	public void I_go_to_the_landing_page() throws Throwable {
		//GlobalHooks.TEST_BROWSER.goTo("http://localhost:" + GlobalHooks.PORT + "/awac/");
	}

	@Then("^the title should be \"([^\"]*)\"$")
	public void the_title_should_be(String title) throws Throwable {
		//assertThat(GlobalHooks.TEST_BROWSER.title()).isEqualTo(title);
	}
}