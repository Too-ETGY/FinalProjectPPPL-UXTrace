package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class AlarmPage extends BasePage {

    private By btnCreateAlarm = By.xpath("//button[contains(text(), 'Tambah Alarm')]");

    private By inputSearchAlarm = By.xpath("//input[@placeholder='Cari alarm anda...']");

    private By tableAlarmRows = By.cssSelector("tbody tr");
    private By checkboxAlarmRows = By.cssSelector("tbody input[type='checkbox']");

    private By dialogConfirmDelete = By.xpath("//dialog[contains(@class, 'rounded-2xl')]");
    private By btnCancelDelete = By.xpath("//dialog//button[contains(text(), 'Batal')]");

    private By inputAlarmName = By.id("alarm-title");
    private By inputAlarmQuery = By.id("alarm-query");
    private By inputAlarmMessage = By.id("alarm-message");
    private By inputAlarmInterval = By.id("alarm-interval");
    private By inputAlarmTelegram = By.id("alarm-telegram");

    private By btnKirimUjiAlert = By.xpath("//dialog//button[contains(text(), 'Kirim Uji Alert')]");
    private By btnSimpanAlarm = By.xpath("//dialog//button[contains(text(), 'Simpan')]");
    private By btnCloseModal = By.xpath("//dialog//button[@aria-label='Close']");

    // Dialog & teks hasil uji alert
    private By dialogTestResult = By.xpath("//dialog[contains(@class, 'rounded-2xl')]//p[contains(text(), 'Berhasil') or contains(text(), 'Gagal')]");
    private By textUjiAlertStatus = By.xpath("//dialog//p[contains(text(), 'Berhasil') or contains(text(), 'Gagal')]");
    private By btnCloseTestResult = By.xpath("//dialog//button[contains(text(), 'Selesai')]");


    public AlarmPage(WebDriver driver) {
        super(driver);
        // driver dan wait sudah dihandle BasePage, tidak perlu set ulang
    }


    public void clickCreateAlarmButton() {
        wait.until(ExpectedConditions.elementToBeClickable(btnCreateAlarm)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputAlarmName));
        System.out.println("Modal tambah alarm terbuka");
    }

    public void clickMultiDeleteButton() {
        try {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
            Thread.sleep(500);

            WebElement deleteBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class, 'bg-error-icon') and contains(text(), 'Hapus')]")
            ));

            System.out.println("Menemukan tombol bulk delete: '" + deleteBtn.getText() + "'");
            deleteBtn.click();

        } catch (Exception e) {
            System.out.println("Tombol bulk delete tidak ditemukan: " + e.getMessage());
            throw new RuntimeException("Tombol bulk delete tidak ditemukan setelah checklist");
        }
    }

    public void searchAlarmByKeyword(String keyword) {
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(inputSearchAlarm));
        searchBox.clear();
        searchBox.sendKeys(keyword);
        searchBox.sendKeys(Keys.ENTER);
        try { Thread.sleep(1500); } catch (InterruptedException e) {}
        System.out.println("Mencari: " + keyword);
    }

    public void fillAlarmFormComplete(String title, String query, String message, String interval, String telegram) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputAlarmName));

        WebElement nameField = driver.findElement(inputAlarmName);
        nameField.clear();
        nameField.sendKeys(title);

        WebElement queryField = driver.findElement(inputAlarmQuery);
        queryField.clear();
        queryField.sendKeys(query);

        WebElement messageField = driver.findElement(inputAlarmMessage);
        messageField.clear();
        messageField.sendKeys(message);

        WebElement intervalField = driver.findElement(inputAlarmInterval);
        intervalField.clear();
        intervalField.sendKeys(interval);

        WebElement telegramField = driver.findElement(inputAlarmTelegram);
        telegramField.clear();
        telegramField.sendKeys(telegram);

        System.out.println("Form diisi: " + title);
    }

    public void clickKirimUjiAlert() {
        wait.until(ExpectedConditions.elementToBeClickable(btnKirimUjiAlert)).click();
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
        System.out.println("Klik Kirim Uji Alert");
    }

    public void clickSimpanAlarm() {
        wait.until(ExpectedConditions.elementToBeClickable(btnSimpanAlarm)).click();
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
        System.out.println("Klik Simpan Alarm");
    }

    public String getUjiAlertStatusText() {
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        wait.until(ExpectedConditions.visibilityOfElementLocated(dialogTestResult));
        WebElement statusElement = driver.findElement(textUjiAlertStatus);
        String text = statusElement.getText();
        System.out.println("Status alert: " + text);
        return text;
    }

    public void clickCloseUjiAlert() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(btnCloseTestResult)).click();
            System.out.println("Klik tombol Selesai");
        } catch (Exception e) {
            driver.findElement(By.tagName("body")).click();
            System.out.println("Klik body untuk menutup dialog");
        }
        try { Thread.sleep(500); } catch (InterruptedException e) {}
    }

    public boolean isAlarmTextVisibleInTable(String expectedText) {
        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        List<WebElement> rows = driver.findElements(tableAlarmRows);
        for (WebElement row : rows) {
            try {
                if (row.getText().contains(expectedText)) {
                    System.out.println("Teks ditemukan: " + expectedText);
                    return true;
                }
            } catch (StaleElementReferenceException e) {
                continue;
            }
        }
        System.out.println("Teks tidak ditemukan: " + expectedText);
        return false;
    }

    public void clickEditButtonByText(String alarmTitle) {
        WebElement editBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//tr[contains(., '" + alarmTitle + "')]//button[contains(text(), 'Edit')]")
        ));
        editBtn.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputAlarmName));
        System.out.println("Klik Edit pada alarm: " + alarmTitle);
    }

    public void clickDeleteButtonByText(String alarmTitle) {
        WebElement deleteBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//tr[contains(., '" + alarmTitle + "')]//button[contains(text(), 'Hapus')]")
        ));
        deleteBtn.click();
        System.out.println("Klik Hapus pada alarm: " + alarmTitle);
    }


    public void confirmDelete() {
        try { Thread.sleep(500); } catch (InterruptedException e) {}

        wait.until(ExpectedConditions.visibilityOfElementLocated(dialogConfirmDelete));

        WebElement confirmBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//dialog//button[contains(text(), 'Hapus')]")
        ));
        confirmBtn.click();

        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(dialogConfirmDelete));
        } catch (Exception e) {}

        try { Thread.sleep(3000); } catch (InterruptedException e) {}
        System.out.println("Konfirmasi hapus selesai");
    }


    public void checkMultipleAlarmsInTable() {
        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
        try { Thread.sleep(500); } catch (InterruptedException e) {}

        List<WebElement> checkboxes = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(checkboxAlarmRows)
        );

        if (checkboxes.size() >= 2) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkboxes.get(0));
            try { Thread.sleep(300); } catch (InterruptedException e) {}
            checkboxes.get(0).click();

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkboxes.get(1));
            try { Thread.sleep(300); } catch (InterruptedException e) {}
            checkboxes.get(1).click();

            System.out.println("Mengecek 2 alarm pertama");

            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
            try { Thread.sleep(1000); } catch (InterruptedException e) {}

        } else {
            System.out.println("Tidak cukup checkbox. Tersedia: " + checkboxes.size());
        }
    }

    public boolean waitUntilAlarmDisappears(String expectedText) {
        try {
            driver.navigate().refresh();
            try { Thread.sleep(2000); } catch (InterruptedException e) {}

            wait.until(driver -> {
                List<WebElement> rows = driver.findElements(tableAlarmRows);
                for (WebElement row : rows) {
                    try {
                        if (row.getText().contains(expectedText)) {
                            return false; // masih ada, terus tunggu
                        }
                    } catch (StaleElementReferenceException e) {
                        return true; // row stale = sudah hilang
                    }
                }
                return true; // tidak ditemukan = sudah terhapus
            });
            System.out.println("Teks berhasil menghilang: " + expectedText);
            return false;

        } catch (Exception e) {
            System.out.println("Teks masih ada setelah tunggu: " + expectedText);
            return true;
        }
    }
}