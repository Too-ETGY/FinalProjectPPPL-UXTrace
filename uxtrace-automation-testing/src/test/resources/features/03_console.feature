@Console
Feature: Console Event Log Functional Testing
  Sebagai Admin UXTrace
  Aku ingin mencari dan melihat detail event log pada halaman Console
  Agar aku dapat memastikan fitur pencarian dan tampilan detail event berfungsi dengan normal

  Background: User sudah berada di halaman Console
    Given User menavigasi ke halaman Console

  Scenario: Berhasil memfilter event log menggunakan absolute time range
    When User membuka dropdown filter waktu
    And User mengisi start time dan end time pada absolute time range
    And User mengklik tombol Apply time range
    Then Daftar event log hanya menampilkan event sesuai rentang waktu yang dipilih

  Scenario: Berhasil mencari event log berdasarkan nama event tunggal
    When User mengetik nama event pada search bar Console
    Then Daftar event log hanya menampilkan event sesuai kata kunci pencarian

  Scenario: Berhasil mencari event log dengan beberapa filter event sekaligus
    When User menambahkan beberapa nama event sebagai filter pencarian
    Then Daftar event log menampilkan event yang sesuai dengan semua filter yang dipilih

  Scenario: Berhasil melihat detail event log setelah mengklik salah satu event
    When User mengklik salah satu event pada daftar event log
    Then Panel detail di sisi kanan harus menampilkan informasi JSON dari event tersebut

  Scenario: Berhasil menghapus filter event yang sudah diterapkan
    When User mengetik nama event pada search bar Console
    And User menghapus filter event yang sudah diterapkan
    Then Daftar event log menampilkan semua event tanpa filter

  Scenario: Berhasil mengkombinasikan filter tanggal dan filter event sekaligus
    When User membuka dropdown filter waktu
    And User mengisi start time dan end time pada absolute time range
    And User mengklik tombol Apply time range
    And User mengetik nama event pada search bar Console
    Then Daftar event log menampilkan event sesuai kombinasi filter tanggal dan event