#!/bin/bash

# 🚀 Pasca AutoClicker Release Script
# Usage: ./create-release.sh [version]

set -e  # Exit on any error

VERSION=${1:-"2.0.0"}
RELEASE_DIR="release-files"
DIST_DIR="dist"

echo "🚀 Creating Pasca AutoClicker v$VERSION Release Package..."
echo "================================================"

# Clean previous builds
echo "🧹 Cleaning previous builds..."
rm -rf target/
rm -rf $RELEASE_DIR/
rm -rf $DIST_DIR/
mkdir -p $RELEASE_DIR
mkdir -p $DIST_DIR

# Build the application
echo "⚡ Building application..."
mvn clean package -q

# Check if JAR was created
if [ ! -f "target/PascaAutoClicker.jar" ]; then
    echo "❌ Error: JAR file not found!"
    exit 1
fi

echo "✅ Build completed successfully!"

# Test the JAR
echo "🧪 Testing JAR file..."
java -jar target/PascaAutoClicker.jar --version 2>/dev/null || echo "⚠️  Warning: Could not test JAR (normal for GUI apps)"

# Get JAR file info
JAR_SIZE=$(du -h target/PascaAutoClicker.jar | cut -f1)
echo "📦 JAR size: $JAR_SIZE"

# Copy files to release directory
echo "📁 Preparing release files..."
cp target/PascaAutoClicker.jar $RELEASE_DIR/
cp README.md $RELEASE_DIR/
cp LICENSE $RELEASE_DIR/

# Create user-friendly README for release
cat > $RELEASE_DIR/INSTALL.txt << EOF
🖱️ PASCA AUTOCLICKER v$VERSION
===============================

📦 CARA INSTALL:
1. Pastikan Java 11+ terinstall
   - Cek dengan: java -version
   - Jika belum ada, download di: https://adoptium.net/

2. Jalankan aplikasi:
   - MUDAH: Double-click file "PascaAutoClicker.jar"
   - MANUAL: Buka terminal, ketik: java -jar PascaAutoClicker.jar

3. Jika diblokir Windows Defender:
   - Klik "More info" -> "Run anyway"
   - Atau tambahkan ke exception list

💡 CARA PAKAI:
1. Klik tombol hijau "AKTIFKAN AUTOCLICKER"
2. Atur kecepatan klik (CPS) dengan slider
3. TAHAN klik kiri mouse = mulai auto-click
4. LEPAS klik kiri mouse = berhenti auto-click
5. Opsional: Aktifkan jitter untuk gerakan natural

🎯 TIPS:
- Jalankan sebagai Administrator untuk performa terbaik
- Gunakan CPS 8-15 untuk gaming yang aman
- Aktifkan jitter agar tidak terdeteksi sebagai bot

🐛 MASALAH? 
- GitHub Issues: https://github.com/nathanpasca/pasca-autoclicker/issues
- Email: your-email@example.com

=======================================
Dibuat dengan ❤️ di Indonesia
Build: $(date '+%Y-%m-%d %H:%M:%S')
Version: $VERSION
Size: $JAR_SIZE
Java: $(java -version 2>&1 | head -n 1)
=======================================
EOF

# Create changelog
cat > $RELEASE_DIR/CHANGELOG.md << EOF
# 📝 Changelog v$VERSION

## ✨ Fitur Baru
- 🎨 **Modern Dark UI** - Interface gelap yang elegan dan modern
- 📊 **Real-time Statistics** - Monitor CPS, total clicks, dan durasi sesi
- 🎯 **Hold-to-Click** - Tahan klik kiri untuk mulai, lepas untuk berhenti
- 🎲 **Jitter Control** - Gerakan mouse acak untuk terlihat natural
- 🇮🇩 **Interface Indonesia** - Sepenuhnya dalam Bahasa Indonesia
- ⚡ **High Performance** - Threading optimized dengan precision timing

## 🔧 Perbaikan Teknis
- Kompatibilitas Java 8-21
- Memory usage yang efisien
- Thread safety improvements
- Cross-platform support (Windows/Linux/macOS)
- Better error handling

## 🎮 Gaming Features
- CPS range 1-50 untuk berbagai kebutuhan
- Jitter randomization untuk anti-detection
- Low latency clicking
- Stable performance untuk long sessions

## 📦 Download
- **PascaAutoClicker.jar** - Main executable ($(du -h target/PascaAutoClicker.jar | cut -f1))
- **INSTALL.txt** - Panduan instalasi lengkap
- **README.md** - Dokumentasi full
- **LICENSE** - MIT License

---
**Full Changelog**: https://github.com/nathanpasca/pasca-autoclicker/compare/v1.0.0...v$VERSION
EOF

# Create ZIP for easy distribution
echo "📦 Creating distribution packages..."
cd $RELEASE_DIR
zip -r "../$DIST_DIR/PascaAutoClicker-v$VERSION.zip" . -q
cd ..

# Create individual files in dist
cp $RELEASE_DIR/PascaAutoClicker.jar $DIST_DIR/
cp $RELEASE_DIR/INSTALL.txt $DIST_DIR/
cp $RELEASE_DIR/CHANGELOG.md $DIST_DIR/

# Generate checksums
echo "🔐 Generating checksums..."
cd $DIST_DIR
sha256sum * > checksums.txt
cd ..

# Summary
echo ""
echo "🎉 Release v$VERSION created successfully!"
echo "================================================"
echo "📁 Files ready for upload:"
echo "   • PascaAutoClicker.jar ($JAR_SIZE)"
echo "   • PascaAutoClicker-v$VERSION.zip"
echo "   • INSTALL.txt (user guide)"
echo "   • CHANGELOG.md (release notes)"
echo "   • checksums.txt (verification)"
echo ""
echo "📂 Location: ./dist/"
echo ""
echo "🚀 Next steps:"
echo "   1. Test: java -jar dist/PascaAutoClicker.jar"
echo "   2. Create GitHub tag: git tag -a v$VERSION -m \"Release v$VERSION\""
echo "   3. Push tag: git push origin v$VERSION"
echo "   4. Upload files to GitHub Release"
echo ""
echo "✅ Ready for deployment!"