package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {
//guys page object masing2 nge extend file BasePaeg ini yakk
    protected WebDriver driver;

    public BasePage() {
        // Constructor kosong untuk subclass
    }

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}