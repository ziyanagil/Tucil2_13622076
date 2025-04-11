# README - Kompresi Gambar Dengan Metode Quadtree

## 1. Deskripsi Program

Program ini merupakan implementasi algoritma kompresi gambar yang memanfaatkan Algoritma Divide and Conquer menggunakan struktur data QuadTree. Program akan membaca sebuah file gambar, kemudian mengompresinya berdasarkan parameter yang ditentukan oleh pengguna, seperti metode pengukuran error, threshold, dan ukuran blok minimum. Hasil kompresi akan disimpan sebagai file gambar baru.

## 2. Struktur Program
```
Tucil2_13622076/
├── bin/                    # File hasil kompilasi (.class)
├── test/                   # Gambar input dan output untuk pengujian
├── doc/                    # Laporan
├── src/                    # Kode program (.java)
│   ├── Main.java
│   ├── QuadTree.java
│   ├── Node.java
│   ├── CompressionParameter.java
│   ├── ImageLoader.java
│   ├── ImageSaver.java
│   ├── ErrorCalculator.java
│   ├── Variance.java
│   ├── MAD.java
│   ├── MaxPixelDiff.java
│   ├── Entropy.java
│   └── SSIM.java
├── .gitignore              
└── README.md               # Penjelasan dan panduan program
```

## 3. Requirement dan Instalasi

### Requirement
- **Bahasa Pemrograman**: Java 11 atau lebih baru
- **Sistem Operasi**: Windows atau Linux
- **Compiler**: JDK (Java Development Kit) harus terinstal
- **Editor (opsional)**: VS Code

### Instalasi Java (Jika Belum Ada)

#### Windows:
1. Unduh dan instal JDK dari [Oracle](https://www.oracle.com/java/technologies/javase-downloads.html) atau [OpenJDK](https://openjdk.org/).
2. Tambahkan JDK ke **Environment Variables** jika diperlukan.
3. Pastikan `javac` dan `java` bisa diakses melalui Command Prompt dengan perintah:
   ```sh
   javac -version
   java -version
   ```

#### Linux (Debian/Ubuntu):
```sh
sudo apt update
sudo apt install openjdk-11-jdk
```

## 4. Cara Kompilasi Program

Sebelum menjalankan program, kode harus dikompilasi terlebih dahulu menggunakan `javac`. Pastikan semua file `.java` berada dalam direktori yang sama.

```sh
javac -d bin src/*.java
```

## 5. Cara Menjalankan Program

Setelah berhasil dikompilasi, jalankan program dengan perintah berikut:

```sh
cd bin
java Main
```

Saat dijalankan, program akan meminta input dari pengguna, termasuk alamat gambar yang akan dikompresi, metode pengukuran error, threshold, ukuran blok minimum, dan alamat untuk menyimpan gambar hasil kompresi.

### Contoh Penggunaan:
```sh
Masukkan alamat absolut gambar yang akan dikompresi: D:\path\to\your\image.jpg
Pilih metode perhitungan error:
1. Variance
2. Mean Absolute Deviation (MAD)
3. Max Pixel Difference
4. Entropy
5. Structural Similarity Index (SSIM) 
Masukkan pilihan (1-5): 1
Masukkan nilai threshold: 10
Masukkan ukuran blok minimum: 8
Masukkan persentase kompresi target (0 untuk menonaktifkan, misalnya 0.5 untuk 50%) [not implemented]: 0.5
Masukkan alamat absolut gambar hasil kompresi: D:\path\to\output\compressed_image.png
Masukkan alamat absolut GIF proses kompresi (kosongkan untuk dilewati) [not implemented]: 
```

Jika kompresi berhasil, program akan menyimpan gambar hasil kompresi di lokasi yang ditentukan.

## 6. Author

- **Nama**: Ziyan Agil Nur Ramadhan
- **NIM**: 13622076
- **Mata Kuliah**: IF2211 Strategi Algoritma
- **Tahun**: 2025