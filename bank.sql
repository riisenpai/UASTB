-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 03, 2022 at 12:14 AM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.4.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bank`
--

-- --------------------------------------------------------

--
-- Table structure for table `setor`
--

CREATE TABLE `setor` (
  `no_transaksi` varchar(20) NOT NULL,
  `tgl` varchar(20) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `norek` int(11) NOT NULL,
  `saldoawal` int(11) NOT NULL,
  `setor` int(11) NOT NULL,
  `bunga` int(11) NOT NULL,
  `saldoakhir` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `setor`
--

INSERT INTO `setor` (`no_transaksi`, `tgl`, `nama`, `norek`, `saldoawal`, `setor`, `bunga`, `saldoakhir`) VALUES
('001', '02-01-2022', 'SHIFA', 232001, 250000, 500000, 15000, 765000),
('002', '02-01-2022', 'ALIFA', 200210, 100000, 500000, 12000, 612000),
('003', '02-01-2022', 'ALYA', 352180, 700000, 100000, 16000, 816000),
('004', '02-01-2022', 'OPA', 987456, 60000, 500000, 11200, 571200),
('005', '02-01-2022', 'SALSA', 765312, 100000, 600000, 14000, 714000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `setor`
--
ALTER TABLE `setor`
  ADD PRIMARY KEY (`no_transaksi`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
