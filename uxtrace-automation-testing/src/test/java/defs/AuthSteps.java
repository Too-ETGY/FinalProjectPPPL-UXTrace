package defs;

import io.cucumber.java.en.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import pages.RegisterPage;
import utils.TestData;

import java.time.Duration;

public class AuthSteps {

    private LoginPage loginPage;
    private RegisterPage registerPage;

    // =====================
    // PRECONDITION (dipakai modul lain)
    // =====================

    @Given("User sudah login ke platform UXTrace menggunakan akun valid")
    public void userSudahLogin() {
        loginPage = new LoginPage(Hooks.driver);
        loginPage.navigateToLogin();
        loginPage.login(TestData.LOGIN_EMAIL, TestData.LOGIN_PASSWORD);

        new WebDriverWait(Hooks.driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.urlContains("/dashboard"));

        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    // =====================
    // LOGIN FLOW
    // =====================

    @Given("User berada di halaman login")
    public void userBeradaDiHalamanLogin() {
        loginPage = new LoginPage(Hooks.driver);
        loginPage.navigateToLogin();
    }

    @When("User memasukkan email dan password yang valid")
    public void userMemasukanKredensialValid() {
        loginPage.login(TestData.LOGIN_EMAIL, TestData.LOGIN_PASSWORD);
    }

    @Then("User berhasil masuk ke halaman dashboard")
    public void userBerhasilMasukDashboard() {
        new WebDriverWait(Hooks.driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.urlContains("/dashboard"));

        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    // =====================
    // REGISTER FLOW
    // =====================

    @Given("User berada di halaman register")
    public void userBeradaDiHalamanRegister() {
        registerPage = new RegisterPage(Hooks.driver);
        registerPage.navigateToRegister();
    }

    @When("User mengisi form register dengan data yang valid")
    public void userMengisiFormRegister() {
        registerPage.register(
                TestData.REGISTER_NAME,
                TestData.REGISTER_EMAIL,
                TestData.REGISTER_PASSWORD,
                TestData.REGISTER_CONFIRM_PASSWORD
        );
    }

    @Then("User berhasil diarahkan ke halaman login")
    public void userDiarahkanKeHalamanLogin() {
        new WebDriverWait(Hooks.driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.urlContains("/login"));

        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
    }
}