# UAS PEMROGRAMAN ANDROID 2

## Deskripsi Proyek
<p align="justify">
  Proyek ini merupakan bagian dari Ujian Akhir Semester (UAS) untuk mata kuliah Pemrograman Android 2 yang dibimbing oleh Muhammad Khaidir Fahram, M.Kom (https://fahram.dev/).
  Aplikasi ini berfungsi untuk menampilkan daftar kursus dan artikel serta mengelola daftar tugas (task) yang mencakup fitur-fitur seperti menambahkan, mengupdate, dan menghapus task.      Aplikasi ini menggunakan RecyclerView untuk menampilkan daftar task dan berinteraksi dengan backend API untuk penyimpanan data.
</p>

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

## Fitur Aplikasi
<ul>
  <li><b>Menambahkan Task:</b> Pengguna dapat menambahkan task baru ke dalam daftar.</li>
  <li><b>Mengupdate Task:</b> Pengguna dapat menandai task sebagai selesai atau belum selesai.</li>
  <li><b>Menghapus Task:</b> Pengguna dapat menghapus task dari daftar.</li>
  <li><b>Sinkronisasi dengan Backend API:</b> Aplikasi ini terhubung dengan backend API untuk menyimpan dan mengambil data task.</li>
</ul>

## Teknologi yang Digunakan
<ul>
    <li><b>Kotlin:</b> Bahasa pemrograman utama untuk pengembangan aplikasi Android.</li>
    <li><b>Android SDK:</b> Framework untuk pengembangan aplikasi Android.</li>
    <li><b>RecyclerView:</b> Widget untuk menampilkan daftar data secara efisien.</li>
    <li><b>LoopJ:</b> Library untuk mengelola komunikasi HTTP dengan backend API.</li>
    <li><b>Coil:</b> Library untuk memuat gambar dari URL.</li>
</ul>

## Dokumentasi
<p align="center">
  <img src="https://github.com/anisanurjanah/Mobile-Course-LoopJ/assets/74089025/c3dc64d4-0340-451e-af92-b157af05b4d3" width=320 height=360 alt="Splash screen">
  <img src="https://github.com/anisanurjanah/Mobile-Course-LoopJ/assets/74089025/f29ca18a-0eb4-4948-aa61-4d73cccd902c" width=320 height=360 alt="Onboarding">
  <img src="https://github.com/anisanurjanah/Mobile-Course-LoopJ/assets/74089025/b1d5cdb8-e5ac-452e-b9d2-ef83b96b092d" width=320 height=360 alt="Login">
  <img src="https://github.com/anisanurjanah/Mobile-Course-LoopJ/assets/74089025/0118322f-1576-40d2-a862-83b38e6568be" width=320 height=360 alt="User Profile">
  <img src="https://github.com/anisanurjanah/Mobile-Course-LoopJ/assets/74089025/f93e8c7c-77d2-422f-b6b9-5725fff377e5" width=320 height=360 alt="Course List">
  <img src="https://github.com/anisanurjanah/Mobile-Course-LoopJ/assets/74089025/818f4eaa-20d9-43dc-896d-1fdb5c725ce0" width=320 height=360 alt="Course Detail">
  <img src="https://github.com/anisanurjanah/Mobile-Course-LoopJ/assets/74089025/07271588-a403-444c-8249-b9f32b91f26f" width=320 height=360 alt="Task List">
  <img src="https://github.com/anisanurjanah/Mobile-Course-LoopJ/assets/74089025/0c663cf4-f070-4e51-a31f-b529cf0dffe6" width=320 height=360 alt="Article List">
  <img src="https://github.com/anisanurjanah/Mobile-Course-LoopJ/assets/74089025/7e4c1402-7e83-43b9-97c7-4a88f1b08e07" width=320 height=360 alt="Article Detail">
</p>

## Instalasi dan Penggunaan
1. <b>Clone Repository</b><br>
   git clone https://github.com/anisanurjanah/Mobile-Course-LoopJ.git
2. <b>Buka di Android Studio</b><br>
   Buka Android Studio, pilih "Open an existing Android Studio project" dan arahkan ke folder proyek yang sudah di-clone.
3. <b>Sync Project dengan Gradle Files</b><br>
   Pastikan project tersinkronisasi dengan Gradle dengan benar. Android Studio biasanya akan otomatis mendeteksi dan meminta Anda untuk melakukan sinkronisasi.
4. <b>Menjalankan Aplikasi</b><br>
   Hubungkan perangkat Android atau gunakan emulator, lalu klik tombol "Run" di Android Studio.

<h2>Let's connect!</h2>
<a href="https://www.instagram.com/nissxxse/">
  <img src="https://img.shields.io/badge/Instagram-@nissxxse-blue?&logo=instagram&logoColor=white" height=25 alt="Anisa's Instagram" />
</a>
<a href="https://www.linkedin.com/in/anisanurjanah/">
  <img src="https://img.shields.io/badge/LinkedIn-Anisa%20Nurjanah-blue?&logo=linkedin&logoColor=white" height=25 alt="Anisa's LinkedIn" />
</a>
