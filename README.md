#  UXTrace Automation Testing

Proyek automation testing untuk sistem **UXTrace** — platform monitoring dan alerting sistem website **KUR Jogja**. Dibangun menggunakan **Selenium WebDriver** dengan pola **Page Object Model (POM)** dan framework **Cucumber BDD**.

>  Mata Kuliah: Praktikum Pengujian Perangkat Lunak  
>  Universitas Gadjah Mada — Semester 4  
>  Repository: [Too-ETGY/FinalProjectPPPL-UXTrace](https://github.com/Too-ETGY/FinalProjectPPPL-UXTrace.git)  
>  Test Case Suite: [Notion — Test Case Management UXTrace](https://app.notion.com/p/Test-Case-Management-UXTrace-37197c0ec9cc80a78467e656a870e3e3)

---

##  Tim Pengembang

| Nama | NIM | Branch | Modul |
|------|-----|--------|-------|
| Dzakiya Hakima Adila | [NIM] | `feature/alert-testing` | Alarm / Alert |
| Rua Adelia | [NIM] | `feature/auth-testing`, `feature/console-testing` | Authentication, Console |
| Farid Ahmad Nur Rahman | [NIM] | `feature/dashboard` | Dashboard |
| Tegar Raditya Hikmawan | [NIM] | `feature/querylog` | Query Log |

---

##  Tech Stack

| Komponen | Teknologi | Versi |
|----------|-----------|-------|
| Bahasa | Java | 24 |
| Automation Framework | Selenium WebDriver | 4.21.0 |
| BDD Framework | Cucumber | 7.18 |
| Test Runner | JUnit | 4 |
| Build Tool | Maven | - |
| IDE | IntelliJ IDEA / VS Code | - |
| Browser | Google Chrome | Latest |
| Design Pattern | Page Object Model (POM) | - |

---

##  Struktur Project

```
uxtrace-automation-testing/
├── src/
│   └── test/
│       ├── java/
│       │   ├── defs/
│       │   │   ├── AlarmSteps.java       # Step definitions modul Alarm
│       │   │   ├── AuthSteps.java        # Step definitions modul Auth
│       │   │   ├── ConsoleSteps.java     # Step definitions modul Console
│       │   │   ├── DashboardSteps.java   # Step definitions modul Dashboard
│       │   │   ├── QueryLogSteps.java    # Step definitions modul Query Log
│       │   │   └── Hooks.java            # Setup & teardown (login, browser)
│       │   ├── pages/
│       │   │   ├── BasePage.java         # Fondasi semua page object
│       │   │   ├── AlarmPage.java        # Page object halaman Alarm
│       │   │   ├── LoginPage.java        # Page object halaman Login
│       │   │   ├── ConsolePage.java      # Page object halaman Console
│       │   │   ├── DashboardPage.java    # Page object halaman Dashboard
│       │   │   └── QueryLogPage.java     # Page object halaman Query Log
│       │   ├── runner/
│       │   │   └── TestRunner.java       # Entry point eksekusi test
│       │   └── utils/
│       │       └── TestData.java         # Centralized test data
│       └── resources/
│           └── features/
│               ├── 01_auth.feature       # Skenario modul Authentication
│               ├── 02_alarm.feature      # Skenario modul Alarm
│               ├── 03_console.feature    # Skenario modul Console
│               ├── 04_querylog.feature   # Skenario modul Query Log
│               └── 05_dashboard.feature  # Skenario modul Dashboard
└── pom.xml
```

---

##  Cakupan Pengujian

###  Modul Authentication (`01_auth.feature`)
| No | Skenario |
|----|----------|
| 1 | Flow Register akun baru |
| 2 | Flow Login dengan akun valid |

###  Modul Alarm (`02_alarm.feature`)
| No | Skenario |
|----|----------|
| 1 | Membuat alarm baru (kirim uji alert → simpan) |
| 2 | Mengedit alarm (kirim uji alert → simpan) |
| 3 | Menghapus satu alarm (single delete) |
| 4 | Menghapus beberapa alarm sekaligus (multi delete) |
| 5 | Mencari alarm berdasarkan kata kunci (search) |

###  Modul Console (`03_console.feature`)
| No | Skenario |
|----|----------|
| 1 | Filter data berdasarkan absolute time range |
| 2a | Search by event — single event |
| 2b | Search by event — multi event |
| 3 | Klik detail console |
| 4 | Remove filter event |
| 5 | Kombinasi filter tanggal + filter event |

###  Modul Dashboard (`05_dashboard.feature`)
| No | Skenario |
|----|----------|
| 1 | Create panel Line Metrics |
| 2 | Create panel Bar Metrics |
| 3 | Create panel Pie Metrics |
| 4 | View data metrics (klik panel, refresh, close) |
| 5 | Update panel Line (by event) |
| 6 | Update panel Bar (by event) |
| 7 | Update panel Pie (by event) |
| 8 | Delete panel Bar |
| 9 | Delete panel Line |
| 10 | Delete panel Pie |
| 11 | Filter relative time range |

###  Modul Query Log (`04_querylog.feature`)
| No | Skenario |
|----|----------|
| 1 | Search by page / button name |
| 2 | Search by event name |
| 3 | Kombinasi filter waktu + keyword search |
| 4 | Remove keyword setelah search |
| 5 | Klik detail query (sampai close window detail) |

---

##  Prerequisites

Sebelum menjalankan test, pastikan hal berikut sudah terpenuhi:

### 1. Aplikasi UXTrace Berjalan
Aplikasi UXTrace harus berjalan di lokal pada port `5173`:
```
http://localhost:5173
```
> Untuk setup dan konfigurasi aplikasi UXTrace, lihat dokumentasi development UXTrace.

### 2. Java 24
Pastikan Java Development Kit (JDK) 24 sudah terinstall:
```bash
java -version
# output: java version "24" ...
```

### 3. Maven
```bash
mvn -version
# output: Apache Maven ...
```

### 4. Google Chrome
Pastikan Google Chrome sudah terinstall. ChromeDriver akan otomatis dikelola oleh Selenium Manager (Selenium 4.21+), tidak perlu install manual.

### 5. IntelliJ IDEA / VS Code
Pastikan sudah terinstall plugin/extension:
- **IntelliJ**: Cucumber for Java, Gherkin
- **VS Code**: Cucumber (Gherkin) Full Support

---

##  Cara Menjalankan Test

>  **Penting**: Setiap modul berada di branch yang berbeda. Pindah ke branch yang sesuai sebelum menjalankan test.

### Langkah 1 — Pindah ke Branch yang Sesuai

```bash
# Modul Alarm
git checkout feature/alert-testing

# Modul Authentication & Console
git checkout feature/auth-testing
git checkout feature/console-testing

# Modul Dashboard
git checkout feature/dashboard

# Modul Query Log
git checkout feature/querylog
```

### Langkah 2 — Pilih Cara Run

####  Cara A — Via IntelliJ IDEA (Direkomendasikan)

**Run seluruh modul:**
1. Buka file `src/test/java/runner/TestRunner.java`
2. Klik kanan pada file → **Run 'TestRunner'**
3. Hasil test akan tampil di panel Run

**Run satu scenario:**
1. Buka file `.feature` yang diinginkan di `src/test/resources/features/`
2. Klik ikon ▶️ di sebelah kiri baris `Scenario` yang ingin dijalankan
3. Atau klik kanan pada nama scenario → **Run Scenario**


### Langkah 3 — Lihat Laporan Test

Setelah test selesai, laporan otomatis dibuat di:
```
target/cucumber-reports/
├── alarm-test-report.html     # Laporan HTML (buka di browser)
└── cucumber.json              # Laporan JSON (untuk CI/CD)
```

Buka file `.html` di browser untuk melihat hasil test secara visual.

---

##  Daftar Tag

| Tag | Keterangan |
|-----|------------|
| `@Alarm` | Semua scenario modul Alarm |
| `@Auth` | Semua scenario modul Authentication |
| `@Dashboard` | Semua scenario modul Dashboard |
| `@Console` | Semua scenario modul Console |
| `@QueryLog` | Semua scenario modul Query Log |

---

##  Arsitektur

Project ini menggunakan pola **Page Object Model (POM)** dikombinasikan dengan **Behavior Driven Development (BDD)**:

```
Feature File (.feature)         ← ditulis dalam bahasa Gherkin
        ↓
Step Definitions (*Steps.java)  ← jembatan Gherkin ke Java
        ↓
Page Objects (*Page.java)       ← representasi halaman UI
        ↓
BasePage.java                   ← fondasi driver & wait
        ↓
Selenium WebDriver              ← interaksi langsung ke browser
```

### Penjelasan Layer

**Feature File** — Skenario test ditulis dalam bahasa natural (Gherkin) sehingga bisa dipahami semua pihak tanpa perlu memahami kode.

**Step Definitions** — Menghubungkan kalimat Gherkin ke method Java. Berisi logika assertion dan delegasi ke Page Object.

**Page Objects** — Menyimpan semua selector elemen dan method interaksi UI. Jika UI berubah, cukup update di sini tanpa menyentuh step definitions.

**BasePage** — Menyediakan `WebDriver` dan `WebDriverWait` yang diwarisi semua page object.

**Hooks** — Mengelola lifecycle test: buka browser, login, dan tutup browser untuk setiap scenario.

**TestData** — Menyimpan semua data test secara terpusat. Jika data berubah, cukup update di satu file.

---

##  Hal yang Perlu Diperhatikan

**Data Test Lokal**
Beberapa scenario (terutama delete) bergantung pada data yang sudah ada di aplikasi lokal. Pastikan data yang diperlukan sudah ada sebelum menjalankan test.

**Urutan Scenario**
Beberapa scenario dalam satu modul memiliki ketergantungan urutan. Jalankan seluruh modul sekaligus via `TestRunner`, bukan satu per satu secara acak.

**Kredensial Login**
Kredensial login tersimpan di `Hooks.java`. Sesuaikan dengan akun yang tersedia di environment lokal masing-masing.

**Port Aplikasi**
Pastikan aplikasi UXTrace berjalan di `http://localhost:5173` sebelum menjalankan test. Jika port berbeda, update `BASE_URL` di `TestData.java`.

---

##  Laporan Hasil Demo

Saat demo, setiap anggota tim menjalankan `TestRunner` masing-masing dari branch yang sesuai. Urutan demo:

| Urutan | Anggota | Branch | Modul |
|--------|---------|--------|-------|
| 1 | Rua Adelia | `feature/auth-testing` | Authentication |
| 2 | Dzakiya Hakima Adila | `feature/alert-testing` | Alarm |
| 3 | Rua Adelia | `feature/console-testing` | Console |
| 4 | Tegar Raditya Hikmawan | `feature/querylog` | Query Log |
| 5 | Farid Ahmad Nur Rahman | `feature/dashboard` | Dashboard |
