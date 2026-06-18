package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/02_alarm.feature",
        glue = "defs",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/alarm-test-report.html",
                "json:target/cucumber-reports/cucumber.json"
        },

        tags = "@Alarm",
        monochrome = true

)
public class TestRunner {
}