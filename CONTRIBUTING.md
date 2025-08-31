# 🤝 Panduan Kontribusi

Terima kasih atas minat Anda untuk berkontribusi pada **Pasca AutoClicker**!

## 📋 Cara Kontribusi

### 1. Fork Repository

```bash
# Fork melalui GitHub UI, kemudian clone
git clone https://github.com/your-username/pasca-autoclicker.git
cd pasca-autoclicker
```

### 2. Setup Development Environment

```bash
# Pastikan Java 11+ terinstall
java -version

# Install Maven dependencies
mvn clean install
```

### 3. Buat Branch Baru

```bash
# Gunakan naming convention yang jelas
git checkout -b fitur/nama-fitur
git checkout -b bugfix/nama-bug
git checkout -b improvement/nama-improvement
```

### 4. Development Guidelines

#### Code Style

- **Bahasa**: Gunakan Bahasa Indonesia untuk komentar dan dokumentasi
- **Formatting**: Gunakan 4 spasi untuk indentasi
- **Naming**:
  - Class: `PascalCase`
  - Method: `camelCase`
  - Variable: `camelCase`
  - Constant: `UPPER_SNAKE_CASE`

#### Contoh Code Style

```java
public class AutoClickerEngine {
    private final int DEFAULT_CPS = 10;
    private boolean isEnabled = false;

    /**
     * Memulai proses auto-clicking
     * @param cps Kecepatan click per second
     */
    public void startClicking(int cps) {
        // Implementasi di sini
    }
}
```

### 5. Testing

```bash
# Compile dan test
mvn clean compile
mvn test

# Test manual
mvn exec:java
```

### 6. Commit Guidelines

#### Format Commit Message

```
type(scope): deskripsi singkat

Deskripsi detail jika diperlukan

Closes #123
```

#### Tipe Commit

- `feat`: Fitur baru
- `fix`: Bug fix
- `docs`: Update dokumentasi
- `style`: Perubahan formatting/style
- `refactor`: Refactoring code
- `perf`: Peningkatan performa
- `test`: Menambah/update test

#### Contoh Commit

```bash
git add .
git commit -m "feat(ui): tambah dark theme toggle

- Menambahkan toggle untuk switch antara light/dark theme
- Update color scheme untuk konsistensi
- Simpan preference theme di local storage

Closes #15"
```

### 7. Submit Pull Request

1. **Push ke fork Anda**:

```bash
git push origin fitur/nama-fitur
```

2. **Buat Pull Request** di GitHub dengan:

   - **Title**: Deskripsi singkat perubahan
   - **Description**: Detail lengkap tentang:
     - Apa yang diubah
     - Mengapa diubah
     - Cara testing
     - Screenshot (jika UI changes)

3. **Template PR**:

```markdown
## 📝 Deskripsi

Brief description of changes

## 🔄 Jenis Perubahan

- [ ] Bug fix
- [ ] Fitur baru
- [ ] Breaking change
- [ ] Update dokumentasi

## 🧪 Testing

- [ ] Code compile tanpa error
- [ ] Manual testing di Windows
- [ ] Manual testing di Linux
- [ ] Tidak ada regression

## 📸 Screenshots (jika ada)

Before / After

## 📋 Checklist

- [ ] Code mengikuti style guide
- [ ] Komentar dalam Bahasa Indonesia
- [ ] Update README jika diperlukan
- [ ] Testing sudah dilakukan
```

## 🐛 Bug Reports

Saat melaporkan bug, sertakan:

1. **Environment**:

   - OS: Windows/Linux/macOS
   - Java version: `java -version`
   - Application version

2. **Steps to Reproduce**:

   ```
   1. Buka aplikasi
   2. Klik tombol X
   3. Lihat error Y
   ```

3. **Expected vs Actual**:

   - Yang diharapkan: ...
   - Yang terjadi: ...

4. **Logs/Screenshots** jika ada

## 💡 Feature Requests

Format untuk request fitur baru:

```markdown
## 🚀 Fitur Request

**Deskripsi Fitur**
Deskripsi detail tentang fitur yang diinginkan

**Use Case**
Kapan dan mengapa fitur ini berguna

**Alternatif**
Apakah ada cara lain untuk mencapai tujuan yang sama?

**Additional Context**
Screenshots, mockups, atau referensi lain
```

## 📖 Development Setup Detail

### Project Structure

```
pasca-autoclicker/
├── src/main/java/com/pasca/
│   └── AutoClicker.java           # Main application
├── src/main/resources/           # Resources (icons, etc)
├── target/                       # Build output
├── pom.xml                      # Maven configuration
├── README.md                    # Documentation
├── CONTRIBUTING.md              # This file
└── LICENSE                      # MIT License
```

### Key Classes

- `AutoClicker`: Main application class
- `NativeMouseListener`: Handle global mouse events
- UI Components: Swing-based modern interface

### Important Dependencies

- **JNativeHook**: Global input detection
- **Java Swing**: UI framework
- **Maven**: Build system

## 🔧 Advanced Development

### Performance Testing

```bash
# Profile memory usage
java -XX:+PrintGCDetails -jar target/PascaAutoClicker.jar

# Check thread usage
jstack <pid>
```

### Debug Mode

```bash
# Run dengan debug output
java -Djava.util.logging.config.file=logging.properties -jar target/PascaAutoClicker.jar
```

## 📞 Butuh Bantuan?

- 💬 **Diskusi**: Gunakan GitHub Discussions
- 🐛 **Bug**: Buat GitHub Issue
- 📧 **Email**: [nathanpasca.project@gmail.com]
- 💬 **Discord**: [[Link Discord Server](https://discord.gg/9VuXZw3uNM)]

## 🙏 Terima Kasih!

Setiap kontribusi, besar atau kecil, sangat dihargai. Mari bersama-sama membuat **Pasca AutoClicker** menjadi lebih baik!

---

**Happy Coding!** 🚀
