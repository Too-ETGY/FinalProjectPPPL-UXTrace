@Alarm
Feature: Alarm Management Functional Testing
  Sebagai Admin UXTrace
  Aku ingin mengelola konfigurasi alarm dan menguji fungsi pemicu alert
  Agar aku dapat memastikan sistem peringatan UI berfungsi dengan normal

  Background: User sudah berada di halaman Alarm Management
    Given User sudah login ke platform UXTrace menggunakan akun valid
    And User menavigasi ke halaman Alarm Management

  @Alarm_Create
  Scenario: Berhasil membuat alarm baru dengan melakukan simulasi uji alert terlebih dahulu
    When User mengklik tombol "Tambah Alarm" untuk membuka form modal
    And User mengisi form alarm dengan data valid
    And User mengklik tombol "Kirim Uji Alert" pada form modal
    Then Jendela pop-up simulasi uji alert harus menampilkan status "Berhasil"
    When User mengklik tombol "Selesai" untuk menutup jendela pop-up uji alert
    And User mengklik tombol "Simpan" untuk mengonfirmasi pembuatan
    Then Sistem harus menutup modal utama dan menampilkan alarm baru di tabel

  @Alarm_Edit
  Scenario: Berhasil mengubah data konfigurasi alarm aktif melalui verifikasi uji alert
    When User mengklik tombol "Edit" pada data alarm yang sudah ada
    And User mengubah data alarm dengan data baru
    And User mengklik tombol "Kirim Uji Alert" pada form edit modal
    Then Jendela pop-up simulasi uji alert harus menampilkan status "Berhasil"
    When User mengklik tombol "Selesai" untuk menutup jendela pop-up uji alert
    And User mengklik tombol "Simpan" untuk mengonfirmasi pembaruan
    Then Sistem harus berhasil memperbarui konfigurasi data alarm tersebut

  @Alarm_Search
  Scenario: Memfilter data alarm secara dinamis menggunakan kolom pencarian
    When User mengetik kata kunci pencarian pada search bar alarm
    Then Tabel daftar alarm hanya menampilkan baris data sesuai kata kunci

  # ✅ FIX: Tidak buat alarm baru, langsung hapus data yang sudah ada di lokal
  @Alarm_Delete_Single
  Scenario: Menghapus satu data alarm secara langsung dari tabel utama
    When User mengklik tombol "Hapus" pada baris tunggal data alarm
    And User mengonfirmasi tindakan penghapusan pada dialog konfirmasi
    Then Baris data alarm tersebut harus menghilang dari tabel utama

  # ✅ FIX: Tidak buat alarm baru, langsung centang data yang sudah ada di lokal
  @Alarm_Delete_Bulk
  Scenario: Menghapus beberapa data alarm sekaligus secara massal
    When User mencentang kotak checkbox pada beberapa baris data alarm di tabel
    And User mengklik tombol "Hapus" di bagian atas tabel
    And User mengonfirmasi tindakan penghapusan massal
    Then Semua baris data alarm yang dicentang harus berhasil dihapus dari tabel