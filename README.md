#  UXTrace Automation Testing — Modul Alarm

Branch automation testing untuk modul **Alarm / Alert** pada sistem **UXTrace** — platform monitoring dan alerting sistem website KUR Jogja.

>  Dzakiya Hakima Adila  
>  Branch: `feature/alert-testing`  


## 📁 Struktur File Modul Alarm

```
uxtrace-automation-testing/
├── src/
│   └── test/
│       ├── java/
│       │   ├── defs/
│       │   │   ├── AlarmSteps.java     # Step definitions semua scenario alarm
│       │   │   └── Hooks.java          # Setup browser & login, teardown
│       │   ├── pages/
│       │   │   ├── BasePage.java       # Fondasi: driver & wait diwarisi semua page
│       │   │   └── AlarmPage.java      # Semua interaksi UI halaman alarm
│       │   ├── runner/
│       │   │   └── TestRunner.java     # Entry point eksekusi test
│       │   └── utils/
│       │       └── TestData.java       # Centralized test data & BASE_URL
│       └── resources/
│           └── features/
│               └── 02_alarm.feature    # Skenario BDD modul alarm
└── pom.xml
```

---

##  Skenario Pengujian

File: `src/test/resources/features/02_alarm.feature`

| Tag | Skenario | Status |
|-----|----------|--------|
| `@Alarm_Create` | Membuat alarm baru dengan simulasi uji alert terlebih dahulu | ✅ PASSED |
| `@Alarm_Edit` | Mengubah data konfigurasi alarm melalui verifikasi uji alert | ✅ PASSED |
| `@Alarm_Search` | Memfilter data alarm menggunakan kolom pencarian | ✅ PASSED |
| `@Alarm_Delete_Single` | Menghapus satu data alarm dari tabel utama | ✅ PASSED |
| `@Alarm_Delete_Bulk` | Menghapus beberapa data alarm sekaligus secara massal | ✅ PASSED |

### Detail Skenario

**Scenario 1 — Create Alarm** `@Alarm_Create`
```
1. Klik tombol "Tambah Alarm" → modal form terbuka
2. Isi form alarm (nama, query, pesan, interval, telegram)
3. Klik "Kirim Uji Alert" → tunggu response server
4. Verifikasi pop-up menampilkan status "Berhasil"
5. Tutup pop-up → klik "Simpan"
6. Verifikasi alarm baru muncul di tabel
```

**Scenario 2 — Edit Alarm** `@Alarm_Edit`
```
1. Klik tombol "Edit" pada baris alarm "Alert Click"
2. Ubah semua field dengan data baru
3. Klik "Kirim Uji Alert" → tunggu response server
4. Verifikasi pop-up menampilkan status "Berhasil"
5. Tutup pop-up → klik "Simpan"
6. Verifikasi alarm terupdate menjadi "Alert Click Updated" di tabel
```

**Scenario 3 — Search Alarm** `@Alarm_Search`
```
1. Ketik kata kunci pada search bar
2. Tekan Enter
3. Verifikasi tabel hanya menampilkan hasil sesuai keyword
```

**Scenario 4 — Delete Single** `@Alarm_Delete_Single`
```
1. Klik tombol "Hapus" pada baris alarm target
2. Konfirmasi pada dialog yang muncul
3. Verifikasi alarm sudah tidak ada di tabel
```

**Scenario 5 — Bulk Delete** `@Alarm_Delete_Bulk`
```
1. Centang checkbox pada 2 baris alarm
2. Klik tombol "Hapus" di bagian atas tabel
3. Konfirmasi pada dialog yang muncul
4. Verifikasi semua alarm yang dicentang sudah terhapus
```


### TestData.java — Data yang Digunakan
| Konstanta | Nilai | Keterangan |
|-----------|-------|------------|
| `BASE_URL` | `http://localhost:5173` | URL aplikasi lokal |
| `ALARM_TITLE` | `Alert Click` | Nama alarm saat create |
| `ALARM_QUERY` | `SELECT * FROM events WHERE...` | Query trigger alarm |
| `ALARM_MESSAGE` | `Pesan pemicu click button terdeteksi` | Pesan notifikasi |
| `ALARM_INTERVAL` | `5` | Interval pengecekan (menit) |
| `ALARM_TELEGRAM` | `1484397336` | ID Telegram tujuan |
| `ALARM_SEARCH_KEYWORD` | `Alert Click` | Keyword pencarian |
| `ALARM_EDIT_TITLE` | `Alert Click Updated` | Nama alarm setelah edit |
| `ALARM_EDIT_INTERVAL` | `10` | Interval baru setelah edit |

---

##  Arsitektur Kode

```
02_alarm.feature
│   Skenario ditulis dalam bahasa Gherkin
│   Bisa dipahami tanpa perlu tahu kode Java
│
└── AlarmSteps.java
│   Jembatan antara Gherkin dan Java
│   Berisi @Given @When @And @Then
│   Delegasi semua aksi ke AlarmPage
│
└── AlarmPage.java
│   Representasi halaman Alarm di UI
│   Menyimpan semua selector elemen (By.id, By.xpath, By.cssSelector)
│   Berisi semua method interaksi: klik, isi form, cek tabel, dll
│
└── BasePage.java
│   Fondasi semua page object
│   Menyediakan WebDriver dan WebDriverWait (15 detik)
│   Di-extend oleh AlarmPage
│
└── Hooks.java
│   @Before(order=1): buka Chrome, login otomatis
│   @Before(order=2): inisialisasi AlarmPage (di AlarmSteps)
│   @After: tutup browser setelah tiap scenario
│
└── TestData.java
    Semua data test terpusat di sini
    Jika data berubah, cukup update file ini
```
