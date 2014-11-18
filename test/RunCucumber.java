import org.junit.runner.RunWith;

import cucumber.api.junit.Cucumber;

/**
 * Created by gaston on 8/5/14.
 */

@RunWith(Cucumber.class)
//@Cucumber.Options(format = {"pretty"})
@Cucumber.Options(
		format = "pretty",
		tags = {"~@Ignore"},
		features = "test/features",  //refer to Feature file
		glue = { "eu.factorx.awac.functional", "eu.factorx.awac.functional.hooks" }
)

public class RunCucumber {

}