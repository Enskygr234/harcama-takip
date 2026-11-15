# harcama-takip

Proje bazlı gelir/gider takip uygulaması: bakiye özeti, kategoriye göre gider grafiği, işlem geçmişi ve günlük notlar. Veriler cihazda (localStorage) saklanır.

**Site:** https://enskygr234.github.io/harcama-takip/

## Özellikler

- **Projeler:** "1. İnşaat", "2. İnşaat" gibi ayrı projeler; her birinin kendi bakiyesi ve işlemleri, "Tümü" sekmesinde toplam
- **Günlük not:** her proje ve gün için ayrı not, otomatik kayıt, geçmiş notlar listesi
- **Telefona kurulum (PWA):** ana ekrana eklenir, internet olmadan çalışır

## Android uygulaması (APK)

**İndir:** https://github.com/Enskygr234/harcama-takip/releases/latest/download/harcama-takip.apk

Telefonda bağlantıyı açın, inen dosyaya dokunun; "bilinmeyen kaynaklardan yükleme" izni istenirse verin. Uygulama internetsiz çalışır, kayıtlar telefonda saklanır. Uygulamadaki kayıtlar, tarayıcıda siteye girdiğiniz kayıtlardan ayrıdır.

APK, `android/` klasöründeki küçük bir Android kabuğudur: depo kökündeki `index.html` dosyasını tam ekran açar. `apk-v*` etiketi gönderildiğinde GitHub Actions APK'yı derleyip Sürümler (Releases) sayfasına koyar. İmza anahtarı (`android/app/harcama-takip.jks`) bilerek depodadır; böylece her yeni sürüm eskisinin üstüne güncellenir ve kayıtlar silinmez.

## Çalıştırma

Kurulum gerektirmez. Çevrimdışı çalışma ve telefona kurulum için sayfanın bir sunucudan (ör. GitHub Pages) açılması gerekir; `index.html` dosyasını doğrudan açınca uygulamanın geri kalanı yine çalışır.

Bağımlılık yok: HTML + CSS + JavaScript.
