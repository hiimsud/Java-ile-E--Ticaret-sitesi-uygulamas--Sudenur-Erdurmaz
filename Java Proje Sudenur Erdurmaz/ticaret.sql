-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Anamakine: 127.0.0.1
-- Üretim Zamanı: 27 Ara 2023, 21:39:33
-- Sunucu sürümü: 10.4.28-MariaDB
-- PHP Sürümü: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Veritabanı: `ticaret`
--

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `admin`
--

CREATE TABLE `admin` (
  `admin_id` int(50) NOT NULL,
  `admin_ad` varchar(50) NOT NULL,
  `admin_sifre` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_turkish_ci;

--
-- Tablo döküm verisi `admin`
--

INSERT INTO `admin` (`admin_id`, `admin_ad`, `admin_sifre`) VALUES
(1, 'admin', '123');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `sepet`
--

CREATE TABLE `sepet` (
  `SepetID` int(11) NOT NULL,
  `KullaniciID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_turkish_ci;

--
-- Tablo döküm verisi `sepet`
--

INSERT INTO `sepet` (`SepetID`, `KullaniciID`) VALUES
(1, 9),
(2, 10),
(3, 6),
(4, 6),
(5, 6),
(6, 6),
(7, 1),
(8, 1),
(9, 1),
(10, 1),
(11, 0),
(12, 1),
(13, 1),
(14, 1),
(15, 11),
(16, 11),
(17, 12),
(18, 12),
(19, 12),
(20, 12);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `sepeturunleri`
--

CREATE TABLE `sepeturunleri` (
  `SepetID` int(11) DEFAULT NULL,
  `UrunID` int(11) DEFAULT NULL,
  `Adet` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_turkish_ci;

--
-- Tablo döküm verisi `sepeturunleri`
--

INSERT INTO `sepeturunleri` (`SepetID`, `UrunID`, `Adet`) VALUES
(2, 3, 1),
(NULL, 3, 1),
(NULL, 3, 1),
(14, 8, 2);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `tbl_kullanici`
--

CREATE TABLE `tbl_kullanici` (
  `ID` int(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `bakiye` double DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_turkish_ci;

--
-- Tablo döküm verisi `tbl_kullanici`
--

INSERT INTO `tbl_kullanici` (`ID`, `name`, `surname`, `password`, `email`, `bakiye`) VALUES
(1, 'a', 'a', 'a', 'a', 35997),
(5, 'b', 'b', 'b', 'b', 0),
(6, 'pına', 'Ay', '1', 'p@gmail.com', 20999),
(7, 'eda', 'yıldız', '12345', 'eddy@gmail.com', 0),
(8, 'büşra', 'ayral', '1', 'bbbb', 2),
(9, 'fırat', 'ayral', '123', 'fırat@gmail.com', 99999),
(10, 'kübra', 'ayral', '12', 'kübra@gmail.com', 200000),
(11, 'pınar', 'ayral', '1', 'pınar@gmail.com', 421001),
(12, 'Öznur', 'Ayazoğlu', '123', 'öznur@gmail.com', 42002);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `tbl_urunler`
--

CREATE TABLE `tbl_urunler` (
  `urunID` int(11) NOT NULL,
  `urunTuru` varchar(255) NOT NULL,
  `urunAdi` varchar(255) NOT NULL,
  `urunMiktar` int(255) NOT NULL,
  `urunBirimFiyat` int(255) NOT NULL,
  `urunRenk` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_turkish_ci;

--
-- Tablo döküm verisi `tbl_urunler`
--

INSERT INTO `tbl_urunler` (`urunID`, `urunTuru`, `urunAdi`, `urunMiktar`, `urunBirimFiyat`, `urunRenk`) VALUES
(1, 'Telefon', 'Samsung Galaxy A54', 30, 9400, 'Siyah'),
(2, 'Laptop', 'Macbook Pro M1', 1, 78999, 'beyaz'),
(3, 'Beyaz eşya', 'Bulaşık Makinası', 2, 32000, 'beyaz'),
(4, 'Telefon', 'İphone 15 Pro Max', 25, 27800, 'Siyah'),
(5, 'Telefon', 'İphone 15', 37, 20006, 'Beyaz'),
(6, 'Telefon', 'Xiaomi Redmi Note 10', 65, 6780, 'Mor'),
(7, 'Telefon', 'Samsung S23', 11, 29800, 'Siyah'),
(8, 'Tablet', 'APPLE 6.Nesil iPad Pro 12.9\"', 8, 35800, 'Uzay Grisi'),
(9, 'Tablet', 'Samsung Galaxy Tab S9', 22, 12680, 'Gri'),
(10, 'Laptop', 'Lenovo Thinkpad P16', 8, 75800, 'Gri'),
(11, 'Laptop', 'Dell Precision M7680', 11, 117800, 'Siyah'),
(12, 'Laptop', 'Asus Zenbook Pro 14', 8, 67800, 'Siyah'),
(13, 'Laptop', 'MSI CREATOR PRO', 9, 56900, 'Siyah'),
(14, 'Laptop', 'Apple MacBook Pro M2 Max ', 7, 46900, 'Uzay Grisi'),
(15, 'Laptop', 'Lenovo Legion 7 Pro', 18, 38790, 'Siyah'),
(16, 'Beyaz eşya', 'Arçelik Buzdolabı', 5, 3400, 'Beyaz'),
(17, 'Beyaz eşya', 'Beko Çamaşır Makinesi', 3, 2980, 'Beyaz'),
(18, 'Beyaz eşya', 'Fakir Su Sebili', 196, 580, 'Mor'),
(19, 'Beyaz eşya', 'Arçelik kurutma makinesi', 5, 16800, 'Siyah'),
(20, 'Beyaz eşya', 'Beko Su Sebili', 200, 480, 'Mor');

--
-- Dökümü yapılmış tablolar için indeksler
--

--
-- Tablo için indeksler `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`admin_id`);

--
-- Tablo için indeksler `sepet`
--
ALTER TABLE `sepet`
  ADD PRIMARY KEY (`SepetID`);

--
-- Tablo için indeksler `sepeturunleri`
--
ALTER TABLE `sepeturunleri`
  ADD KEY `SepetID` (`SepetID`),
  ADD KEY `UrunID` (`UrunID`);

--
-- Tablo için indeksler `tbl_kullanici`
--
ALTER TABLE `tbl_kullanici`
  ADD PRIMARY KEY (`ID`);

--
-- Tablo için indeksler `tbl_urunler`
--
ALTER TABLE `tbl_urunler`
  ADD PRIMARY KEY (`urunID`);

--
-- Dökümü yapılmış tablolar için AUTO_INCREMENT değeri
--

--
-- Tablo için AUTO_INCREMENT değeri `admin`
--
ALTER TABLE `admin`
  MODIFY `admin_id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Tablo için AUTO_INCREMENT değeri `sepet`
--
ALTER TABLE `sepet`
  MODIFY `SepetID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- Tablo için AUTO_INCREMENT değeri `tbl_kullanici`
--
ALTER TABLE `tbl_kullanici`
  MODIFY `ID` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Tablo için AUTO_INCREMENT değeri `tbl_urunler`
--
ALTER TABLE `tbl_urunler`
  MODIFY `urunID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- Dökümü yapılmış tablolar için kısıtlamalar
--

--
-- Tablo kısıtlamaları `sepeturunleri`
--
ALTER TABLE `sepeturunleri`
  ADD CONSTRAINT `sepeturunleri_ibfk_1` FOREIGN KEY (`SepetID`) REFERENCES `sepet` (`SepetID`),
  ADD CONSTRAINT `sepeturunleri_ibfk_2` FOREIGN KEY (`UrunID`) REFERENCES `tbl_urunler` (`urunID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
