-- phpMyAdmin SQL Dump
-- version 4.0.10.8
-- http://www.phpmyadmin.net
--
-- Host: 127.12.176.2:3306
-- Generation Time: Mar 17, 2015 at 02:22 PM
-- Server version: 5.5.41
-- PHP Version: 5.3.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `bookstore`
--

-- --------------------------------------------------------

--
-- Table structure for table `Address`
--

CREATE TABLE IF NOT EXISTS `Address` (
  `id` int(11) NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `postalCode` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `streetAddress1` varchar(255) DEFAULT NULL,
  `streetAddress2` varchar(255) DEFAULT NULL,
  `streetAddress3` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Address`
--

INSERT INTO `Address` (`id`, `city`, `country`, `postalCode`, `state`, `streetAddress1`, `streetAddress2`, `streetAddress3`) VALUES
(7, 'Easytown', 'USA', '11111', 'Easy State', '123 Easy St', '', ''),
(8, 'Easytown', 'USA', '11111', 'Easy State', '123 Easy St', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `CatalogItem`
--

CREATE TABLE IF NOT EXISTS `CatalogItem` (
  `id` int(11) NOT NULL,
  `author` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `imagePath` varchar(255) DEFAULT NULL,
  `newItem` tinyint(1) DEFAULT NULL,
  `price` decimal(19,2) DEFAULT NULL,
  `sku` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `CatalogItem`
--

INSERT INTO `CatalogItem` (`id`, `author`, `category`, `description`, `imagePath`, `newItem`, `price`, `sku`, `title`) VALUES
(1, 'Lt. Howard Payson', 'military', 'description 1', '/images/books/BoyScouts.jpg', 1, '45.00', 'ABC123', 'The Boy Scouts at the Panama-Pacific Exposition'),
(2, 'Unknown', 'children', 'description 2', '/images/books/3LittleKittens.jpg', 0, '15.00', 'DEF456', 'The 3 Little Kittens'),
(3, 'Beatrix Potter', 'children', 'description 3', '/images/books/JemimaPuddleDuck.jpg', 1, '25.00', 'GHI789', 'The Tale of Jemima Puddle Duck'),
(4, 'Waldemar Bonsels', 'children', 'description 4', '/images/books/AdventuresOfMayaTheBee.jpg', 0, '23.00', '123132', 'The Adventures of Maya the Bee'),
(5, 'Unknown', 'comics', 'expensive sucker', '/images/books/CameraComics.jpg', 0, '195.00', '45677', 'Camera Comics');

-- --------------------------------------------------------

--
-- Table structure for table `Customer`
--

CREATE TABLE IF NOT EXISTS `Customer` (
  `id` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `bill_addr_id` int(11) DEFAULT NULL,
  `ship_addr_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_mf05ettnsb50ssbfdo9rpru5x` (`bill_addr_id`),
  KEY `FK_17jcm2qq5ou3jov17u3r6j0qv` (`ship_addr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Customer`
--

INSERT INTO `Customer` (`id`, `email`, `firstName`, `lastName`, `password`, `username`, `bill_addr_id`, `ship_addr_id`) VALUES
(6, 'jdoe@doe.com', 'John', 'Doe', 'redhat', 'jdoe', 7, 8);

-- --------------------------------------------------------

--
-- Table structure for table `OrderItem`
--

CREATE TABLE IF NOT EXISTS `OrderItem` (
  `id` int(11) NOT NULL,
  `extPrice` decimal(19,2) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_j0791dgrp2sf3xb8ga7yyyv70` (`item_id`),
  KEY `FK_6cxptya5vldowhtfdxs70ytw1` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `Payment`
--

CREATE TABLE IF NOT EXISTS `Payment` (
  `id` int(11) NOT NULL,
  `expireMonth` varchar(255) DEFAULT NULL,
  `expireYear` varchar(255) DEFAULT NULL,
  `holderName` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `paymentType` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Payment`
--

INSERT INTO `Payment` (`id`, `expireMonth`, `expireYear`, `holderName`, `number`, `paymentType`) VALUES
(10, '', '', '', '', 2),
(12, '', '', '', '', 2);

-- --------------------------------------------------------

--
-- Table structure for table `Promotion`
--

CREATE TABLE IF NOT EXISTS `Promotion` (
  `id` int(11) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `percent` int(11) DEFAULT NULL,
  `valid` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `hibernate_sequence`
--

CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(13);

-- --------------------------------------------------------

--
-- Table structure for table `order_`
--

CREATE TABLE IF NOT EXISTS `order_` (
  `id` int(11) NOT NULL,
  `discount` decimal(19,2) DEFAULT NULL,
  `orderDate` datetime DEFAULT NULL,
  `promoCode` varchar(255) DEFAULT NULL,
  `cust_id` int(11) DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_jotwtj6jjdavkted5btdeuaf` (`cust_id`),
  KEY `FK_jfompnp30ekifkbnwyoaytuo9` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `order_`
--

INSERT INTO `order_` (`id`, `discount`, `orderDate`, `promoCode`, `cust_id`, `order_id`) VALUES
(9, '1.00', '2015-03-16 16:05:00', 'abc123', 6, 10),
(11, '0.00', '2015-03-16 16:10:13', '', 6, 12);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Customer`
--
ALTER TABLE `Customer`
  ADD CONSTRAINT `FK_17jcm2qq5ou3jov17u3r6j0qv` FOREIGN KEY (`ship_addr_id`) REFERENCES `Address` (`id`),
  ADD CONSTRAINT `FK_mf05ettnsb50ssbfdo9rpru5x` FOREIGN KEY (`bill_addr_id`) REFERENCES `Address` (`id`);

--
-- Constraints for table `OrderItem`
--
ALTER TABLE `OrderItem`
  ADD CONSTRAINT `FK_6cxptya5vldowhtfdxs70ytw1` FOREIGN KEY (`order_id`) REFERENCES `order_` (`id`),
  ADD CONSTRAINT `FK_j0791dgrp2sf3xb8ga7yyyv70` FOREIGN KEY (`item_id`) REFERENCES `CatalogItem` (`id`);

--
-- Constraints for table `order_`
--
ALTER TABLE `order_`
  ADD CONSTRAINT `FK_jfompnp30ekifkbnwyoaytuo9` FOREIGN KEY (`order_id`) REFERENCES `Payment` (`id`),
  ADD CONSTRAINT `FK_jotwtj6jjdavkted5btdeuaf` FOREIGN KEY (`cust_id`) REFERENCES `Customer` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
