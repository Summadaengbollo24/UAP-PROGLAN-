﻿#UAP PROGLAN 050 & 222
Sistem Akademik Mahasiswa

Deskripsi Program

Program ini adalah sebuah aplikasi berbasis Java Swing yang digunakan untuk mengelola data akademik mahasiswa. Aplikasi ini menyediakan berbagai fitur seperti login, tambah data mahasiswa, hapus data mahasiswa, cari mahasiswa berdasarkan NIM, menampilkan semua data mahasiswa, dan memperbarui data mahasiswa. Selain itu, aplikasi ini memiliki validasi untuk memastikan keamanan dan integritas data yang dimasukkan.

Fitur Utama

Login Sistem:

Pengguna harus login dengan username dan password yang valid untuk mengakses menu utama.

Validasi login meliputi:

Username harus bernilai "ADMIN".

Password harus minimal 8 karakter, mengandung huruf besar, huruf kecil, angka, dan simbol khusus.

Tambah Data Mahasiswa:

Pengguna dapat menambahkan data mahasiswa baru.

Data yang perlu diisi:

Nama mahasiswa.

NIM mahasiswa.

Path file foto mahasiswa.

Foto akan disalin ke folder new_images di dalam direktori proyek.

Validasi:

Semua field wajib diisi.

Foto harus memiliki ekstensi .jpg atau .png.

NIM harus unik.

Cari Mahasiswa:

Pengguna dapat mencari data mahasiswa berdasarkan NIM.

Informasi yang ditampilkan:

Nama mahasiswa.

NIM mahasiswa.

Path file foto.

Tampilkan Semua Mahasiswa:

Menampilkan daftar semua mahasiswa dalam bentuk tabel.

Tabel mencakup kolom Nama, NIM, dan Foto.

Foto mahasiswa akan ditampilkan di tabel.

Hapus Data Mahasiswa:

Pengguna dapat menghapus data mahasiswa berdasarkan NIM.

Dilengkapi dengan konfirmasi sebelum penghapusan.

Update Data Mahasiswa:

Pengguna dapat memperbarui nama dan/atau foto mahasiswa berdasarkan NIM.

Validasi:

Data NIM harus ada di database.

Tambah Mata Kuliah dan Nilai:

Pengguna dapat menambahkan data mata kuliah dan nilai mahasiswa.

Validasi memastikan semua field diisi.

Cara Menjalankan Program

Persiapan:

Pastikan Anda memiliki JDK 8 atau versi yang lebih baru terinstal di sistem Anda.

Kompilasi dan Eksekusi:

Buka terminal atau command prompt.

Pindah ke direktori tempat file Main.java berada.

Jalankan perintah berikut untuk mengompilasi:

javac Main.java

Jalankan program menggunakan perintah:

java Main

Login ke Sistem:

Masukkan username: ADMIN

Masukkan password yang valid sesuai dengan kriteria validasi.

Struktur Folder

|-- Main.java
|-- new_images/    (Folder untuk menyimpan salinan foto mahasiswa)

Validasi Login

Username:

Harus bernilai "ADMIN".

Password:

Minimal 8 karakter.

Mengandung minimal:

1 huruf besar (A-Z).

1 huruf kecil (a-z).

1 angka (0-9).

1 simbol khusus (!@#$%^&*).

Contoh password valid: Admin@123

Teknologi yang Digunakan

Java Swing: Untuk membangun antarmuka pengguna grafis (GUI).

Java I/O dan NIO: Untuk membaca, menulis, dan memindahkan file foto.

Java Collections (HashMap): Untuk menyimpan data mahasiswa secara sementara.

Catatan Tambahan

Semua data mahasiswa disimpan sementara dalam memori (HashMap) dan tidak tersimpan secara permanen.

Anda dapat menambahkan fitur penyimpanan permanen menggunakan database atau file sesuai kebutuhan.

Lisensi

Program ini bebas digunakan untuk keperluan pembelajaran dan pengembangan.
