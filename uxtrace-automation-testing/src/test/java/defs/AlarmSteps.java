package defs;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import pages.AlarmPage;
import utils.TestData;

public class AlarmSteps {

    private AlarmPage alarmPage;

    @Before(order = 2)
    public void initPage() {
        alarmPage = new AlarmPage(Hooks.driver);
    }


    @Given("User sudah login ke platform UXTrace menggunakan akun valid")
    public void userSudahLoginKePlatformUXTrace() {
        if (Hooks.driver == null) {
            throw new RuntimeException("Driver tidak terinisialisasi!");
        }
        System.out.println("Verifikasi: User sudah login");
    }

    @And("User menavigasi ke halaman Alarm Management")
    public void userMenavigasiKeHalamanAlarm() {
        Hooks.driver.get(TestData.BASE_URL + "/alarm"); // ✅ FIX: pakai BASE_URL
        System.out.println("Navigasi ke halaman Alarm Management");
    }

    @When("User mengklik tombol {string} untuk membuka form modal")
    public void userMengklikTombolCreateAlarm(String btnName) {
        alarmPage.clickCreateAlarmButton();
        System.out.println("Klik tombol: " + btnName);
    }

    @And("User mengisi form alarm dengan data valid")
    public void userMengisiFormAlarmDenganDataValid() {
        alarmPage.fillAlarmFormComplete(
                TestData.ALARM_TITLE,
                TestData.ALARM_QUERY,
                TestData.ALARM_MESSAGE,
                TestData.ALARM_INTERVAL,
                TestData.ALARM_TELEGRAM
        );
        System.out.println("Mengisi form alarm dengan data valid");
    }

    @And("User mengubah data alarm dengan data baru")
    public void userMengubahDataAlarmDenganDataBaru() {
        alarmPage.fillAlarmFormComplete(
                TestData.ALARM_EDIT_TITLE,
                TestData.ALARM_EDIT_QUERY,
                TestData.ALARM_EDIT_MESSAGE,
                TestData.ALARM_EDIT_INTERVAL,
                TestData.ALARM_EDIT_TELEGRAM
        );
        System.out.println("Mengubah data alarm dengan data baru");
    }

    @And("User mengklik tombol {string} pada form modal")
    public void userMengklikTombolKirimUjiAlert(String btnName) {
        alarmPage.clickKirimUjiAlert();
        System.out.println("Klik tombol: " + btnName);
    }

    @And("User mengklik tombol {string} pada form edit modal")
    public void userMengklikTombolKirimUjiAlertDiFormEdit(String btnName) {
        alarmPage.clickKirimUjiAlert();
        System.out.println("Klik tombol: " + btnName + " pada form edit");
    }

    @And("User mengklik tombol {string} untuk mengonfirmasi pembuatan")
    public void userMengklikTombolSimpanAlarmPembuatan(String btnName) {
        alarmPage.clickSimpanAlarm();
        System.out.println("Klik tombol: " + btnName);
    }

    @And("User mengklik tombol {string} untuk mengonfirmasi pembaruan")
    public void userMengklikTombolSimpanAlarmPembaruan(String btnName) {
        alarmPage.clickSimpanAlarm();
        System.out.println("Klik tombol: " + btnName);
    }

    @When("User mengklik tombol {string} untuk menutup jendela pop-up uji alert")
    public void userMengklikTombolCloseUjiAlert(String btnName) {
        alarmPage.clickCloseUjiAlert();
        System.out.println("Klik tombol: " + btnName);
    }


    @When("User mengklik tombol {string} pada data alarm yang sudah ada")
    public void userMengklikTombolEditPadaDataAlarm(String btnName) {
        alarmPage.clickEditButtonByText(TestData.ALARM_TITLE);
        System.out.println("Klik tombol " + btnName + " pada alarm: " + TestData.ALARM_TITLE);
    }

    @When("User mengetik kata kunci pencarian pada search bar alarm")
    public void userMengetikKataKunciPencarian() {
        alarmPage.searchAlarmByKeyword(TestData.ALARM_SEARCH_KEYWORD);
        System.out.println("Mencari alarm dengan keyword: " + TestData.ALARM_SEARCH_KEYWORD);
    }

    @When("User mengklik tombol {string} pada baris tunggal data alarm")
    public void userMengklikTombolHapusPadaBarisTunggal(String btnName) {
        alarmPage.clickDeleteButtonByText(TestData.ALARM_TITLE);
        System.out.println("Klik tombol " + btnName + " pada alarm: " + TestData.ALARM_TITLE);
    }

    @When("User mencentang kotak checkbox pada beberapa baris data alarm di tabel")
    public void userMencentangKotakCheckboxBulk() {
        alarmPage.checkMultipleAlarmsInTable();
        System.out.println("Menceklis beberapa alarm");
    }

    @And("User mengklik tombol {string} di bagian atas tabel")
    public void userMengklikTombolUtamaMultiDelete(String btnName) {
        alarmPage.clickMultiDeleteButton();
        System.out.println("Klik tombol: " + btnName);
    }


    @And("User mengonfirmasi tindakan penghapusan pada dialog konfirmasi")
    public void userMengonfirmasiTindakanPenghapusan() {
        alarmPage.confirmDelete();
        System.out.println("Konfirmasi penghapusan");
    }

    @And("User mengonfirmasi tindakan penghapusan massal")
    public void userMengonfirmasiTindakanPenghapusanMassal() {
        alarmPage.confirmDelete();
        System.out.println("Konfirmasi penghapusan massal");
    }

    @Then("Jendela pop-up simulasi uji alert harus menampilkan status {string}")
    public void jendelaPopUpSimulasiUjiAlertHarusMenampilkanStatus(String expectedStatus) {
        String actualStatus = alarmPage.getUjiAlertStatusText();
        Assert.assertTrue(
                "Status uji alert salah! Expected: '" + expectedStatus + "', Actual: '" + actualStatus + "'",
                actualStatus.contains(expectedStatus)
        );
        System.out.println("Status uji alert: " + actualStatus);
    }

    @Then("Sistem harus menutup modal utama dan menampilkan alarm baru di tabel")
    public void sistemHarusMenampilkanAlarmBaruDiTabel() {
        boolean isVisible = alarmPage.isAlarmTextVisibleInTable(TestData.ALARM_TITLE);
        Assert.assertTrue(
                "Alarm '" + TestData.ALARM_TITLE + "' tidak ditemukan di tabel!",
                isVisible
        );
        System.out.println("Alarm '" + TestData.ALARM_TITLE + "' berhasil ditampilkan di tabel");
    }

    @Then("Sistem harus berhasil memperbarui konfigurasi data alarm tersebut")
    public void sistemHarusBerhasilMemperbaruiKonfigurasiDataAlarm() {
        boolean isVisible = alarmPage.isAlarmTextVisibleInTable(TestData.ALARM_EDIT_TITLE);
        Assert.assertTrue(
                "Alarm '" + TestData.ALARM_EDIT_TITLE + "' tidak ditemukan di tabel!",
                isVisible
        );
        System.out.println("Alarm berhasil diupdate ke: " + TestData.ALARM_EDIT_TITLE);
    }

    @Then("Tabel daftar alarm hanya menampilkan baris data sesuai kata kunci")
    public void tabelDaftarAlarmHanyaMenampilkanBarisDataSesuaiKataKunci() {
        boolean isVisible = alarmPage.isAlarmTextVisibleInTable(TestData.ALARM_SEARCH_KEYWORD);
        Assert.assertTrue(
                "Alarm dengan keyword '" + TestData.ALARM_SEARCH_KEYWORD + "' tidak ditemukan!",
                isVisible
        );
        System.out.println("Pencarian berhasil, alarm ditemukan");
    }


    @Then("Baris data alarm tersebut harus menghilang dari tabel utama")
    public void barisDataAlarmTersebutHarusMenghilangDariTabelUtama() {
        boolean isStillVisible = alarmPage.waitUntilAlarmDisappears(TestData.ALARM_TITLE);
        Assert.assertFalse(
                "Alarm '" + TestData.ALARM_TITLE + "' masih ada di tabel!",
                isStillVisible
        );
        System.out.println("Alarm '" + TestData.ALARM_TITLE + "' berhasil dihapus");
    }

    @Then("Semua baris data alarm yang dicentang harus berhasil dihapus dari tabel")
    public void semuaBarisDataAlarmYangDicentangBerhasilDihapus() {
        boolean isStillVisible = alarmPage.waitUntilAlarmDisappears(TestData.ALARM_TITLE);
        Assert.assertFalse(
                "Alarm yang dihapus masih ditemukan di tabel!",
                isStillVisible
        );
        System.out.println("Semua alarm yang dipilih berhasil dihapus");
    }
}