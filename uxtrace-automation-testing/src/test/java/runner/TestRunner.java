package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/01_auth.feature", //tambah fiturnya masing2 di sini
        glue = "defs",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/auth-test-report.html",
                "json:target/cucumber-reports/cucumber.json"
        },
        tags = "@Auth", //tambah tag masing2 di sini
        monochrome = true
)
public class TestRunner {
}