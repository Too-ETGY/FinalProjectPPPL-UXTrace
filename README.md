# Tugas Besar Praktikum Pengujian Perangkat Lunak

Repositori ini digunakan untuk memenuhi Tugas Besar Praktikum Pengujian Perangkat Lunak. Kami melakukan pengujian *end-to-end* (E2E) pada platform **UXTrace**, sebuah sistem *dashboard analytics* dan *alert system tracking* yang digunakan untuk memantau website **KUR Jogja**.
> 📢 **Catatan:** Repositori ini akan terus diperbarui secara berkala seiring berjalannya progres proyek**.

---

## 👥 Anggota Kelompok & Pembagian Tugas

**Kelompok: Sesuai Kelompok PAD 2**

| Nama Anggota | Fitur / Alur yang Diuji | Tanggung Jawab Kode Pengujian |
| :--- | :--- | :--- |
| **Farid Ahmad Nur Rahman** | Fitur Autentikasi | Login/Logout, Validasi Akses Pengguna |
| **Rua Adelia** | Fitur Dashboard Panel | Visualisasi Data, Navigasi Metrik Analytics |
| **Tegar Raditya Hikmawan** | Fitur Query Log | Pencarian Log, Riwayat Query, Validasi Data Log |
| **Dzakiya Hakima Adila** | Fitur Alert | Konfigurasi Alert, Notifikasi Trigger System |

---

## 🎯 Target Pengujian (Scope)

* **Aplikasi Target:** UXTrace (Dashboard Analytics & Alert System - KUR Jogja)
* **Jenis Pengujian:** *End-to-End* (E2E) / *Minimum Viable Product* (MVP) User Flow
* **Cakupan Halaman (Minimal 5 Halaman):**
    1. Halaman Login / Autentikasi
    2. Halaman Dashboard Utama (Panel Analytics)
    3. Halaman Query Log Browser
    4. Halaman Pengaturan / Detail Alert
    5. Halaman Log Out / Konfirmasi Sesi

---

## 🛠️ Tech Stack & Arsitektur

Proyek pengujian ini dibangun menggunakan teknologi berikut:

* **Bahasa Pemrograman:** Java
* **Build Tool:** Apache Maven
* **Framework Pengujian:** Selenium WebDriver
* **Pendekatan BDD:** Cucumber (dengan Gherkin Syntax)
* **Design Pattern:** Page Object Model (POM) untuk efisiensi dan reusabilitas kode.

---

## 📑 Komponen Proyek Akhir

### 1. Test Suite & Desain Test Case
Penyusunan *test case* dilakukan dengan memanfaatkan metode pengujian formal seperti **Boundary Value Analysis (BVA)** dan **Equivalence Partitioning (EP)** untuk memastikan efektivitas *input validation*.
> *[link spreadsheet will be added later]*

### 2. Kode Pengujian (BDD & POM)
* Menggunakan syntax **Gherkin** (`.feature` files) untuk mendefinisikan skenario dalam bahasa alami.
* Pemisahan logika elemen web menggunakan **Page Object Model (POM)** untuk mempermudah *maintenance* kode jika terjadi perubahan elemen UI pada website UXTrace.

### 3. Bug Reporting
Setiap *bug* atau *defect* yang ditemukan selama proses pengujian dicatat secara terstruktur pada dokumentasi laporan bug kelompok kami.
> *[Link docs or github issues will be added later]*

### 4. Automated Report Generation (Bonus Feature)
Proyek ini telah dikonfigurasi untuk menghasilkan laporan pengujian secara otomatis (*automated generation of report*) setiap kali pengujian selesai dijalankan, menggunakan plugin *Cucumber HTML Reporter / Cluecumber*.

---

## 🚀 Cara Menjalankan Pengujian

### Prasyarat (Prerequisites)
* Java Development Kit (JDK) versi 11 atau yang terbaru.
* Apache Maven terinstall.
* Google Chrome / WebDriver yang sesuai.

### Langkah-Langkah

1. **Clone Repositori:**
   ```bash
   git clone [https://github.com/username/repository-name.git](https://github.com/username/repository-name.git)
   cd repository-name
2. **
