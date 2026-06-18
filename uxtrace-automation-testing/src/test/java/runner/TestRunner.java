package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/05_dashboard.feature",
        glue = "defs",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/query-log-test-report.html",
                "json:target/cucumber-reports/query-log.json"
        },
        tags = "@Dashboard",
        monochrome = true
)
public class TestRunner {
}