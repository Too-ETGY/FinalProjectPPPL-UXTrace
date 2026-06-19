sebelum semua skenario jalan, perlu setup dulu
1. setup chrome --> buka fullscreen, cegah crash ram, izinkan koneksi
                --> buat instance browser chrome baru, jadi tiap skeanrio buka browser baru jalan dari nol
                --> kalau findelements ga nemu, atau timeout dia bakalan otomatis nge retry 10 detik, ini berlaku untuk global semua findelemen di seluruh test
                --> eksplisit wait ada di lokal, dengan timeout selama 15 detik.
2. Login
   --> buka base url, tunggu sampai url bener2 mengandung endpoint /login dan pastikan halaman benar2 ter load
   --> jeda 2 detik
3. Debug print semua input 
    --> cari semua elemen <input> di halaman by tagname "input" (cari berdasarkan tag html)
4. Isi email - find by xpath
    --> cari elemen <input> yang punya atribut type email atau test.
    --> ambil yang pertama ditemukan. Hapus teks lama kl ada ketik email
5. Isi password - Find by xpath
    -->cari input type dengan atribut password. Hapus teks sebelumnya, isi
6. Klik tombol login
7. Tunggu redirect 
    --> not maksudnya itu sampai url tidak mengandung /login lagi.

## BACKGROUND STEP
Given User sudah login ke platform UXTrace menggunakan akun valid
--> cek dan pastikan driver tidak null, kl null akan lempar expectation pesan tsb
And User menavigasi ke halaman Alarm Management
--> navigasi ke base url, ga klik menu karena lebih cepat dan reliale gitu deh

## STEP 1 -- KLIK TOMBOL TAMBAH ALARM
When User mengklik tombol "Tambah Alarm" untuk membuka form modal
@When("User mengklik tombol {string} untuk membuka form modal")
public void userMengklikTombolCreateAlarm(String btnName) {
        alarmPage.clickCreateAlarmButton();
--> ini hanya untuk logging, bukan untuk cari elemen. Logika sebenarnya ada di clickcreatrealarmbutton()

Menggunakan 2 locator.
- private By btnCreateAlarm = By.xpath("//button[contains(text(), 'Tambah Alarm')]");
- private By inputAlarmName = By.id("alarm-title");

function clickcretaealarmbutton
    --> pakai wait.until biar eksekusi bisa stop ketika kondisi tdk terpenuhi atau timeout
    --> elementtobeclockable : ada di DOM, terlihat, bisa diklik
    --> .click() lgsg dipanggil dr hasil wait

    --> setelah klik, tunggu modal muncul.
    --> visibilityofelementlocated = elemen ada di DOM

## STEP 2 -- ISI FORM ALARM
And User mengisi form alarm dengan data valid

//alarmsteps.java --> Isi semua data yg udah di state di test data, jadi nggak ada yang hardcoded di sini

//alarmpage.java --> aku langsung lookup pakai index id nya
--> tunggu form modal emang visible sebelum diisi, kl lgsg findelement tanpa wait bisa jadi elementnotinteractableexception
--> driver.findelement = cari satu elemeny yakni input alarm name.
--> hapus teks kl sebelumnya ada, dan isi dengan data yg ada di file testdata

## STEP 3 -- KIRIM UJI ALERT
And User mengklik tombol "Kirim Uji Alert" pada form modal

@Before(order=1) Hooks.setUp()
├── Buka Chrome
├── Buka /login
├── Find By xpath → input[type='text'][1] → isi email
├── Find By xpath → input[type='password'] → isi password
├── Find By xpath → button[type='submit'] → klik
└── Wait until URL not contains '/login'
↓
@Before(order=2) AlarmSteps.initPage()
└── new AlarmPage(Hooks.driver)
↓
Background
├── Given → cek driver not null
└── And → driver.get('/alarm')
↓
Step 1: clickCreateAlarmButton()
├── Wait elementToBeClickable → Find By xpath button 'Tambah Alarm' → click()
└── Wait visibilityOf → Find By id 'alarm-title'
↓
Step 2: fillAlarmFormComplete()
├── Wait visibilityOf inputAlarmName
├── Find By id 'alarm-title' → clear() → sendKeys("Alert Click")
├── Find By id 'alarm-query' → clear() → sendKeys(query)
├── Find By id 'alarm-message' → clear() → sendKeys(message)
├── Find By id 'alarm-interval' → clear() → sendKeys("5")
└── Find By id 'alarm-telegram' → clear() → sendKeys("1484397336")
↓
Step 3: clickKirimUjiAlert()
├── Wait elementToBeClickable → Find By xpath //dialog//button 'Kirim Uji Alert' → click()
└── Thread.sleep(2000) → tunggu response server
↓
Step 4: getUjiAlertStatusText() + Assert.assertTrue ← ASSERTION 1
├── Thread.sleep(1000)
├── Wait visibilityOf → Find By xpath //dialog//p 'Berhasil'/'Gagal'
├── Find By xpath → getText() → "Berhasil"
└── Assert.assertTrue("Berhasil".contains("Berhasil")) → ✅ PASSED
↓
Step 5: clickCloseUjiAlert()
├── Wait elementToBeClickable → Find By xpath //dialog//button 'Selesai' → click()
└── Thread.sleep(500)
↓
Step 6: clickSimpanAlarm()
├── Wait elementToBeClickable → Find By xpath //dialog//button 'Simpan' → click()
└── Thread.sleep(2000) → tunggu save + tabel refresh
↓
Step 7: isAlarmTextVisibleInTable() + Assert.assertTrue ← ASSERTION 2
├── Thread.sleep(1000)
├── findElements By cssSelector 'tbody tr' → List<WebElement>
├── Loop tiap row → row.getText().contains("Alert Click")
└── Assert.assertTrue(isVisible = true) → ✅ PASSED
↓
@After Hooks.tearDown()
└── driver.quit()


@Before(order=1) → Chrome baru → login ulang
@Before(order=2) → new AlarmPage(Hooks.driver)
Background       → cek driver → navigasi ke /alarm
↓
Step 1: clickEditButtonByText("Alert Click")
├── Wait elementToBeClickable
├── Find By xpath → //tr[contains(.,'Alert Click')]//button[contains(text(),'Edit')]
├── click() → modal edit terbuka, field sudah terisi data lama
└── Wait visibilityOf → By.id "alarm-title"
↓
Step 2: fillAlarmFormComplete(EDIT data)
├── Wait visibilityOf inputAlarmName
├── Find By id 'alarm-title' → clear() → sendKeys("Alert Click Updated")
├── Find By id 'alarm-query' → clear() → sendKeys(EDIT_QUERY)
├── Find By id 'alarm-message' → clear() → sendKeys("Pesan Notifikasi Hasil Update")
├── Find By id 'alarm-interval' → clear() → sendKeys("10")
└── Find By id 'alarm-telegram' → clear() → sendKeys("1484397336")
↓
Step 3: clickKirimUjiAlert()
├── Wait elementToBeClickable → Find By xpath //dialog//button 'Kirim Uji Alert'
├── click() → hit API dengan EDIT_QUERY
└── Thread.sleep(2000) → tunggu response
↓
Step 4: getUjiAlertStatusText() + Assert.assertTrue ← ASSERTION 1
├── Thread.sleep(1000)
├── Wait visibilityOf → dialogTestResult
├── Find By xpath → textUjiAlertStatus → getText() → "Berhasil"
└── Assert.assertTrue("Berhasil".contains("Berhasil")) → ✅ PASSED
↓
Step 5: clickCloseUjiAlert()
├── Wait elementToBeClickable → Find By xpath //dialog//button 'Selesai'
├── click()
└── Thread.sleep(500)
↓
Step 6: clickSimpanAlarm()
├── Wait elementToBeClickable → Find By xpath //dialog//button 'Simpan'
├── click() → POST ke server, simpan data edit
└── Thread.sleep(2000) → tunggu save + tabel refresh
↓
Step 7: isAlarmTextVisibleInTable("Alert Click Updated") + Assert ← ASSERTION 2
├── Thread.sleep(1000)
├── findElements By cssSelector 'tbody tr'
├── loop rows → row.getText().contains("Alert Click Updated")
└── Assert.assertTrue(isVisible = true) → ✅ PASSED
↓
@After → driver.quit()


## SKENARIO 3 -- SEARCH ALARM

@Alarm_Search
Scenario: Memfilter data alarm secara dinamis menggunakan kolom pencarian
When User mengetik kata kunci pencarian pada search bar alarm
Then Tabel daftar alarm hanya menampilkan baris data sesuai kata kunci

@Before(order=1) → Chrome baru → login ulang
@Before(order=2) → new AlarmPage(Hooks.driver)
Background       → cek driver → navigasi ke /alarm
↓
Step 1: searchAlarmByKeyword("Alert Click")
├── Wait visibilityOfElementLocated
│   └── Find By xpath → //input[@placeholder='Cari alarm anda...']
├── searchBox.clear()
│   └── hapus teks lama di search bar kalau ada
├── searchBox.sendKeys("Alert Click")
│   └── ketik keyword ke search bar
├── searchBox.sendKeys(Keys.ENTER)
│   └── tekan Enter untuk trigger search
└── Thread.sleep(1500)
└── tunggu hasil filter muncul di tabel
↓
Step 2: isAlarmTextVisibleInTable("Alert Click") + Assert.assertTrue ← ASSERTION
├── Thread.sleep(1000)
├── findElements By cssSelector → "tbody tr"
│   └── ambil semua baris tabel yang tampil setelah filter
├── loop tiap row
│   └── row.getText().contains("Alert Click")
│       → kalau ketemu → return true
│       → StaleElementReferenceException → continue
└── Assert.assertTrue(isVisible = true) → ✅ PASSED
↓
@After → driver.quit()


## SCENARIO 4 -- DELETE SINGLE ALARM
@Alarm_Delete_Single
Scenario: Menghapus satu data alarm secara langsung dari tabel utama
When User mengklik tombol "Hapus" pada baris tunggal data alarm
And User mengonfirmasi tindakan penghapusan pada dialog konfirmasi
Then Baris data alarm tersebut harus menghilang dari tabel utama

@Before(order=1) → Chrome baru → login ulang
@Before(order=2) → new AlarmPage(Hooks.driver)
Background       → cek driver → navigasi ke /alarm
↓
Step 1: clickDeleteButtonByText("Alert Click")
├── Wait elementToBeClickable
│   └── Find By xpath →
│       "//tr[contains(., 'Alert Click')]//button[contains(text(), 'Hapus')]"
│       ├── //tr[contains(., 'Alert Click')] → filter baris yang ada "Alert Click"
│       └── //button[contains(text(), 'Hapus')] → klik tombol Hapus di baris itu
└── deleteBtn.click()
└── dialog konfirmasi hapus muncul
↓
Step 2: confirmDelete()
├── Thread.sleep(500)
│   └── jeda singkat tunggu dialog animasi muncul
├── Wait visibilityOfElementLocated
│   └── Find By xpath → "//dialog[contains(@class, 'rounded-2xl')]"
│       └── pastikan dialog konfirmasi benar-benar visible
├── Wait elementToBeClickable
│   └── Find By xpath → "//dialog//button[contains(text(), 'Hapus')]"
│       ├── //dialog → pastikan tombol ada di dalam dialog
│       └── bukan tombol Hapus di tabel, tapi di dalam dialog konfirmasi
├── confirmBtn.click()
│   └── klik konfirmasi → request DELETE ke server
├── Wait invisibilityOfElementLocated → dialogConfirmDelete
│   └── tunggu dialog menutup setelah konfirmasi
│   └── dibungkus try-catch karena dialog mungkin sudah hilang sebelum wait
└── Thread.sleep(3000)
└── tunggu server proses delete + tabel refresh
↓
Step 3: waitUntilAlarmDisappears("Alert Click") + Assert.assertFalse ← ASSERTION
├── driver.navigate().refresh()
│   └── refresh halaman untuk pastikan data terbaru dari server
├── Thread.sleep(2000)
│   └── tunggu halaman selesai load setelah refresh
├── wait.until(driver -> { ... }) ← CUSTOM WAIT / LAMBDA
│   ├── ini bukan ExpectedConditions biasa
│   ├── driver -> { } = lambda function, cek kondisi sendiri
│   ├── List<WebElement> rows = driver.findElements(tableAlarmRows)
│   │   └── ambil semua baris tabel terbaru
│   ├── loop tiap row
│   │   ├── row.getText().contains("Alert Click")
│   │   │   └── kalau MASIH ADA → return false → wait retry lagi
│   │   └── StaleElementReferenceException
│   │       └── return true → baris sudah hilang dari DOM
│   └── kalau loop selesai tanpa ketemu → return true → kondisi terpenuhi
├── return false → "Alert Click" tidak ada = sudah terhapus
└── Assert.assertFalse(isStillVisible = false) → ✅ PASSED
↓
@After → driver.quit()


## SCENARIO 5 -- BULK DELETE ALARM
@Alarm_Delete_Bulk
Scenario: Menghapus beberapa data alarm sekaligus secara massal
When User mencentang kotak checkbox pada beberapa baris data alarm di tabel
And User mengklik tombol "Hapus" di bagian atas tabel
And User mengonfirmasi tindakan penghapusan massal
Then Semua baris data alarm yang dicentang harus berhasil dihapus dari tabel

@Before(order=1) → Chrome baru → login ulang
@Before(order=2) → new AlarmPage(Hooks.driver)
Background       → cek driver → navigasi ke /alarm
↓
Step 1: checkMultipleAlarmsInTable()
├── Thread.sleep(1000)
│   └── tunggu tabel load sempurna
├── JavascriptExecutor → "window.scrollTo(0, 0)"
│   └── scroll ke atas supaya checkbox baris pertama terlihat
├── Thread.sleep(500)
├── Wait presenceOfAllElementsLocatedBy
│   └── Find By cssSelector → "tbody input[type='checkbox']"
│       ├── tbody → hanya checkbox di dalam tabel body
│       └── input[type='checkbox'] → semua checkbox di tabel
│       └── return List<WebElement> semua checkbox
├── checkboxes.size() >= 2 ?
│   ├── YA → lanjut centang 2 checkbox
│   │   ├── JavascriptExecutor → scrollIntoView(checkboxes.get(0))
│   │   │   └── scroll sampai checkbox index 0 terlihat di viewport
│   │   ├── Thread.sleep(300)
│   │   ├── checkboxes.get(0).click() → centang baris pertama
│   │   ├── JavascriptExecutor → scrollIntoView(checkboxes.get(1))
│   │   ├── Thread.sleep(300)
│   │   └── checkboxes.get(1).click() → centang baris kedua
│   └── TIDAK → print "Tidak cukup checkbox" → test akan gagal di step berikutnya
├── JavascriptExecutor → "window.scrollTo(0, document.body.scrollHeight)"
│   └── scroll ke bawah supaya tombol bulk delete terlihat
└── Thread.sleep(1000)
↓
Step 2: clickMultiDeleteButton()
├── JavascriptExecutor → "window.scrollTo(0, document.body.scrollHeight)"
│   └── scroll ke bawah lagi (double scroll untuk pastikan)
├── Thread.sleep(500)
├── Wait elementToBeClickable
│   └── Find By xpath →
│       "//button[contains(@class, 'bg-error-icon') and contains(text(), 'Hapus')]"
│       ├── contains(@class, 'bg-error-icon') → tombol dengan class error/merah
│       └── contains(text(), 'Hapus') → teksnya mengandung "Hapus"
│           └── hasilnya: tombol "Hapus (2)" yang muncul setelah centang
├── print getText() → "Hapus (2)"
│   └── (2) = jumlah item yang dicentang
└── deleteBtn.click()
└── dialog konfirmasi bulk delete muncul
↓
Step 3: confirmDelete()
├── Thread.sleep(500)
├── Wait visibilityOfElementLocated
│   └── Find By xpath → "//dialog[contains(@class, 'rounded-2xl')]"
│       └── dialog konfirmasi muncul
├── Wait elementToBeClickable
│   └── Find By xpath → "//dialog//button[contains(text(), 'Hapus')]"
│       └── tombol konfirmasi di dalam dialog
├── confirmBtn.click()
│   └── request DELETE massal ke server
├── Wait invisibilityOfElementLocated → dialogConfirmDelete
│   └── tunggu dialog menutup, dibungkus try-catch
└── Thread.sleep(3000)
└── tunggu server proses delete 2 item + tabel refresh
↓
Step 4: waitUntilAlarmDisappears("Alert Click") + Assert.assertFalse ← ASSERTION
├── driver.navigate().refresh()
│   └── refresh untuk pastikan data terbaru
├── Thread.sleep(2000)
├── wait.until(driver -> { ... }) ← CUSTOM LAMBDA WAIT
│   ├── findElements By cssSelector → "tbody tr"
│   ├── loop tiap row
│   │   ├── row.getText().contains("Alert Click")
│   │   │   └── masih ada → return false → retry
│   │   └── StaleElementReferenceException → return true
│   └── tidak ketemu di semua row → return true → selesai
├── return false → sudah tidak ada
└── Assert.assertFalse(isStillVisible = false) → ✅ PASSED
↓
@After → driver.quit()

## CATATAN 1 - FIND BY STRATEGY

By.id
→ cari elemen berdasarkan attribute id="..."
→ PALING CEPAT karena browser punya index khusus untuk id
→ PALING RELIABLE karena id harusnya unik per halaman
→ dipakai untuk: semua input field di form modal
→ contoh:
By.id("alarm-title")    → <input id="alarm-title">
By.id("alarm-query")    → <input id="alarm-query">
By.id("alarm-interval") → <input id="alarm-interval">

By.xpath
→ cari elemen berdasarkan struktur/posisi di HTML
→ paling FLEKSIBEL, bisa cari berdasarkan teks, attribute, posisi, relasi
→ dipakai untuk: tombol dengan teks, elemen di dalam dialog, baris tabel spesifik
→ contoh:
By.xpath("//button[contains(text(), 'Tambah Alarm')]")
By.xpath("//dialog//button[contains(text(), 'Simpan')]")
By.xpath("//tr[contains(., 'Alert Click')]//button[contains(text(), 'Edit')]")

By.cssSelector
→ cari elemen pakai syntax CSS
→ lebih SINGKAT dari xpath untuk struktur sederhana
→ tidak bisa cari berdasarkan teks (kelemahan vs xpath)
→ dipakai untuk: baris tabel, checkbox di tabel
→ contoh:
By.cssSelector("tbody tr")
By.cssSelector("tbody input[type='checkbox']")

By.tagName
→ cari elemen berdasarkan nama tag HTML
→ paling UMUM, biasanya return banyak elemen
→ dipakai untuk: debug (ambil semua input), fallback klik body
→ contoh:
By.tagName("input") → semua <input> di halaman
By.tagName("body")  → elemen <body>, selalu ada 1

## CATATAN 2 - XPATH SYNTAX YANG DIPAKAI
// (double slash)
→ cari di MANA SAJA dalam dokumen, tidak harus langsung child
→ contoh: //button = cari semua <button> di seluruh halaman

/ (single slash)
→ cari langsung child dari elemen sebelumnya
→ contoh: dialog/button = <button> yang langsung di dalam <dialog>

. (titik)
→ "node saat ini beserta seluruh isinya"
→ contoh: //tr[contains(., 'Alert Click')]
→ artinya: <tr> yang di dalamnya (termasuk child) ada teks "Alert Click"

text()
→ teks langsung dari elemen, tidak termasuk child
→ contoh: //button[contains(text(), 'Simpan')]
→ artinya: <button> yang teks langsungnya mengandung "Simpan"

@ (at sign)
→ menunjuk attribute HTML
→ contoh: //input[@type='password']
→ artinya: <input> yang punya attribute type="password"

contains()
→ cek apakah string mengandung substring
→ lebih robust dari = karena tidak perlu exact match
→ contoh: contains(text(), 'Hapus') → cocok untuk "Hapus", "Hapus (2)", dll

and / or
→ kombinasi kondisi dalam filter XPath
→ contoh:
[@type='email' or @type='text']   → salah satu terpenuhi
[contains(@class,'bg-error-icon') and contains(text(),'Hapus')] → keduanya harus terpenuhi

[1]
→ ambil elemen pertama dari hasil pencarian
→ contoh: //input[@type='text'][1] → input text pertama yang ketemu

| (pipe)
→ OR antar selector XPath yang berbeda
→ contoh:
//button[@type='submit'] | //button[contains(text(), 'Masuk')]
→ coba selector pertama, kalau tidak ketemu coba berikutnya

## CATATAN 3 - WAIT STRATEGY
presenceOfElementLocated(By)
→ tunggu sampai elemen ADA di DOM
→ belum tentu visible/terlihat user
→ cocok untuk: elemen yang ada di HTML tapi mungkin tersembunyi
→ dipakai di: Hooks (email field, password field)

presenceOfAllElementsLocatedBy(By)
→ sama seperti presence tapi return List<WebElement>
→ tunggu sampai MINIMAL 1 elemen ketemu
→ cocok untuk: ambil semua elemen sekaligus (checkbox list)
→ dipakai di: checkMultipleAlarmsInTable() Scenario 5

visibilityOfElementLocated(By)
→ tunggu sampai elemen ADA di DOM + TERLIHAT user
→ display bukan none, opacity > 0, ukuran > 0
→ cocok untuk: modal, dialog, pop-up yang muncul dengan animasi
→ dipakai di: setelah klik Tambah Alarm, setelah klik Edit,
cek dialog konfirmasi, cek pop-up hasil uji alert

elementToBeClickable(By)
→ tunggu sampai elemen visible + ENABLED (bisa diklik)
→ paling KETAT dari semua wait
→ cocok untuk: semua tombol sebelum diklik
→ dipakai di: semua klik tombol di seluruh scenario

invisibilityOfElementLocated(By)
→ tunggu sampai elemen TIDAK TERLIHAT / hilang dari DOM
→ kebalikan dari visibilityOfElementLocated
→ cocok untuk: tunggu dialog menutup setelah konfirmasi
→ dipakai di: confirmDelete() setelah klik tombol hapus

urlContains(String)
→ tunggu sampai URL mengandung string tertentu
→ cocok untuk: verifikasi navigasi/redirect berhasil
→ dipakai di: Hooks setelah login

not(ExpectedConditions)
→ negasi dari kondisi apapun
→ tunggu sampai kondisi TIDAK terpenuhi
→ contoh: not(urlContains("/login")) = tunggu sampai URL bukan /login lagi
→ dipakai di: Hooks setelah klik tombol login

wait.until(driver -> { ... }) ← CUSTOM LAMBDA WAIT
→ buat kondisi wait sendiri yang tidak ada di ExpectedConditions
→ return true = kondisi terpenuhi, berhenti wait
→ return false = belum, retry lagi sampai timeout
→ cocok untuk: kondisi kompleks seperti tunggu baris tabel hilang
→ dipakai di: waitUntilAlarmDisappears() Scenario 4 & 5

## CATATAN 4 - 3 TIPE TIMEOUT DAN WAIT STARTEGY DI SELENIUM
implicitlyWait(Duration)
→ set di: driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))
→ berlaku GLOBAL untuk semua findElement()
→ kalau elemen tidak ketemu, otomatis retry sampai 10 detik
→ set sekali, berlaku selamanya untuk session itu
→ dipakai di: Hooks.setUp()

pageLoadTimeout(Duration)
→ set di: driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30))
→ kalau halaman tidak selesai load dalam 30 detik → TimeoutException
→ cocok untuk: antisipasi halaman lambat load
→ dipakai di: Hooks.setUp()

WebDriverWait (explicit wait)
→ new WebDriverWait(driver, Duration.ofSeconds(15))
→ berlaku untuk kondisi SPESIFIK yang didefinisikan
→ lebih fleksibel dan powerful dari implicitlyWait
→ timeout 15 detik per kondisi
→ dipakai di: BasePage (wait field), Hooks (wait lokal)

Perbandingan:
implicitlyWait  → global, hanya untuk findElement
pageLoadTimeout → global, hanya untuk page load
WebDriverWait   → per kondisi, paling fleksibel
Thread.sleep    → bukan wait Selenium, block semua eksekusi sekian ms

## CATATAN 5 - THREAD.SLEEP VS EXPLICIT WAIT
Thread.sleep(ms) ← cara lama, kurang ideal
→ SELALU buang waktu sebanyak ms yang ditentukan
→ tidak peduli apakah kondisi sudah terpenuhi atau belum
→ contoh: Thread.sleep(2000) = selalu tunggu 2 detik
→ kapan tetap dipakai di kode ini:
- setelah klik API (Kirim Uji Alert, Simpan) → tunggu response server
- setelah sendKeys ENTER (search) → tunggu filter
- setelah klik close dialog → tunggu animasi
  → total sleep di Scenario 5: ~8.6 detik

wait.until(ExpectedConditions) ← cara modern, lebih baik
→ berhenti SEGERA setelah kondisi terpenuhi
→ retry setiap 500ms sampai timeout (15 detik)
→ kalau kondisi terpenuhi di detik ke-2 → lanjut di detik ke-2
→ tidak buang waktu
→ lebih reliable karena tidak tergantung estimasi waktu

Idealnya:
Thread.sleep → diganti explicit wait semua
Tapi untuk project ini Thread.sleep dipertahankan karena:
→ lebih simple untuk dipahami
→ cukup reliable untuk test local

## CATATAN 6 - JAVASCRIPT EXECUTOR
Kapan HARUS pakai JavascriptExecutor?
→ Selenium tidak bisa interaksi dengan elemen di luar viewport
→ elemen ada tapi tidak bisa diklik karena tertutup/di luar layar

3 penggunaan di kode ini:

1. Scroll ke posisi tertentu
   ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)");
   → scroll ke atas (x=0, y=0)
   ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
   → scroll ke paling bawah halaman

2. Scroll sampai elemen terlihat
   ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
   → arguments[0] = elemen yang dikirim sebagai parameter
   → scrollIntoView(true) = scroll elemen ke bagian atas viewport
   → dipakai untuk checkbox yang mungkin di luar layar

3. Casting ke JavascriptExecutor
   (JavascriptExecutor) driver
   → driver aslinya adalah WebDriver
   → WebDriver tidak punya method executeScript()
   → casting ke JavascriptExecutor untuk akses method itu
   → ChromeDriver implements keduanya, jadi casting aman

## CATATAN 7 - FINDELEMENT VS FINDELEMENTS
driver.findElement(By)
→ cari SATU elemen
→ kalau tidak ketemu → throw NoSuchElementException
→ kalau ketemu lebih dari satu → return yang PERTAMA
→ return tipe: WebElement
→ dipakai untuk: semua interaksi dengan elemen tunggal

driver.findElements(By)
→ cari SEMUA elemen yang cocok
→ kalau tidak ketemu → return List KOSONG (tidak throw exception)
→ return tipe: List<WebElement>
→ dipakai untuk:
- ambil semua baris tabel (tableAlarmRows)
- ambil semua checkbox (checkboxAlarmRows)
- debug print semua input di Hooks

Contoh penggunaan:
// findElement → langsung interaksi
driver.findElement(By.id("alarm-title")).sendKeys("Alert Click");

// findElements → perlu loop atau get(index)
List<WebElement> rows = driver.findElements(tableAlarmRows);
for (WebElement row : rows) { ... }         // loop semua
checkboxes.get(0).click();                  // akses by index

## CATATAN 8 - CLEAR() + SENDKEYS()
Kenapa selalu clear() dulu sebelum sendKeys()?

Scenario Create:
→ field kosong → clear() tidak berbahaya tapi tidak perlu
→ tapi tetap dipakai untuk konsistensi

Scenario Edit:
→ field SUDAH TERISI data lama dari database
→ tanpa clear() → sendKeys() APPEND ke teks yang ada
→ contoh: field "Alert Click" + sendKeys("Alert Click Updated")
→ hasilnya: "Alert ClickAlert Click Updated" → SALAH!
→ dengan clear() → hapus dulu → sendKeys() → "Alert Click Updated" → BENAR

Kenapa tidak pakai CTRL+A lalu Delete?
→ clear() lebih simple dan cross-platform
→ CTRL+A shortcut bisa berbeda di Mac vs Windows

## CATATAN 9 - STALE ELEMENT REFERENCE EXCEPTION
Apa itu Stale Element?
→ elemen yang sudah di-find/disimpan sebagai WebElement
→ tiba-tiba TIDAK VALID karena DOM berubah

Kapan terjadi?
→ halaman di-refresh
→ komponen React/Vue re-render setelah data berubah
→ animasi yang hapus dan buat ulang elemen

Contoh di kode:
List<WebElement> rows = driver.findElements(tableAlarmRows);
// ↑ simpan referensi semua baris

// ... tabel refresh karena save alarm ...

for (WebElement row : rows) {
row.getText(); // ← StaleElementReferenceException!
// referensi row sudah tidak valid karena DOM berubah
}

Cara handle di kode ini:
try {
if (row.getText().contains(expectedText)) {
return true;
}
} catch (StaleElementReferenceException e) {
continue; // skip baris ini, lanjut ke baris berikutnya
}

Kenapa continue bukan throw?
→ baris lain mungkin masih valid
→ kalau throw → seluruh loop berhenti
→ dengan continue → skip baris bermasalah, tetap cek baris lain

## CATATAN 10 - ASSERT.ASSERTTRUE VS ASSERT.ASSERTFALSE
Assert.assertTrue(pesanError, kondisi)
→ kondisi harus bernilai TRUE supaya PASSED
→ kalau FALSE → throw AssertionError dengan pesanError
→ dipakai untuk verifikasi data HARUS ADA
→ dipakai di: Scenario 1, 2, 3

Scenario 1: Assert.assertTrue("tidak ditemukan!", isVisible)
→ isVisible harus true = "Alert Click" ada di tabel

Scenario 2: Assert.assertTrue("tidak ditemukan!", isVisible)
→ isVisible harus true = "Alert Click Updated" ada di tabel

Scenario 3: Assert.assertTrue("tidak ditemukan!", isVisible)
→ isVisible harus true = hasil search "Alert Click" ada

Assert.assertFalse(pesanError, kondisi)
→ kondisi harus bernilai FALSE supaya PASSED
→ kalau TRUE → throw AssertionError dengan pesanError
→ dipakai untuk verifikasi data TIDAK ADA / SUDAH TERHAPUS
→ dipakai di: Scenario 4, 5

Scenario 4: Assert.assertFalse("masih ada!", isStillVisible)
→ isStillVisible harus false = "Alert Click" sudah tidak ada

Scenario 5: Assert.assertFalse("masih ada!", isStillVisible)
→ isStillVisible harus false = "Alert Click" sudah tidak ada

Tabel ringkas:
Scenario 1 Create → assertTrue  → data HARUS ADA    ✅
Scenario 2 Edit   → assertTrue  → data baru HARUS ADA ✅
Scenario 3 Search → assertTrue  → hasil HARUS ADA    ✅
Scenario 4 Delete → assertFalse → data TIDAK BOLEH ADA ✅
Scenario 5 Bulk   → assertFalse → data TIDAK BOLEH ADA ✅


## CATATAN 11 - STEP DEFINITION REUSE
Beberapa step definition dipakai di LEBIH DARI SATU scenario:

1. fillAlarmFormComplete() → Scenario 1, 2, 4(sebelumnya), 5(sebelumnya)
   → method sama, data berbeda (ALARM_* vs ALARM_EDIT_*)

2. clickKirimUjiAlert() → Scenario 1, 2
   → dipanggil dari 2 step definition berbeda:
   "pada form modal" → Scenario 1
   "pada form edit modal" → Scenario 2
   → kalimat Gherkin beda, method Java sama

3. getUjiAlertStatusText() + Assert.assertTrue → Scenario 1, 2
   → step definition SAMA PERSIS dipakai ulang:
   "Jendela pop-up simulasi uji alert harus menampilkan status {string}"

4. clickSimpanAlarm() → Scenario 1, 2
   → dipanggil dari 2 step definition berbeda:
   "mengonfirmasi pembuatan" → Scenario 1
   "mengonfirmasi pembaruan" → Scenario 2

5. confirmDelete() → Scenario 4, 5
   → step definition berbeda tapi method sama:
   "pada dialog konfirmasi" → Scenario 4
   "penghapusan massal" → Scenario 5

6. isAlarmTextVisibleInTable() → Scenario 1, 2, 3
7. waitUntilAlarmDisappears() → Scenario 4, 5

Prinsip: DRY (Don't Repeat Yourself)
→ tulis method sekali, pakai berkali-kali
→ kalau UI berubah, cukup update di satu tempat

## CATATAN 12 - LIFECYCLE EKSEKUSI PER SCENARIO
Urutan eksekusi SETIAP scenario (tanpa terkecuali):

1. @Before(order=1) Hooks.setUp()
   → buka Chrome baru
   → buka /login
   → isi email + password
   → klik login
   → tunggu redirect dari /login

2. @Before(order=2) AlarmSteps.initPage()
   → new AlarmPage(Hooks.driver)
   → driver sudah siap dari step 1

3. Background
   → Given: cek driver not null
   → And: navigasi ke /alarm

4. Scenario Steps
   → When/And/Then sesuai scenario

5. @After Hooks.tearDown()
   → driver.quit() → tutup browser + kill ChromeDriver

Artinya:
→ 5 scenario = 5x buka Chrome = 5x login
→ setiap scenario INDEPENDEN dari sisi browser
→ tapi TIDAK independen dari sisi data (Scenario 2 butuh data dari Scenario 1)

## CATATAN 13 - KELEMAHAN DAN POTENSI IMPROVEMENT
1. Banyak Thread.sleep()
   Masalah: buang waktu, tidak adaptive
   Solusi: ganti dengan explicit wait yang sesuai

2. Test dependency antar scenario
   Masalah: Scenario 2 butuh data dari Scenario 1
   Scenario 4 & 5 butuh data yang sudah ada di lokal
   Solusi ideal: tiap scenario setup datanya sendiri via API call
   bukan via UI (lebih cepat dan reliable)

3. static WebDriver di Hooks
   Masalah: tidak aman untuk parallel test
   Solusi: pakai ThreadLocal<WebDriver>
   ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
   → tiap thread punya instance driver sendiri

4. Hardcoded credential di Hooks
   Masalah: email & password visible di kode
   Solusi: pindah ke file .properties atau environment variable
   String email = System.getenv("TEST_EMAIL");

5. Tidak ada screenshot saat gagal
   Masalah: susah debug kalau test gagal
   Solusi: tambah di @After:
   if (scenario.isFailed()) {
   byte[] screenshot = ((TakesScreenshot) driver)
   .getScreenshotAs(OutputType.BYTES);
   scenario.attach(screenshot, "image/png", "screenshot");
   }
