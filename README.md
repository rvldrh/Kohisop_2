# KohiSop II - Data Structure Based Payment System

## Deskripsi Proyek

KohiSop II merupakan aplikasi simulasi sistem kasir dan pembayaran untuk sebuah warung kopi yang dikembangkan menggunakan bahasa pemrograman Java dengan antarmuka GUI (Java Swing/NetBeans).

Proyek ini dibuat untuk memenuhi tugas Case-Based Learning (CBL) mata kuliah Algoritma dan Struktur Data. Sistem mengimplementasikan berbagai konsep Object-Oriented Programming (OOP), struktur data, abstraksi, polymorphism, serta pengelolaan transaksi pelanggan dan membership.

---

## Fitur Utama

### Manajemen Menu

* Menampilkan daftar menu makanan dan minuman.
* Menu dikelompokkan berdasarkan kategori.
* Menu diurutkan berdasarkan kode maupun harga.
* Validasi kode menu yang dimasukkan pelanggan.

### Manajemen Pesanan

* Pelanggan dapat memesan maksimal 5 jenis makanan dan 5 jenis minuman.
* Pengaturan kuantitas setiap item.
* Pembatalan item menggunakan nilai 0 atau perintah Skip.
* Validasi seluruh input agar program tidak berhenti akibat exception.

### Membership System

* Pembuatan member otomatis setelah transaksi.
* Kode member dibuat secara acak dengan format 6 karakter alfanumerik.
* Sistem poin berdasarkan total pembelian.
* Bonus poin khusus apabila kode member mengandung karakter "A".

### Perhitungan Pajak

* Pajak dihitung berdasarkan kategori dan harga item.
* Member tertentu dapat memperoleh pembebasan pajak.
* Pajak dihitung secara otomatis pada setiap transaksi.

### Mata Uang Pembayaran

Mendukung beberapa mata uang:

* IDR
* USD
* JPY
* MYR
* EUR

Sistem melakukan konversi nilai transaksi berdasarkan kurs yang telah ditentukan.

### Channel Pembayaran

#### Tunai

* Tidak mendapatkan diskon.

#### QRIS

* Diskon 5%.

#### eMoney

* Diskon 7%.
* Biaya administrasi Rp20.

#### Poin Member

* 1 poin = Rp2.
* Hanya dapat digunakan untuk pembayaran dalam mata uang IDR.

### Kuitansi Transaksi

Sistem menghasilkan kuitansi yang menampilkan:

* Detail makanan dan minuman.
* Kuantitas pesanan.
* Pajak setiap item.
* Total sebelum pajak.
* Total setelah pajak.
* Diskon pembayaran.
* Penggunaan poin member.
* Total akhir transaksi.
* Informasi member dan poin.

### Proses Tim Dapur

#### Tim Makanan

Menggunakan Priority Queue.

Karakteristik:

* Pesanan dengan harga makanan lebih tinggi diproses terlebih dahulu.
* Tidak memperhatikan urutan pelanggan.

#### Tim Minuman

Menggunakan Stack.

Karakteristik:

* Last Ordered First Served (LOFS).
* Pesanan minuman terakhir diproses lebih dahulu.

---

## Struktur Data yang Digunakan

| Struktur Data          | Kegunaan                  |
| ---------------------- | ------------------------- |
| ArrayList / LinkedList | Menyimpan daftar menu     |
| ArrayList / LinkedList | Menyimpan daftar pesanan  |
| ArrayList / LinkedList | Menyimpan database member |
| Stack                  | Antrian proses minuman    |
| Priority Queue         | Antrian proses makanan    |

---

## Konsep OOP yang Diterapkan

### Abstract Class

Digunakan untuk merepresentasikan objek Menu.

Contoh:

* Menu

  * Food
  * Beverage

### Interface / Abstract Class

Digunakan untuk:

* PaymentChannel
* Currency

### Inheritance

Subclass mewarisi atribut dan method dari superclass.

### Polymorphism

Digunakan pada:

* Perhitungan pajak.
* Perhitungan diskon pembayaran.
* Konversi mata uang.

### Encapsulation

Seluruh atribut disimpan secara private dan diakses melalui getter dan setter.

---

## Struktur Project

```text
src
│
├── model
│   ├── Menu.java
│   ├── Food.java
│   ├── Beverage.java
│   ├── Member.java
│   ├── OrderItem.java
│
├── payment
│   ├── PaymentChannel.java
│   ├── CashPayment.java
│   ├── QRISPayment.java
│   └── EMoneyPayment.java
│
├── currency
│   ├── Currency.java
│   ├── IDR.java
│   ├── USD.java
│   ├── JPY.java
│   ├── MYR.java
│   └── EUR.java
│
├── service
│   ├── OrderService.java
│   ├── TaxService.java
│   ├── MemberService.java
│   ├── ReceiptService.java
│   └── KitchenService.java
│
├── view
│   └── GUI Form
│
└── Main.java
```

---

## Aturan Pajak

### Minuman

| Kondisi         | Pajak |
| --------------- | ----- |
| Harga < 50      | 0%    |
| 50 ≤ Harga ≤ 55 | 8%    |
| Harga > 55      | 11%   |

### Makanan

| Kondisi    | Pajak |
| ---------- | ----- |
| Harga > 50 | 8%    |
| Harga ≤ 50 | 11%   |

### Member Khusus

Apabila kode member mengandung karakter "A", maka transaksi dibebaskan dari pajak.

---

## Aturan Poin

* Setiap kelipatan Rp10 transaksi menghasilkan 1 poin.
* 1 poin bernilai Rp2.
* Poin hanya dapat digunakan untuk pembayaran dalam mata uang IDR.
* Jika poin lebih besar dari total transaksi, sisa poin tetap tersimpan.

---

## Cara Menjalankan Program

### Prasyarat

* Java JDK 17 atau lebih baru
* Apache NetBeans IDE

### Langkah Menjalankan

1. Clone repository.

```bash
git clone https://github.com/rvldrh/kohisop_2.git
```

2. Buka project menggunakan NetBeans.

3. Build project.

4. Jalankan file Main.java.

5. Gunakan antarmuka GUI untuk melakukan simulasi transaksi.

---

## Tujuan Pembelajaran

Melalui proyek ini mahasiswa mempelajari:

* Implementasi struktur data pada studi kasus nyata.
* Penggunaan Stack dan Priority Queue.
* Pengembangan aplikasi berbasis Java GUI.
* Penerapan konsep Object-Oriented Programming.
* Pemanfaatan polymorphism untuk menyederhanakan logika bisnis.
* Perancangan sistem transaksi dan membership.

---

## Author

Kelompok Pemrograman Lanjut TI-C 25

Teknologi Informasi
Universitas Brawijaya
