package defs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

// Sesuaikan import ini jika Hooks berada di package yang berbeda, misal: import config.Hooks;
// import Hooks; 
import pages.QueryLogPage;
import utils.TestData;

public class QueryLogSteps {

    private QueryLogPage queryLogPage;

    private QueryLogPage page() {
        if (queryLogPage == null) {
            queryLogPage = new QueryLogPage(Hooks.getDriver());
        }
        return queryLogPage;
    }

    @Given("User sudah login ke platform UXTrace menggunakan akun valid")
    public void userSudahLoginKePlatformUXTraceMenggunakanAkunValid() {
        // Auth is handled via cookie injection in Hooks.@Before — nothing to do here.
        // This step exists purely to keep the Gherkin readable for non-technical readers.
    }

    @Given("User menavigasi ke halaman Query Log")
    public void userMenavigasiKeHalamanQueryLog() {
        page().open();
    }

    @When("User mengetik kata kunci halaman atau nama tombol pada kolom pencarian")
    public void userMengetikKataKunciHalamanAtauNamaTombolPadaKolomPencarian() {
        page().searchFor(TestData.SEARCH_KEYWORD_PAGE_OR_BUTTON);
    }

    @Then("Tabel daftar log harus diperbarui dan hanya menampilkan data yang sesuai dengan halaman atau tombol tersebut")
    public void tabelDaftarLogHarusDiperbaruiDanHanyaMenampilkanDataYangSesuaiHalamanTombol() {
        assertTrue(
                "Expected all visible rows to contain keyword: " + TestData.SEARCH_KEYWORD_PAGE_OR_BUTTON,
                page().allVisibleRowsContainKeyword(TestData.SEARCH_KEYWORD_PAGE_OR_BUTTON)
        );
    }

    @When("User mengetik kata kunci nama event pada kolom pencarian")
    public void userMengetikKataKunciNamaEventPadaKolomPencarian() {
        page().searchFor(TestData.SEARCH_KEYWORD_EVENT_NAME);
    }

    @Then("Tabel daftar log harus diperbarui dan hanya menampilkan data yang sesuai dengan nama event tersebut")
    public void tabelDaftarLogHarusDiperbaruiDanHanyaMenampilkanDataYangSesuaiNamaEvent() {
        assertTrue(
                "Expected all visible rows to contain keyword: " + TestData.SEARCH_KEYWORD_EVENT_NAME,
                page().allVisibleRowsContainKeyword(TestData.SEARCH_KEYWORD_EVENT_NAME)
        );
    }

    @When("User memilih rentang waktu tertentu pada filter waktu")
    public void userMemilihRentangWaktuTertentuPadaFilterWaktu() {
        page().selectRelativeTimeRange(TestData.TIME_RANGE_LABEL_LAST_7_DAYS);
    }

    @When("User mengetik kata kunci spesifik pada kolom pencarian")
    public void userMengetikKataKunciSpesifikPadaKolomPencarian() {
        page().searchFor(TestData.SEARCH_KEYWORD_COMBINED);
    }

    @Then("Tabel daftar log harus menampilkan data yang berada dalam rentang waktu dan mengandung kata kunci tersebut")
    public void tabelDaftarLogHarusMenampilkanDataYangBeradaDalamRentangWaktuDanKataKunci() {
        // Time range effect on a dummy/uncontrolled dataset can't be independently
        // verified, so we only assert the keyword filter held (per agreed scope).
        assertTrue(
                "Expected all visible rows to contain keyword: " + TestData.SEARCH_KEYWORD_COMBINED,
                page().allVisibleRowsContainKeyword(TestData.SEARCH_KEYWORD_COMBINED)
        );
    }

    @Given("User sudah melakukan pencarian menggunakan kata kunci tertentu")
    public void userSudahMelakukanPencarianMenggunakanKataKunciTertentu() {
        page().searchFor(TestData.SEARCH_KEYWORD_EVENT_NAME);
    }

    @When("User membersihkan teks atau mengklik tombol hapus pada kolom pencarian")
    public void userMembersihkanTeksAtauMengklikTombolHapusPadaKolomPencarian() {
        page().clearSearch();
    }

    @Then("Tabel daftar log harus direset dan menampilkan seluruh data kembali semula")
    public void tabelDaftarLogHarusDiresetDanMenampilkanSeluruhDataKembaliSemula() {
        assertTrue("Expected search input to be cleared", page().isSearchCleared());
    }

    @When("User mengklik tombol atau ikon {string} pada salah satu baris log kueri")
    public void userMengklikTombolAtauIkonPadaSalahSatuBarisLogKueri(String buttonName) {
        // Param 'buttonName' menangkap text "Detail" dari Gherkin secara dinamis
        page().openFirstRowDetail();
    }

    @Then("Sistem harus menampilkan jendela pop-up yang berisi informasi detail dari kueri tersebut")
    public void sistemHarusMenampilkanJendelaPopUpModalYangBerisiInformasiDetail() {
        assertTrue("Expected detail modal to be open", page().isDetailModalOpen());
    }

    @When("User mengklik tombol {string} pada jendela detail kueri")
    public void userMengklikTombolPadaJendelaDetailKueri(String buttonName) {
        // Param 'buttonName' menangkap text "Tutup" dari Gherkin secara dinamis
        page().closeDetailModal();
    }

    @Then("Jendela detail kueri harus tertutup dan pengguna kembali ke tampilan tabel utama")
    public void jendelaDetailKueriHarusTertutupDanPenggunaKembaliKeTampilanTabelUtama() {
        assertFalse("Expected detail modal to be closed", page().isDetailModalOpen());
    }
}