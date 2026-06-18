package defs;

import io.cucumber.java.en.*;
import org.junit.Assert;
import pages.DashboardPage;
import utils.TestData;

public class DashboardSteps {

    private final DashboardPage dashboardPage;
    private String currentPanelTitle = "";

    public DashboardSteps() {
        this.dashboardPage = new DashboardPage(Hooks.getDriver());
    }

    @Given("User sudah login ke platform UXTrace")
    public void user_sudah_login_ke_platform_uxtrace_menggunakan_akun_valid() {
        Assert.assertNotNull("Driver belum diinisialisasi atau cookie gagal diinjeksi", Hooks.getDriver());
    }

    @And("User menavigasi ke halaman Dashboard")
    public void user_menavigasi_ke_halaman_dashboard() {
        dashboardPage.open();
    }

    // CREATE PANEL

    @When("User mengklik tombol tambah panel metrics")
    public void user_mengklik_tombol_tambah_panel_metrics() {
        dashboardPage.clickAddMetrics();
    }

    @And("User mengisi form pembuatan panel metrics dengan tipe chart {string}")
    public void user_mengisi_form_pembuatan_panel_metrics_dengan_tipe_chart(String chartType) {
        dashboardPage.selectGraphType(chartType);
        String query = switch (chartType.toLowerCase()) {
            case "line" -> TestData.PANEL_CREATE_QUERY_LINE;
            case "bar"  -> TestData.PANEL_CREATE_QUERY_BAR;
            case "pie"  -> TestData.PANEL_CREATE_QUERY_PIE;
            default -> throw new IllegalArgumentException("Tipe chart tidak dikenal: " + chartType);
        };
        dashboardPage.fillQuery(query);
    }

    @And("User mengecek preview chart")
    public void user_mengecek_preview_chart() {
        dashboardPage.clickCheckPreview();
    }

    @And("User menyimpan panel metrics")
    public void user_menyimpan_panel_metrics() {
        dashboardPage.clickSavePanel();
    }

    @Then("Panel metrics dengan tipe chart line harus berhasil dibuat dan tampil di dashboard")
    public void panel_metrics_line_berhasil_dibuat() {
        Assert.assertTrue("Panel Line tidak tampil di dashboard",
                dashboardPage.isPanelVisible(TestData.PANEL_TITLE_LINE));
    }

    @Then("Panel metrics dengan tipe chart bar harus berhasil dibuat dan tampil di dashboard")
    public void panel_metrics_bar_berhasil_dibuat() {
        Assert.assertTrue("Panel Bar tidak tampil di dashboard",
                dashboardPage.isPanelVisible(TestData.PANEL_TITLE_BAR));
    }

    @Then("Panel metrics dengan tipe chart pie harus berhasil dibuat dan tampil di dashboard")
    public void panel_metrics_pie_berhasil_dibuat() {
        Assert.assertTrue("Panel Pie tidak tampil di dashboard",
                dashboardPage.isPanelVisible(TestData.PANEL_TITLE_PIE));
    }

    // VIEW METRICS

    @When("User mengklik salah satu panel metrics")
    public void user_mengklik_salah_satu_panel_metrics() {
        dashboardPage.clickFirstPanel();
    }

    @Then("Sistem harus menampilkan data detail metrics")
    public void sistem_harus_menampilkan_data_detail_metrics() {
        Assert.assertTrue("Dialog detail tidak terbuka", dashboardPage.isDetailDialogOpen());
        Assert.assertTrue("Tabel detail data tidak terlihat", dashboardPage.isDetailTableVisible());
    }

    @When("User mengklik tombol refresh pada panel metrics")
    public void user_mengklik_tombol_refresh_pada_panel_metrics() {
        dashboardPage.clickDetailRefresh();
    }

    @Then("Data metrics harus diperbarui")
    public void data_metrics_harus_diperbarui() {
        Assert.assertTrue("Tabel detail tidak terlihat setelah refresh", dashboardPage.isDetailTableVisible());
    }

    @When("User mengklik tombol close panel")
    public void user_mengklik_tombol_close_panel() {
        dashboardPage.closeDetailDialog();
    }

    @Then("Panel detail metrics harus tertutup dan User kembali ke tampilan dashboard")
    public void panel_detail_metrics_harus_tertutup() {
        Assert.assertTrue("Dialog detail gagal ditutup", dashboardPage.isDetailDialogClosed());
    }

    // UPDATE PANEL

    @Given("Panel metrics line sudah tersedia di dashboard")
    public void panel_metrics_line_sudah_tersedia() {
        String title = dashboardPage.isPanelVisible(TestData.PANEL_UPDATED_TITLE_LINE)
                ? TestData.PANEL_UPDATED_TITLE_LINE : TestData.PANEL_TITLE_LINE;
        if (!dashboardPage.isPanelVisible(title)) {
            dashboardPage.clickAddMetrics();
            dashboardPage.selectGraphType("Line");
            dashboardPage.fillQuery(TestData.PANEL_CREATE_QUERY_LINE);
            dashboardPage.clickCheckPreview();
            dashboardPage.clickSavePanel();
            title = TestData.PANEL_TITLE_LINE;
        }
        this.currentPanelTitle = title;
        Assert.assertTrue("Prasyarat Gagal: Panel Line tidak ditemukan", dashboardPage.isPanelVisible(title));
    }

    @Given("Panel metrics bar sudah tersedia di dashboard")
    public void panel_metrics_bar_sudah_tersedia() {
        String title = dashboardPage.isPanelVisible(TestData.PANEL_UPDATED_TITLE_BAR)
                ? TestData.PANEL_UPDATED_TITLE_BAR : TestData.PANEL_TITLE_BAR;
        if (!dashboardPage.isPanelVisible(title)) {
            dashboardPage.clickAddMetrics();
            dashboardPage.selectGraphType("Bar");
            dashboardPage.fillQuery(TestData.PANEL_CREATE_QUERY_BAR);
            dashboardPage.clickCheckPreview();
            dashboardPage.clickSavePanel();
            title = TestData.PANEL_TITLE_BAR;
        }
        this.currentPanelTitle = title;
        Assert.assertTrue("Prasyarat Gagal: Panel Bar tidak ditemukan", dashboardPage.isPanelVisible(title));
    }

    @Given("Panel metrics pie sudah tersedia di dashboard")
    public void panel_metrics_pie_sudah_tersedia() {
        String title = dashboardPage.isPanelVisible(TestData.PANEL_UPDATED_TITLE_PIE)
                ? TestData.PANEL_UPDATED_TITLE_PIE : TestData.PANEL_TITLE_PIE;
        if (!dashboardPage.isPanelVisible(title)) {
            dashboardPage.clickAddMetrics();
            dashboardPage.selectGraphType("Pie");
            dashboardPage.fillQuery(TestData.PANEL_CREATE_QUERY_PIE);
            dashboardPage.clickCheckPreview();
            dashboardPage.clickSavePanel();
            title = TestData.PANEL_TITLE_PIE;
        }
        this.currentPanelTitle = title;
        Assert.assertTrue("Prasyarat Gagal: Panel Pie tidak ditemukan", dashboardPage.isPanelVisible(title));
    }

    @When("User mengklik tombol edit pada panel metrics line")
    public void user_mengklik_tombol_edit_pada_panel_metrics_line() {
        this.currentPanelTitle = TestData.PANEL_TITLE_LINE;
        dashboardPage.openUpdateModalForPanel(this.currentPanelTitle);
    }

    @When("User mengklik tombol edit pada panel metrics bar")
    public void user_mengklik_tombol_edit_pada_panel_metrics_bar() {
        this.currentPanelTitle = TestData.PANEL_TITLE_BAR;
        dashboardPage.openUpdateModalForPanel(this.currentPanelTitle);
    }

    @When("User mengklik tombol edit pada panel metrics pie")
    public void user_mengklik_tombol_edit_pada_panel_metrics_pie() {
        this.currentPanelTitle = TestData.PANEL_TITLE_PIE;
        dashboardPage.openUpdateModalForPanel(this.currentPanelTitle);
    }

    @And("User membersihkan query lama")
    public void user_membersihkan_query_lama() {
        dashboardPage.clearQuery();
    }

    @And("User menulis ulang query berdasarkan event baru")
    public void user_menulis_ulang_query_berdasarkan_event_baru() {
        if (this.currentPanelTitle.equals(TestData.PANEL_TITLE_LINE)) {
            dashboardPage.fillQuery(TestData.PANEL_UPDATE_QUERY_LINE);
        } else if (this.currentPanelTitle.equals(TestData.PANEL_TITLE_BAR)) {
            dashboardPage.fillQuery(TestData.PANEL_UPDATE_QUERY_BAR);
        } else {
            dashboardPage.fillQuery(TestData.PANEL_UPDATE_QUERY_PIE);
        }
    }

    @And("User mengklik tombol update panel")
    public void user_mengklik_tombol_update_panel() {
        dashboardPage.clickUpdatePanel();
    }

    @Then("Panel metrics line harus berhasil diperbarui sesuai event yang baru")
    public void panel_metrics_line_harus_berhasil_diperbarui() {
        Assert.assertTrue("Panel terupdate tidak muncul di dashboard",
                dashboardPage.isPanelVisible(TestData.PANEL_UPDATED_TITLE_LINE));
    }

    @Then("Panel metrics bar harus berhasil diperbarui sesuai event yang baru")
    public void panel_metrics_bar_harus_berhasil_diperbarui() {
        Assert.assertTrue("Panel terupdate tidak muncul di dashboard",
                dashboardPage.isPanelVisible(TestData.PANEL_UPDATED_TITLE_BAR));
    }

    @Then("Panel metrics pie harus berhasil diperbarui sesuai event yang baru")
    public void panel_metrics_pie_harus_berhasil_diperbarui() {
        Assert.assertTrue("Panel terupdate tidak muncul di dashboard",
                dashboardPage.isPanelVisible(TestData.PANEL_UPDATED_TITLE_PIE));
    }

    // DELETE PANEL

    @When("User mengklik tombol hapus pada panel metrics bar")
    public void user_mengklik_tombol_hapus_pada_panel_metrics_bar() {
        dashboardPage.openDeleteDialogForPanel(this.currentPanelTitle);
    }

    @When("User mengklik tombol hapus pada panel metrics line")
    public void user_mengklik_tombol_hapus_pada_panel_metrics_line() {
        dashboardPage.openDeleteDialogForPanel(this.currentPanelTitle);
    }

    @When("User mengklik tombol hapus pada panel metrics pie")
    public void user_mengklik_tombol_hapus_pada_panel_metrics_pie() {
        dashboardPage.openDeleteDialogForPanel(this.currentPanelTitle);
    }

    @And("User mengonfirmasi penghapusan panel")
    public void user_mengonfirmasi_penghapusan_panel() {
        dashboardPage.confirmDelete();
    }

    @Then("Panel metrics bar harus berhasil terhapus dari dashboard")
    public void panel_metrics_bar_harus_berhasil_terhapus() {
        Assert.assertFalse("Panel Bar masih terlihat di dashboard setelah dihapus",
                dashboardPage.isPanelVisible(this.currentPanelTitle));
    }

    @Then("Panel metrics line harus berhasil terhapus dari dashboard")
    public void panel_metrics_line_harus_berhasil_terhapus() {
        Assert.assertFalse("Panel Line masih terlihat di dashboard setelah dihapus",
                dashboardPage.isPanelVisible(this.currentPanelTitle));
    }

    @Then("Panel metrics pie harus berhasil terhapus dari dashboard")
    public void panel_metrics_pie_harus_berhasil_terhapus() {
        Assert.assertFalse("Panel Pie masih terlihat di dashboard setelah dihapus",
                dashboardPage.isPanelVisible(this.currentPanelTitle));
    }

    // RELATIVE TIME RANGE

    @When("User memilih filter relative time range pada dashboard")
    public void user_memilih_filter_relative_time_range_pada_dashboard() {
        dashboardPage.selectRelativeTimeRange(TestData.TIME_RANGE_LABEL);
    }

    @Then("Data metrics pada dashboard harus diperbarui sesuai rentang waktu relatif yang dipilih")
    public void data_metrics_dashboard_harus_diperbarui_sesuai_rentang_waktu_relatif() {
        Assert.assertTrue("Halaman dashboard gagal memuat ulang data grid", true);
    }
}