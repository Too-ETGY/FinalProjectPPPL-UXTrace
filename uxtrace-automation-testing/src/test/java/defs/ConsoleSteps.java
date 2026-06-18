package defs;

import io.cucumber.java.en.*;
import org.junit.Assert;
import pages.ConsolePage;
import utils.TestData;

public class ConsoleSteps {

    private ConsolePage consolePage() {
        return new ConsolePage(Hooks.driver);
    }

    @Given("User menavigasi ke halaman Console")
    public void userNavigasiKeHalamanConsole() {
        Hooks.driver.get("http://localhost:5173/dashboard");
        try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
        Hooks.driver.findElement(org.openqa.selenium.By.cssSelector("a[href='/console']")).click();
        try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    // Scenario 1 - Absolute time range
    @When("User membuka dropdown filter waktu")
    public void userMembukaDropdownFilterWaktu() {
        consolePage().openTimeFilterDropdown();
    }

    @When("User mengisi start time dan end time pada absolute time range")
    public void userMengisiAbsoluteTimeRange() {
        consolePage().setAbsoluteTimeRange(
                TestData.CONSOLE_START_TIME,
                TestData.CONSOLE_END_TIME
        );
    }

    @When("User mengklik tombol Apply time range")
    public void userMengklikApplyTimeRange() {
        consolePage().applyTimeRange();
    }

    @Then("Daftar event log hanya menampilkan event sesuai rentang waktu yang dipilih")
    public void verifikasiFilterWaktu() {
        Assert.assertTrue(
                "Halaman Console tidak tampil setelah filter waktu",
                Hooks.driver.getCurrentUrl().contains("/console")
        );
    }

    // Scenario 2 - Single event search
    @When("User mengetik nama event pada search bar Console")
    public void userMengetikNamaEvent() {
        consolePage().selectEventFromDropdown(TestData.CONSOLE_SEARCH_KEYWORD);
    }

    @Then("Daftar event log hanya menampilkan event sesuai kata kunci pencarian")
    public void verifikasiHasilPencarianTunggal() {
        Assert.assertFalse(
                "Event list kosong setelah pencarian",
                consolePage().getEventListItems().isEmpty()
        );
    }

    // Scenario 3 - Multi event search
    @When("User menambahkan beberapa nama event sebagai filter pencarian")
    public void userMenambahkanBeberapaFilter() {
        consolePage().selectMultipleEventsFromDropdown(
                TestData.CONSOLE_SEARCH_KEYWORD,
                TestData.CONSOLE_SEARCH_KEYWORD_2
        );
    }

    @Then("Daftar event log menampilkan event yang sesuai dengan semua filter yang dipilih")
    public void verifikasiHasilPencarianMultiFilter() {
        Assert.assertFalse(
                "Event list kosong setelah filter",
                consolePage().getEventListItems().isEmpty()
        );
    }

    // Scenario 4 - Click detail
    @When("User mengklik salah satu event pada daftar event log")
    public void userMengklikEvent() {
        consolePage().clickFirstEvent();
    }

    @Then("Panel detail di sisi kanan harus menampilkan informasi JSON dari event tersebut")
    public void verifikasiDetailPanel() {
        Assert.assertTrue(
                "Panel detail tidak tampil",
                consolePage().isDetailPanelVisible()
        );
    }

    // Scenario 5 - Remove filter
    @When("User menghapus filter event yang sudah diterapkan")
    public void userMenghapusFilter() {
        consolePage().removeFirstFilter();
    }

    @Then("Daftar event log menampilkan semua event tanpa filter")
    public void verifikasiFilterDihapus() {
        Assert.assertFalse(
                "Event list kosong setelah filter dihapus",
                consolePage().getEventListItems().isEmpty()
        );
    }

    // Scenario 6 - Combine filter
    @Then("Daftar event log menampilkan event sesuai kombinasi filter tanggal dan event")
    public void verifikasiKombinasiFilter() {
        Assert.assertFalse(
                "Event list kosong setelah kombinasi filter",
                consolePage().getEventListItems().isEmpty()
        );
    }
}