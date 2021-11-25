-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Nov 11, 2016 at 01:30 
-- Server version: 10.1.16-MariaDB
-- PHP Version: 7.0.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `facturation2`
--

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

CREATE TABLE `client` (
  `id_client` int(11) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`id_client`, `nom`, `prenom`) VALUES
(1, 'MAGIC', 'Banana');

-- --------------------------------------------------------

--
-- Table structure for table `facture`
--

CREATE TABLE `facture` (
  `id_facture` int(11) NOT NULL,
  `id_client` int(11) DEFAULT NULL,
  `date_facture` datetime DEFAULT NULL,
  `total` double DEFAULT NULL,
  `id_reduction` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `facture`
--

INSERT INTO `facture` (`id_facture`, `id_client`, `date_facture`, `total`, `id_reduction`) VALUES
(1, 1, '2016-11-11 00:00:00', 1.6, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `ligne_facture`
--

CREATE TABLE `ligne_facture` (
  `id_facture` int(11) NOT NULL,
  `id_produit` int(11) NOT NULL,
  `quantite` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ligne_facture`
--

INSERT INTO `ligne_facture` (`id_facture`, `id_produit`, `quantite`) VALUES
(1, 1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `produit`
--

CREATE TABLE `produit` (
  `id_produit` int(11) NOT NULL,
  `libelle` varchar(50) NOT NULL,
  `id_type` int(11) DEFAULT NULL,
  `prix` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `produit`
--

INSERT INTO `produit` (`id_produit`, `libelle`, `id_type`, `prix`) VALUES
(1, 'Coca-Cola', 1, 0.8);

-- --------------------------------------------------------

--
-- Table structure for table `reduction`
--

CREATE TABLE `reduction` (
  `id_reduction` int(11) NOT NULL,
  `id_type_reduction` enum('1','2','3') NOT NULL,
  `taux` int(11) DEFAULT NULL,
  `id_produit` int(11) DEFAULT NULL,
  `quantite` int(11) DEFAULT NULL,
  `id_type_produit` int(11) DEFAULT NULL,
  `date_start` datetime DEFAULT NULL,
  `date_end` datetime DEFAULT NULL,
  `total_facture` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tva`
--

CREATE TABLE `tva` (
  `id_tva` int(11) NOT NULL,
  `libelle` varchar(20) NOT NULL,
  `taux` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tva`
--

INSERT INTO `tva` (`id_tva`, `libelle`, `taux`) VALUES
(1, 'Alimentaire', 5.5);

-- --------------------------------------------------------

--
-- Table structure for table `type_produit`
--

CREATE TABLE `type_produit` (
  `id_type` int(11) NOT NULL,
  `libelle` varchar(50) NOT NULL,
  `id_tva` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `type_produit`
--

INSERT INTO `type_produit` (`id_type`, `libelle`, `id_tva`) VALUES
(1, 'Soda', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`id_client`);

--
-- Indexes for table `facture`
--
ALTER TABLE `facture`
  ADD PRIMARY KEY (`id_facture`),
  ADD KEY `id_client` (`id_client`),
  ADD KEY `id_reduction` (`id_reduction`);

--
-- Indexes for table `ligne_facture`
--
ALTER TABLE `ligne_facture`
  ADD PRIMARY KEY (`id_facture`,`id_produit`),
  ADD KEY `id_produit` (`id_produit`);

--
-- Indexes for table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`id_produit`),
  ADD KEY `id_type` (`id_type`);

--
-- Indexes for table `reduction`
--
ALTER TABLE `reduction`
  ADD PRIMARY KEY (`id_reduction`),
  ADD KEY `id_produit` (`id_produit`),
  ADD KEY `id_type_produit` (`id_type_produit`);

--
-- Indexes for table `tva`
--
ALTER TABLE `tva`
  ADD PRIMARY KEY (`id_tva`);

--
-- Indexes for table `type_produit`
--
ALTER TABLE `type_produit`
  ADD PRIMARY KEY (`id_type`),
  ADD KEY `id_tva` (`id_tva`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `client`
--
ALTER TABLE `client`
  MODIFY `id_client` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `facture`
--
ALTER TABLE `facture`
  MODIFY `id_facture` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `produit`
--
ALTER TABLE `produit`
  MODIFY `id_produit` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `reduction`
--
ALTER TABLE `reduction`
  MODIFY `id_reduction` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `tva`
--
ALTER TABLE `tva`
  MODIFY `id_tva` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `type_produit`
--
ALTER TABLE `type_produit`
  MODIFY `id_type` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `facture`
--
ALTER TABLE `facture`
  ADD CONSTRAINT `facture_ibfk_1` FOREIGN KEY (`id_client`) REFERENCES `client` (`id_client`);

--
-- Constraints for table `ligne_facture`
--
ALTER TABLE `ligne_facture`
  ADD CONSTRAINT `ligne_facture_ibfk_1` FOREIGN KEY (`id_facture`) REFERENCES `facture` (`id_facture`),
  ADD CONSTRAINT `ligne_facture_ibfk_2` FOREIGN KEY (`id_produit`) REFERENCES `produit` (`id_produit`);

--
-- Constraints for table `produit`
--
ALTER TABLE `produit`
  ADD CONSTRAINT `produit_ibfk_1` FOREIGN KEY (`id_type`) REFERENCES `type_produit` (`id_type`);

--
-- Constraints for table `reduction`
--
ALTER TABLE `reduction`
  ADD CONSTRAINT `reduction_ibfk_1` FOREIGN KEY (`id_reduction`) REFERENCES `facture` (`id_reduction`),
  ADD CONSTRAINT `reduction_ibfk_2` FOREIGN KEY (`id_type_produit`) REFERENCES `type_produit` (`id_type`),
  ADD CONSTRAINT `reduction_ibfk_3` FOREIGN KEY (`id_produit`) REFERENCES `produit` (`id_produit`);

--
-- Constraints for table `type_produit`
--
ALTER TABLE `type_produit`
  ADD CONSTRAINT `type_produit_ibfk_1` FOREIGN KEY (`id_tva`) REFERENCES `tva` (`id_tva`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
