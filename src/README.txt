# OrangeHRM & API Test Otomasyon Framework

Bu proje, Java ile Selenium ve TestNG kullanılarak geliştirilmiş bir test otomasyon framework'üdür. 
Hem Web arayüzü testleri hem de REST API testleri içerir.

## 🔍 Test Edilen Alanlar

### Web UI Testleri
- Site: https://opensource-demo.orangehrmlive.com/web/index.php/auth/login
- Test Senaryoları:
  - Geçerli kullanıcı ile başarılı giriş (valid login)
  - Geçersiz kullanıcı ile hatalı giriş (invalid login)
  - Kullanıcı ekleme, düzenleme ve doğrulama

### API Testleri
- Gorest API (`GET`): https://gorest.co.in/public/v1
- JsonPlaceholder API (`POST`): https://jsonplaceholder.typicode.com
- POJO sınıfları kullanılarak JSON dönüşümleri kontrol edildi.

## 🛠️ Kullanılan Teknolojiler

| Teknoloji         | Açıklama                                      |
|------------------|-----------------------------------------------|
| Java             | Otomasyon dili                                |
| Selenium WebDriver | UI otomasyonu için                          |
| TestNG           | Test koşumu ve yönetimi                       |
| RestAssured      | API testleri için                             |
| Faker            | Sahte veri üretimi                            |
| ExtentReports    | Test sonuçlarının raporlanması                |
| Maven            | Proje yapı ve bağımlılık yönetimi             |
| IntelliJ IDEA    | Geliştirme ortamı                             |

## 📁 Proje Yapısı

```
remwaste/
├── api/
│   └── apiTests/...
├── driver/
│   └── DriverManager.java
├── methods/
│   ├── BasePage.java
│   └── BaseTest.java
├── pages/
│   ├── orangehrm/
│   │   ├── constants/
│   │   └── enums/
│   └── tests/
│       ├── AdminTests.java
│       └── LoginTests.java
```

## ▶️ Testleri Çalıştırma

```bash
mvn clean test
```

ExtentReports çıktısı: `target/Rapor` klasöründe HTML olarak oluşturulur.

## 📝 Notlar

- Web testlerinde Page Object Model yapısı benimsenmiştir.
- BaseTest ve DriverManager sınıfları ile merkezi yapı sağlanmıştır.
- API testleri POJO sınıfları ile güçlü şekilde kontrol edilmiştir.
