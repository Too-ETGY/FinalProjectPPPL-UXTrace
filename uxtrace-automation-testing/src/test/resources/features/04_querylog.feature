@QueryLog
Feature: Query Log Management Functional Testing
  Sebagai Admin UXTrace
  Aku ingin melacak, memfilter, dan memeriksa detail log kueri sistem
  Agar aku dapat memantau aktivitas kueri dan menganalisis performa sistem secara akurat

  Background: User sudah berada di halaman Query Log
    Given User sudah login ke platform UXTrace menggunakan akun valid
    And User menavigasi ke halaman Query Log

  Scenario: Memfilter data query log berdasarkan halaman atau nama tombol
    When User mengetik kata kunci halaman atau nama tombol pada kolom pencarian
    Then Tabel daftar log harus diperbarui dan hanya menampilkan data yang sesuai dengan halaman atau tombol tersebut

  Scenario: Memfilter data query log berdasarkan nama event
    When User mengetik kata kunci nama event pada kolom pencarian
    Then Tabel daftar log harus diperbarui dan hanya menampilkan data yang sesuai dengan nama event tersebut

  Scenario: Mengombinasikan filter rentang waktu dan pencarian kata kunci
    When User memilih rentang waktu tertentu pada filter waktu
    And User mengetik kata kunci spesifik pada kolom pencarian
    Then Tabel daftar log harus menampilkan data yang berada dalam rentang waktu dan mengandung kata kunci tersebut

  Scenario: Menghapus kata kunci pencarian untuk menampilkan kembali semua data
    Given User sudah melakukan pencarian menggunakan kata kunci tertentu
    When User membersihkan teks atau mengklik tombol hapus pada kolom pencarian
    Then Tabel daftar log harus direset dan menampilkan seluruh data kembali semula

  Scenario: Memeriksa detail log kueri spesifik hingga menutup jendela detail
    When User mengklik tombol atau ikon "Detail" pada salah satu baris log kueri
    Then Sistem harus menampilkan jendela pop-up yang berisi informasi detail dari kueri tersebut
    When User mengklik tombol "Tutup" pada jendela detail kueri
    Then Jendela detail kueri harus tertutup dan pengguna kembali ke tampilan tabel utama