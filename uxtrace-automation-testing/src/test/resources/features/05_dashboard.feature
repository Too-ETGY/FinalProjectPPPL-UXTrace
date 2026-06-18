@Dashboard
Feature: Dashboard Metrics Management Functional Testing
  Sebagai Admin UXTrace
  Aku ingin membuat, melihat, memperbarui, menghapus, dan memfilter panel metrics
  Agar aku dapat memantau data aktivitas pengguna dalam bentuk visualisasi dashboard

  Background: User sudah berada di halaman Dashboard
    Given User sudah login ke platform UXTrace
    And User menavigasi ke halaman Dashboard

  Scenario: Membuat panel metrics dengan tipe chart line
    When User mengklik tombol tambah panel metrics
    And User mengisi form pembuatan panel metrics dengan tipe chart "Line"
    And User mengecek preview chart
    And User menyimpan panel metrics
    Then Panel metrics dengan tipe chart line harus berhasil dibuat dan tampil di dashboard

  Scenario: Membuat panel metrics dengan tipe chart bar
    When User mengklik tombol tambah panel metrics
    And User mengisi form pembuatan panel metrics dengan tipe chart "Bar"
    And User mengecek preview chart
    And User menyimpan panel metrics
    Then Panel metrics dengan tipe chart bar harus berhasil dibuat dan tampil di dashboard

  Scenario: Membuat panel metrics dengan tipe chart pie
    When User mengklik tombol tambah panel metrics
    And User mengisi form pembuatan panel metrics dengan tipe chart "Pie"
    And User mengecek preview chart
    And User menyimpan panel metrics
    Then Panel metrics dengan tipe chart pie harus berhasil dibuat dan tampil di dashboard

  Scenario: Melihat data metrics pada panel dashboard
    When User mengklik salah satu panel metrics
    Then Sistem harus menampilkan data detail metrics
    When User mengklik tombol refresh pada panel metrics
    Then Data metrics harus diperbarui
    When User mengklik tombol close panel
    Then Panel detail metrics harus tertutup dan User kembali ke tampilan dashboard

  Scenario: Memperbarui panel metrics dengan tipe chart line berdasarkan event
    Given Panel metrics line sudah tersedia di dashboard
    When User mengklik tombol edit pada panel metrics line
    And User membersihkan query lama
    And User menulis ulang query berdasarkan event baru
    And User mengecek preview chart
    And User mengklik tombol update panel
    Then Panel metrics line harus berhasil diperbarui sesuai event yang baru

  Scenario: Memperbarui panel metrics dengan tipe chart bar berdasarkan event
    Given Panel metrics bar sudah tersedia di dashboard
    When User mengklik tombol edit pada panel metrics bar
    And User membersihkan query lama
    And User menulis ulang query berdasarkan event baru
    And User mengecek preview chart
    And User mengklik tombol update panel
    Then Panel metrics bar harus berhasil diperbarui sesuai event yang baru

  Scenario: Memperbarui panel metrics dengan tipe chart pie berdasarkan event
    Given Panel metrics pie sudah tersedia di dashboard
    When User mengklik tombol edit pada panel metrics pie
    And User membersihkan query lama
    And User menulis ulang query berdasarkan event baru
    And User mengecek preview chart
    And User mengklik tombol update panel
    Then Panel metrics pie harus berhasil diperbarui sesuai event yang baru

  Scenario: Menghapus panel metrics dengan tipe chart bar
    Given Panel metrics bar sudah tersedia di dashboard
    When User mengklik tombol hapus pada panel metrics bar
    And User mengonfirmasi penghapusan panel
    Then Panel metrics bar harus berhasil terhapus dari dashboard

  Scenario: Menghapus panel metrics dengan tipe chart line
    Given Panel metrics line sudah tersedia di dashboard
    When User mengklik tombol hapus pada panel metrics line
    And User mengonfirmasi penghapusan panel
    Then Panel metrics line harus berhasil terhapus dari dashboard

  Scenario: Menghapus panel metrics dengan tipe chart pie
    Given Panel metrics pie sudah tersedia di dashboard
    When User mengklik tombol hapus pada panel metrics pie
    And User mengonfirmasi penghapusan panel
    Then Panel metrics pie harus berhasil terhapus dari dashboard

  Scenario: Memfilter data dashboard menggunakan relative time range
    When User memilih filter relative time range pada dashboard
    Then Data metrics pada dashboard harus diperbarui sesuai rentang waktu relatif yang dipilih
