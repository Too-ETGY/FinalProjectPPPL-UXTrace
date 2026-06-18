Feature: Authentication

  Scenario: Login berhasil dengan kredensial valid
    Given User berada di halaman login
    When User memasukkan email dan password yang valid
    Then User berhasil masuk ke halaman dashboard

  Scenario: Register berhasil dengan data valid
    Given User berada di halaman register
    When User mengisi form register dengan data yang valid
    Then User berhasil diarahkan ke halaman login