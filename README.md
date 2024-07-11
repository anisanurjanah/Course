# UAS PEMROGRAMAN ANDROID 2

## Deskripsi Proyek
Proyek ini merupakan bagian dari Ujian Akhir Semester (UAS) untuk mata kuliah Pemrograman Android 2 yang dibimbing oleh Muhammad Khaidir Fahram, M.Kom (https://fahram.dev/).
Aplikasi ini berfungsi untuk menampilkan daftar kursus dan artikel serta mengelola daftar tugas (task) yang mencakup fitur-fitur seperti menambahkan, mengupdate, dan menghapus task. Aplikasi ini menggunakan RecyclerView untuk menampilkan daftar task dan berinteraksi dengan backend API untuk penyimpanan data.

## Fitur Aplikasi
<ul>
  <li><b>Menambahkan Task:</b> Pengguna dapat menambahkan task baru ke dalam daftar.</li>
  <li><b>Mengupdate Task:</b>Pengguna dapat menandai task sebagai selesai atau belum selesai.</li>
  <li><b>Menghapus Task:</b>Pengguna dapat menghapus task dari daftar.</li>
  <li><b>Sinkronisasi dengan Backend API:</b>Aplikasi ini terhubung dengan backend API untuk menyimpan dan mengambil data task.</li>
</ul>

## Teknologi yang Digunakan
<ul>
    <li><b>Kotlin:</b>Bahasa pemrograman utama untuk pengembangan aplikasi Android.</li>
    <li><b>Android SDK:</b>Framework untuk pengembangan aplikasi Android.</li>
    <li><b>RecyclerView:</b>Widget untuk menampilkan daftar data secara efisien.</li>
    <li><b>LoopJ:</b>Library untuk mengelola komunikasi HTTP dengan backend API.</li>
    <li><b>Coil:</b>Library untuk memuat gambar dari URL.</li>
</ul>

## Instalasi dan Penggunaan
1. <b>Clone Repository</b><br>
   git clone https://github.com/anisanurjanah/Mobile-Course-LoopJ.git
2. <b>Buka di Android Studio</b><br>
   Buka Android Studio, pilih "Open an existing Android Studio project" dan arahkan ke folder proyek yang sudah di-clone.
3. <b>Sync Project dengan Gradle Files</b><br>
   Pastikan project tersinkronisasi dengan Gradle dengan benar. Android Studio biasanya akan otomatis mendeteksi dan meminta Anda untuk melakukan sinkronisasi.
4. <b>Menjalankan Aplikasi</b><br>
   Hubungkan perangkat Android atau gunakan emulator, lalu klik tombol "Run" di Android Studio.

## BASE URL
https://fahram.dev/api

## API Endpoints
<b>POST</b> /login: Masuk ke dalam aplikasi<br>
<b>GET</b> /user: Mengambil semua informasi pengguna<br>
<b>GET</b> /course: Mengambil semua daftar kursus<br>
<b>GET</b> /posts: Mengambil semua daftar artikel<br>
<b>GET</b> /tasks: Mengambil semua daftar task<br>
<b>POST</b> /task/store: Menambahkan task baru<br>
<b>POST</b> /task/update: Mengupdate task sebagai selesai atau belum selesai<br>
<b>DELETE</b> /task/delete: Menghapus task dengan ID sebagai body
