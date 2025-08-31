# 🖱️ Pasca AutoClicker

![Java](https://img.shields.io/badge/Java-11+-orange.svg)
![License](https://img.shields.io/badge/License-MIT-blue.svg)
![Platform](https://img.shields.io/badge/Platform-Windows%20%7C%20Linux%20%7C%20macOS-lightgrey.svg)
![Build](https://img.shields.io/badge/Build-Maven-red.svg)

**Aplikasi auto-clicker modern dengan performa tinggi, dan statistik real-time.**

## ✨ Fitur Utama

- 🎯 **Tahan untuk Klik**: Tahan tombol kiri mouse untuk mulai auto-click, lepas untuk berhenti
- ⚡ **Performa Tinggi**: Threading yang dioptimasi dengan timing presisi nanosecond
- 🎨 **UI Modern Gelap**: Interface yang elegan dengan statistik real-time
- 📊 **Statistik Real-time**: Monitor CPS langsung, penghitung klik, dan durasi sesi
- 🎲 **Kontrol Jitter**: Tambahkan randomisasi gerakan mouse yang natural
- 🇮🇩 **Interface Indonesia**: Sepenuhnya dalam Bahasa Indonesia
- 🔧 **CPS Dapat Disesuaikan**: Kecepatan klik dari 1-50 CPS
- 💾 **Multi-platform**: Bekerja di Windows, Linux, dan macOS

## 🚀 Memulai

### Unduh Versi Terbaru
1. Kunjungi halaman [Releases](../../releases)
2. Unduh `PascaAutoClicker.jar`
3. Klik dua kali untuk menjalankan (butuh Java 11+)

### Atau Build dari Source
```bash
git clone https://github.com/nathanpasca/pasca-autoclicker.git
cd pasca-autoclicker
mvn clean package
java -jar target/PascaAutoClicker.jar
```

## 📖 Cara Penggunaan

1. **Aktifkan AutoClicker**: Klik tombol hijau "AKTIFKAN AUTOCLICKER"
2. **Atur Kecepatan Klik**: Sesuaikan slider CPS ke kecepatan yang diinginkan
3. **Mulai Klik**: Tahan tombol kiri mouse di mana saja
4. **Berhenti Klik**: Lepas tombol kiri mouse
5. **Opsional**: Aktifkan jitter untuk gerakan mouse yang lebih natural

![Screenshot](screenshot.png)

## 🔧 Persyaratan Sistem

- **Java 11 atau lebih tinggi**
- **Windows 7+**, **Linux**, atau **macOS**
- **Hak akses Administrator** (untuk deteksi mouse global)

## 🛠️ Persyaratan Build

- Java 11+ JDK
- Maven 3.6+
- Git

## 📦 Dependencies

- [JNativeHook 2.2.2](https://github.com/kwhat/jnativehook) - Deteksi input global
- Java Swing - Antarmuka pengguna
- Java AWT Robot - Otomasi mouse

## 🏗️ Cara Build

```bash
# Clone repository
git clone https://github.com/yourusername/pasca-autoclicker.git
cd pasca-autoclicker

# Compile dan package
mvn clean package

# Jalankan
java -jar target/PascaAutoClicker.jar
```

### Perintah Maven
```bash
mvn clean compile          # Compile saja
mvn clean package          # Build JAR dengan dependencies
mvn clean test            # Jalankan test (jika ada)
mvn exec:java             # Jalankan dari source
```

## ⚙️ Konfigurasi

Aplikasi otomatis menyimpan pengaturan Anda:
- **Kecepatan CPS**: Kecepatan klik yang disukai
- **Pengaturan Jitter**: Konfigurasi gerakan natural
- **Posisi Window**: Mengingat posisi terakhir

## 🎯 Optimasi Performa

- **Threading prioritas tinggi** untuk klik yang akurat
- **Timing presisi nanosecond** untuk CPS yang konsisten
- **Tracking statistik hemat memori**
- **Update UI yang dioptimasi** (interval 500ms)
- **Manajemen resource pintar**

## 🐛 Troubleshooting

### Aplikasi Tidak Bisa Detect Mouse
- Jalankan sebagai Administrator/sudo
- Pastikan Java memiliki permission untuk input system
- Cek apakah antivirus memblokir aplikasi

### Klik Tidak Akurat
- Kurangi CPS jika terlalu tinggi (>30 CPS)
- Aktifkan jitter untuk gerakan yang lebih natural
- Pastikan tidak ada aplikasi lain yang menggunakan mouse hook

### Performa Lambat
- Tutup aplikasi lain yang berat
- Pastikan Java memiliki cukup memory
- Restart aplikasi jika sudah lama berjalan

## 🔒 Keamanan

- **Open Source**: Kode dapat diperiksa oleh semua orang
- **Tanpa Network**: Tidak ada koneksi internet yang diperlukan
- **Lokal**: Semua data tersimpan di komputer Anda
- **Aman**: Tidak mengumpulkan data pribadi

## 📝 Changelog

### v2.0.0 (Latest)
- ✨ UI modern dengan dark theme
- ⚡ Performa tinggi dengan precision timing
- 📊 Real-time statistics
- 🎲 Jitter control
- 🇮🇩 Interface Bahasa Indonesia
- 🔧 Improved compatibility (Java 8-21)

### v1.0.0
- 🎯 Basic hold-to-click functionality
- ⚙️ Simple CPS control
- 💻 Cross-platform support

## 🤝 Kontribusi

Kontribusi sangat diterima! Ikuti langkah berikut:

1. **Fork** repository ini
2. **Buat branch** untuk fitur (`git checkout -b fitur-baru`)
3. **Commit** perubahan (`git commit -am 'Tambah fitur baru'`)
4. **Push** ke branch (`git push origin fitur-baru`)
5. **Buat Pull Request**

### Guidelines Kontribusi
- Gunakan Bahasa Indonesia untuk komentar dan dokumentasi
- Ikuti coding style yang ada
- Test fitur sebelum submit
- Update README jika diperlukan

## 📄 Lisensi

Proyek ini dilisensikan di bawah [MIT License](LICENSE) - lihat file LICENSE untuk detail.

## 🙏 Kredit

- **JNativeHook** - Library untuk deteksi input global
- **Oracle Java** - Platform dan runtime
- **Maven** - Build system
- **Komunitas Open Source** - Inspirasi dan dukungan

## 📞 Dukungan

Butuh bantuan? Silakan:
- 🐛 Buat [Issue](../../issues) untuk bug report
- 💡 Request fitur melalui [Discussions](../../discussions)
- 📧 Email: [your-email@example.com]
- 💬 Discord: [Link Discord Server]

## ⭐ Dukung Proyek

Jika aplikasi ini berguna untuk Anda:
- ⭐ **Star** repository ini
- 🍴 **Fork** untuk kontribusi
- 📢 **Share** ke teman-teman
- ☕ **[Buy me a coffee](https://buymeacoffee.com/yourname)**

---

<div align="center">

**Dibuat dengan ❤️ di Indonesia**

[⬆ Kembali ke atas](#-pasca-autoclicker)

</div>
