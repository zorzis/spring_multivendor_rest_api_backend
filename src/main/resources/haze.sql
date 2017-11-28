-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Generation Time: Oct 27, 2017 at 08:53 PM
-- Server version: 5.6.35-log
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `haze`
--

-- --------------------------------------------------------

--
-- Table structure for table `auth_revoked_tokens`
--

CREATE TABLE IF NOT EXISTS `auth_revoked_tokens` (
  `shoppingCartID` int(11) NOT NULL AUTO_INCREMENT,
  `user_account_id` varchar(250) NOT NULL,
  `token_id` varchar(250) NOT NULL,
  PRIMARY KEY (`shoppingCartID`),
  UNIQUE KEY `auth_revoked_tokens_user_account_id_uindex` (`user_account_id`),
  UNIQUE KEY `auth_revoked_tokens_token_id_uindex` (`token_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `clients_accounts`
--

CREATE TABLE IF NOT EXISTS `clients_accounts` (
  `shoppingCartID` int(11) NOT NULL AUTO_INCREMENT,
  `clientID` varchar(100) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `isEnabled` tinyint(4) NOT NULL DEFAULT '0',
  `last_password_reset_date` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`shoppingCartID`),
  UNIQUE KEY `clients_accounts_clientID_uindex` (`clientID`),
  UNIQUE KEY `clients_accounts_email_uindex` (`email`),
  UNIQUE KEY `clients_accounts_password_uindex` (`password`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=29 ;

--
-- Dumping data for table `clients_accounts`
--

INSERT INTO `clients_accounts` (`shoppingCartID`, `clientID`, `email`, `password`, `created_at`, `updated_at`, `isEnabled`, `last_password_reset_date`) VALUES
(27, 'haze_gr_client_UUID_8a7ae042a0374805a5a775214a499875', 'zorzis@gmail.com', 'uD2IEebxCWg0LmW41kpXktiwXT5qKOd6XKc38VSlRNwKNyrkPTVTd20LgMUi7LYbbtAqJZqem/FrDi3p1kPxTh5+Z96QKrSHis6H4jfMX/b8VmDBJX00NbY+X1p/va+q9Rskite3PzyhBUu5K2VZMumM2YwqwwUe/rt3ZpQdY34=', '2017-05-13 14:25:58', '2017-06-12 22:59:49', 0, NULL),
(28, 'haze_gr_client_UUID_8968fe3b6e2845478fe0c04e8db709d6', 'maraki@gmail.com', '46OLvgX7pFKXJ4ZSNROiNAxirBqAqZtGPAulu78h87HAezqnoeCxiDQe1IrA1HLNKeNclZTYVZLBdRRyk/8nnY74H5nDstrIQSBai47e/8uUpEnHR0IlCCw0oP2Yhli9XuydyNT40R7URIGyxxeaYn8dIaZltJr9+mkjAm22Mro=', '2017-05-15 22:33:49', '2017-05-27 08:37:09', 0, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `clients_addresses`
--

CREATE TABLE IF NOT EXISTS `clients_addresses` (
  `shoppingCartID` int(11) NOT NULL AUTO_INCREMENT,
  `street` varchar(255) DEFAULT NULL,
  `street_number` varchar(25) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `postal_code` varchar(100) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `latitude` varchar(255) DEFAULT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  `floor` varchar(100) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`shoppingCartID`),
  UNIQUE KEY `clients_addresses_id_uindex` (`shoppingCartID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=66 ;

--
-- Dumping data for table `clients_addresses`
--

INSERT INTO `clients_addresses` (`shoppingCartID`, `street`, `street_number`, `city`, `postal_code`, `state`, `country`, `latitude`, `longitude`, `floor`, `created_at`, `updated_at`) VALUES
(52, 'Vitsi', '12', 'Pefki', '151 21', 'Vorios Tomeas Athinon', 'Greece', '38.0526404', '23.79254220000007', '', '2017-06-13 00:42:00', NULL),
(53, 'Aspasias', '108', 'Cholargos', '155 61', 'Vorios Tomeas Athinon', 'Greece', '38.0038008', '23.79755590000002', '', '2017-06-13 00:45:59', NULL),
(57, 'Megalou Alexandrou', '20-22', 'Marousi', '151 22', 'Vorios Tomeas Athinon', 'Greece', '38.0532008', '23.798488099999986', '', '2017-07-10 17:16:59', NULL),
(63, 'Favierou', '2', 'Chios', '821 00', 'Chios', 'Greece', '38.3658312', '26.137211699999966', '', '2017-10-19 19:13:49', NULL),
(65, 'Georgiou Papandreou', '58', 'Zografou', '157 73', 'Kentrikos Tomeas Athinon', 'Greece', '37.98095379999999', '23.768494000000032', '', '2017-10-24 23:35:57', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `clients_have_addresses`
--

CREATE TABLE IF NOT EXISTS `clients_have_addresses` (
  `shoppingCartID` int(11) NOT NULL AUTO_INCREMENT,
  `clientID` varchar(100) NOT NULL,
  `addressID` int(11) NOT NULL,
  PRIMARY KEY (`shoppingCartID`),
  UNIQUE KEY `clients_have_addresses_id_uindex` (`shoppingCartID`),
  KEY `clients_have_addresses_clients_accounts_clientID_fk` (`clientID`),
  KEY `clients_have_addresses_clients_addresses_id_fk` (`addressID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=64 ;

--
-- Dumping data for table `clients_have_addresses`
--

INSERT INTO `clients_have_addresses` (`shoppingCartID`, `clientID`, `addressID`) VALUES
(50, 'haze_gr_client_UUID_8968fe3b6e2845478fe0c04e8db709d6', 52),
(55, 'haze_gr_client_UUID_8a7ae042a0374805a5a775214a499875', 57),
(61, 'haze_gr_client_UUID_8a7ae042a0374805a5a775214a499875', 63),
(63, 'haze_gr_client_UUID_8a7ae042a0374805a5a775214a499875', 65);

-- --------------------------------------------------------

--
-- Table structure for table `clients_have_authorities`
--

CREATE TABLE IF NOT EXISTS `clients_have_authorities` (
  `shoppingCartID` int(11) NOT NULL AUTO_INCREMENT,
  `clientID` varchar(100) NOT NULL,
  `authorityID` int(11) NOT NULL,
  PRIMARY KEY (`shoppingCartID`),
  UNIQUE KEY `clients_have_authorities_id_uindex` (`shoppingCartID`),
  KEY `clients_have_authorities_clients_accounts_clientID_fk` (`clientID`),
  KEY `clients_have_authorities_users_authorities_id_fk` (`authorityID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `clients_have_authorities`
--

INSERT INTO `clients_have_authorities` (`shoppingCartID`, `clientID`, `authorityID`) VALUES
(2, 'haze_gr_client_UUID_8a7ae042a0374805a5a775214a499875', 4),
(3, 'haze_gr_client_UUID_8968fe3b6e2845478fe0c04e8db709d6', 4),
(4, 'haze_gr_client_UUID_8968fe3b6e2845478fe0c04e8db709d6', 1);

-- --------------------------------------------------------

--
-- Table structure for table `clients_profile`
--

CREATE TABLE IF NOT EXISTS `clients_profile` (
  `clientID` varchar(100) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`clientID`),
  UNIQUE KEY `clients_profile_clientID_uindex` (`clientID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `clients_profile`
--

INSERT INTO `clients_profile` (`clientID`, `first_name`, `last_name`, `birth_date`, `gender`) VALUES
('haze_gr_client_UUID_8968fe3b6e2845478fe0c04e8db709d6', 'Maria', 'Linou', '1988-08-04', 'FEMALE'),
('haze_gr_client_UUID_8a7ae042a0374805a5a775214a499875', 'Zorzis', 'Varkaris', '1986-11-21', 'MALE');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE IF NOT EXISTS `orders` (
  `orderID` varchar(100) NOT NULL,
  `paymentID` varchar(100) NOT NULL,
  `total_order_price` float NOT NULL,
  `subtotal_order_price` float NOT NULL,
  `order_status_code` varchar(100) DEFAULT NULL,
  `date_order_placed` datetime NOT NULL,
  `date_order_paid` datetime DEFAULT NULL,
  `other_order_details` text,
  `total_products_tax` float NOT NULL,
  `total_processor_tax` float DEFAULT '0',
  `total_shipping_tax` float DEFAULT NULL,
  PRIMARY KEY (`orderID`),
  UNIQUE KEY `orders_orderID_uindex` (`orderID`),
  UNIQUE KEY `orders_paymentID_uindex` (`paymentID`),
  KEY `orders_orders_status_codes_ref_order_status_code_fk` (`order_status_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`orderID`, `paymentID`, `total_order_price`, `subtotal_order_price`, `order_status_code`, `date_order_placed`, `date_order_paid`, `other_order_details`, `total_products_tax`, `total_processor_tax`, `total_shipping_tax`) VALUES
('order_5a1dce131f45434ca694b57e6f46386b', 'haze_payment_5fa21f9da3024463ae6059dcaee3987c', 37.2, 30, 'inProgress', '2017-10-26 18:21:38', NULL, NULL, 7.2, 1.6148, NULL),
('order_7a0a2de76bfb47c0973f77b1b1cc2254', 'haze_payment_e9cd3cd0da7a4c04899148f0aa15b6fe', 66.34, 53.5, 'paid', '2017-10-26 20:40:58', '2017-10-26 20:43:06', NULL, 12.84, 2.60556, NULL),
('order_c1c3660823d24a8fbdfcaed1785c5301', 'haze_payment_0a3bb1f3f97e4c2caca0c12cdfebd77f', 37.2, 30, 'paid', '2017-10-25 22:22:45', '2017-10-25 22:24:08', NULL, 7.2, 1.6148, NULL),
('order_ee1400e5e7cd4f3ea310e7633aea51c4', 'haze_payment_57b6bb0f3db24c38a5a0fb4673c65b7b', 36.58, 29.5, 'paid', '2017-10-25 22:25:29', '2017-10-25 22:25:29', NULL, 7.08, 1.59372, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `orders_have_clients_producers`
--

CREATE TABLE IF NOT EXISTS `orders_have_clients_producers` (
  `shoppingCartID` int(11) NOT NULL AUTO_INCREMENT,
  `orderID` varchar(100) NOT NULL,
  `clientID` varchar(100) NOT NULL,
  `producerID` varchar(100) NOT NULL,
  PRIMARY KEY (`shoppingCartID`),
  UNIQUE KEY `orders_have_clients_producers_orderID_uindex` (`orderID`),
  UNIQUE KEY `orders_have_clients_producers_id_uindex` (`shoppingCartID`),
  KEY `orders_have_clients_producers_clients_accounts_clientID_fk` (`clientID`),
  KEY `orders_have_clients_producers_producers_accounts_producerID_fk` (`producerID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=143 ;

--
-- Dumping data for table `orders_have_clients_producers`
--

INSERT INTO `orders_have_clients_producers` (`shoppingCartID`, `orderID`, `clientID`, `producerID`) VALUES
(139, 'order_c1c3660823d24a8fbdfcaed1785c5301', 'haze_gr_client_UUID_8a7ae042a0374805a5a775214a499875', 'haze_gr_producer_UUID_92c8e9fdcde94632b728d0e2492364f4'),
(140, 'order_ee1400e5e7cd4f3ea310e7633aea51c4', 'haze_gr_client_UUID_8a7ae042a0374805a5a775214a499875', 'haze_gr_producer_UUID_92c8e9fdcde94632b728d0e2492364f4'),
(141, 'order_5a1dce131f45434ca694b57e6f46386b', 'haze_gr_client_UUID_8a7ae042a0374805a5a775214a499875', 'haze_gr_producer_UUID_92c8e9fdcde94632b728d0e2492364f4'),
(142, 'order_7a0a2de76bfb47c0973f77b1b1cc2254', 'haze_gr_client_UUID_8a7ae042a0374805a5a775214a499875', 'haze_gr_producer_UUID_92c8e9fdcde94632b728d0e2492364f4');

-- --------------------------------------------------------

--
-- Table structure for table `orders_have_products`
--

CREATE TABLE IF NOT EXISTS `orders_have_products` (
  `orders_have_products_id` int(11) NOT NULL AUTO_INCREMENT,
  `orderID` varchar(100) NOT NULL,
  `order_product_ID` int(11) NOT NULL,
  PRIMARY KEY (`orders_have_products_id`),
  UNIQUE KEY `orders_have_products_orders_have_products_id_uindex` (`orders_have_products_id`),
  KEY `orders_have_products_orders_orderID_fk` (`orderID`),
  KEY `orders_have_products_order_products_order_product_id_fk` (`order_product_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=170 ;

--
-- Dumping data for table `orders_have_products`
--

INSERT INTO `orders_have_products` (`orders_have_products_id`, `orderID`, `order_product_ID`) VALUES
(163, 'order_c1c3660823d24a8fbdfcaed1785c5301', 198),
(164, 'order_ee1400e5e7cd4f3ea310e7633aea51c4', 199),
(165, 'order_ee1400e5e7cd4f3ea310e7633aea51c4', 200),
(166, 'order_5a1dce131f45434ca694b57e6f46386b', 201),
(167, 'order_5a1dce131f45434ca694b57e6f46386b', 202),
(168, 'order_7a0a2de76bfb47c0973f77b1b1cc2254', 203),
(169, 'order_7a0a2de76bfb47c0973f77b1b1cc2254', 204);

-- --------------------------------------------------------

--
-- Table structure for table `orders_status_codes_ref`
--

CREATE TABLE IF NOT EXISTS `orders_status_codes_ref` (
  `order_status_code` varchar(100) NOT NULL,
  `order_status_code_description` text NOT NULL,
  PRIMARY KEY (`order_status_code`),
  UNIQUE KEY `orders_status_codes_ref_order_status_code_uindex` (`order_status_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `orders_status_codes_ref`
--

INSERT INTO `orders_status_codes_ref` (`order_status_code`, `order_status_code_description`) VALUES
('inProgress', 'For Orders In Progress'),
('paid', 'For Paid Orders'),
('started', 'For Orders Just started the travel through our beautifull API');

-- --------------------------------------------------------

--
-- Table structure for table `order_client_address_details`
--

CREATE TABLE IF NOT EXISTS `order_client_address_details` (
  `orderID` varchar(100) NOT NULL,
  `clientID` varchar(100) NOT NULL,
  `street` varchar(255) DEFAULT NULL,
  `street_number` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `postal_code` varchar(100) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `latitude` varchar(255) DEFAULT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  `floor` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`orderID`),
  UNIQUE KEY `order_client_address_details_orderID_uindex` (`orderID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `order_client_address_details`
--

INSERT INTO `order_client_address_details` (`orderID`, `clientID`, `street`, `street_number`, `city`, `postal_code`, `state`, `country`, `latitude`, `longitude`, `floor`) VALUES
('order_5a1dce131f45434ca694b57e6f46386b', 'haze_gr_client_UUID_8a7ae042a0374805a5a775214a499875', 'Favierou', '2', 'Chios', '821 00', 'Chios', 'Greece', '38.3658312', '26.137211699999966', ''),
('order_7a0a2de76bfb47c0973f77b1b1cc2254', 'haze_gr_client_UUID_8a7ae042a0374805a5a775214a499875', 'Megalou Alexandrou', '20-22', 'Marousi', '151 22', 'Vorios Tomeas Athinon', 'Greece', '38.0532008', '23.798488099999986', ''),
('order_c1c3660823d24a8fbdfcaed1785c5301', 'haze_gr_client_UUID_8a7ae042a0374805a5a775214a499875', 'Georgiou Papandreou', '58', 'Zografou', '157 73', 'Kentrikos Tomeas Athinon', 'Greece', '37.98095379999999', '23.768494000000032', ''),
('order_ee1400e5e7cd4f3ea310e7633aea51c4', 'haze_gr_client_UUID_8a7ae042a0374805a5a775214a499875', 'Favierou', '2', 'Chios', '821 00', 'Chios', 'Greece', '38.3658312', '26.137211699999966', '');

-- --------------------------------------------------------

--
-- Table structure for table `order_client_details`
--

CREATE TABLE IF NOT EXISTS `order_client_details` (
  `orderID` varchar(100) NOT NULL,
  `clientID` varchar(100) NOT NULL,
  `order_client_first_name` varchar(255) NOT NULL,
  `order_client_last_name` varchar(255) NOT NULL,
  `order_client_email` varchar(255) NOT NULL,
  `order_client_gender` varchar(255) NOT NULL,
  `order_client_birth_date` date NOT NULL,
  PRIMARY KEY (`orderID`),
  UNIQUE KEY `order_client_details_orderID_uindex` (`orderID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `order_client_details`
--

INSERT INTO `order_client_details` (`orderID`, `clientID`, `order_client_first_name`, `order_client_last_name`, `order_client_email`, `order_client_gender`, `order_client_birth_date`) VALUES
('order_5a1dce131f45434ca694b57e6f46386b', 'haze_gr_client_UUID_8a7ae042a0374805a5a775214a499875', 'Zorzis', 'Varkaris', 'zorzis@gmail.com', 'MALE', '1986-11-21'),
('order_7a0a2de76bfb47c0973f77b1b1cc2254', 'haze_gr_client_UUID_8a7ae042a0374805a5a775214a499875', 'Zorzis', 'Varkaris', 'zorzis@gmail.com', 'MALE', '1986-11-21'),
('order_c1c3660823d24a8fbdfcaed1785c5301', 'haze_gr_client_UUID_8a7ae042a0374805a5a775214a499875', 'Zorzis', 'Varkaris', 'zorzis@gmail.com', 'MALE', '1986-11-21'),
('order_ee1400e5e7cd4f3ea310e7633aea51c4', 'haze_gr_client_UUID_8a7ae042a0374805a5a775214a499875', 'Zorzis', 'Varkaris', 'zorzis@gmail.com', 'MALE', '1986-11-21');

-- --------------------------------------------------------

--
-- Table structure for table `order_producer_address_details`
--

CREATE TABLE IF NOT EXISTS `order_producer_address_details` (
  `orderID` varchar(100) NOT NULL,
  `producerID` varchar(100) NOT NULL,
  `street` varchar(255) DEFAULT NULL,
  `street_number` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `postal_code` varchar(100) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `latitude` varchar(255) DEFAULT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  `floor` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`orderID`),
  UNIQUE KEY `order_producer_address_details_orderID_uindex` (`orderID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `order_producer_address_details`
--

INSERT INTO `order_producer_address_details` (`orderID`, `producerID`, `street`, `street_number`, `city`, `postal_code`, `state`, `country`, `latitude`, `longitude`, `floor`) VALUES
('order_5a1dce131f45434ca694b57e6f46386b', 'haze_gr_producer_UUID_92c8e9fdcde94632b728d0e2492364f4', 'Unnamed Road', '', 'Katarraktis', '821 02', 'Chios', 'Greece', '38.2662805', '26.10051450000003', ''),
('order_7a0a2de76bfb47c0973f77b1b1cc2254', 'haze_gr_producer_UUID_92c8e9fdcde94632b728d0e2492364f4', 'Unnamed Road', '', 'Katarraktis', '821 02', 'Chios', 'Greece', '38.2662805', '26.10051450000003', ''),
('order_c1c3660823d24a8fbdfcaed1785c5301', 'haze_gr_producer_UUID_92c8e9fdcde94632b728d0e2492364f4', 'Unnamed Road', '', 'Katarraktis', '821 02', 'Chios', 'Greece', '38.2662805', '26.10051450000003', ''),
('order_ee1400e5e7cd4f3ea310e7633aea51c4', 'haze_gr_producer_UUID_92c8e9fdcde94632b728d0e2492364f4', 'Unnamed Road', '', 'Katarraktis', '821 02', 'Chios', 'Greece', '38.2662805', '26.10051450000003', '');

-- --------------------------------------------------------

--
-- Table structure for table `order_producer_details`
--

CREATE TABLE IF NOT EXISTS `order_producer_details` (
  `orderID` varchar(100) NOT NULL,
  `producerID` varchar(100) NOT NULL,
  `order_producer_first_name` varchar(255) NOT NULL,
  `order_producer_last_name` varchar(255) NOT NULL,
  `order_producer_email` varchar(255) NOT NULL,
  `order_producer_gender` varchar(255) NOT NULL,
  `order_producer_birth_date` date NOT NULL,
  PRIMARY KEY (`orderID`),
  UNIQUE KEY `order_producer_details_orderID_uindex` (`orderID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `order_producer_details`
--

INSERT INTO `order_producer_details` (`orderID`, `producerID`, `order_producer_first_name`, `order_producer_last_name`, `order_producer_email`, `order_producer_gender`, `order_producer_birth_date`) VALUES
('order_5a1dce131f45434ca694b57e6f46386b', 'haze_gr_producer_UUID_92c8e9fdcde94632b728d0e2492364f4', 'Panagiotis', 'Tsampos', 'tsampos@gmail.com', 'MALE', '1987-01-16'),
('order_7a0a2de76bfb47c0973f77b1b1cc2254', 'haze_gr_producer_UUID_92c8e9fdcde94632b728d0e2492364f4', 'Panagiotis', 'Tsampos', 'tsampos@gmail.com', 'MALE', '1987-01-16'),
('order_c1c3660823d24a8fbdfcaed1785c5301', 'haze_gr_producer_UUID_92c8e9fdcde94632b728d0e2492364f4', 'Panagiotis', 'Tsampos', 'tsampos@gmail.com', 'MALE', '1987-01-16'),
('order_ee1400e5e7cd4f3ea310e7633aea51c4', 'haze_gr_producer_UUID_92c8e9fdcde94632b728d0e2492364f4', 'Panagiotis', 'Tsampos', 'tsampos@gmail.com', 'MALE', '1987-01-16');

-- --------------------------------------------------------

--
-- Table structure for table `order_products`
--

CREATE TABLE IF NOT EXISTS `order_products` (
  `productID` varchar(100) NOT NULL,
  `order_product_name` varchar(255) NOT NULL,
  `order_product_description` varchar(255) NOT NULL,
  `order_product_price` float NOT NULL,
  `order_product_stock_quantity_at_moment_order_placed` float NOT NULL,
  `order_product_quantity` float NOT NULL,
  `order_product_category_id` varchar(100) NOT NULL,
  `order_product_category_name` varchar(255) NOT NULL,
  `order_product_id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`order_product_id`),
  UNIQUE KEY `order_products_id_uindex` (`order_product_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=205 ;

--
-- Dumping data for table `order_products`
--

INSERT INTO `order_products` (`productID`, `order_product_name`, `order_product_description`, `order_product_price`, `order_product_stock_quantity_at_moment_order_placed`, `order_product_quantity`, `order_product_category_id`, `order_product_category_name`, `order_product_id`) VALUES
('haze_gr_Spirit__UID_05185b0c71a54510b3b5ffe15d29ef7a', 'hazeTsamposSample2', 'hazeTsamposSample2Description', 15, 94, 2, 'haze_gr_haze_123', 'haze', 198),
('haze_gr_Spirit__UID_7d4ff57b4d514d468ef956801b00d42a', 'haze Chiotiki me Glukaniso', 'haze chiotiki me glukaniso', 14, 67, 0.5, 'haze_gr_haze_123', 'haze', 199),
('haze_gr_Spirit__UID_05185b0c71a54510b3b5ffe15d29ef7a', 'hazeTsamposSample2', 'hazeTsamposSample2Description', 15, 94, 1.5, 'haze_gr_haze_123', 'haze', 200),
('haze_gr_Spirit__UID_8a95f82aa54045e896f25b5ceb4a3530', 'hazeTsamposSample3', 'hazeTsamposSample3Description', 17, 45, 1, 'haze_gr_haze_123', 'haze', 201),
('haze_gr_Spirit__UID_7cd37c91b2294e329027b889c7dc291a', 'hazeTsamposSample1', 'hazeTsamposSample1Description', 13, 67, 1, 'haze_gr_haze_123', 'haze', 202),
('haze_gr_Spirit__UID_8a95f82aa54045e896f25b5ceb4a3530', 'hazeTsamposSample3', 'hazeTsamposSample3Description', 17, 45, 1.5, 'haze_gr_haze_123', 'haze', 203),
('haze_gr_Spirit__UID_7d4ff57b4d514d468ef956801b00d42a', 'haze Chiotiki me Glukaniso', 'haze chiotiki me glukaniso', 14, 67, 2, 'haze_gr_haze_123', 'haze', 204);

-- --------------------------------------------------------

--
-- Table structure for table `payments`
--

CREATE TABLE IF NOT EXISTS `payments` (
  `paymentID` varchar(255) NOT NULL,
  `paymentMethodID` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`paymentID`),
  UNIQUE KEY `payments_paymentID_uindex` (`paymentID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `payments`
--

INSERT INTO `payments` (`paymentID`, `paymentMethodID`, `created_at`, `updated_at`) VALUES
('haze_payment_0a3bb1f3f97e4c2caca0c12cdfebd77f', 'paypal', '2017-10-25 22:22:48', '2017-10-25 22:24:08'),
('haze_payment_57b6bb0f3db24c38a5a0fb4673c65b7b', 'onDelivery', '2017-10-25 22:25:29', '2017-10-25 22:25:29'),
('haze_payment_5fa21f9da3024463ae6059dcaee3987c', 'paypal', '2017-10-26 18:21:47', NULL),
('haze_payment_e9cd3cd0da7a4c04899148f0aa15b6fe', 'paypal', '2017-10-26 20:41:00', '2017-10-26 20:43:06');

-- --------------------------------------------------------

--
-- Table structure for table `payments_approvals`
--

CREATE TABLE IF NOT EXISTS `payments_approvals` (
  `paymentApprovalID` varchar(255) NOT NULL,
  `paymentID` varchar(255) DEFAULT NULL,
  `status` varchar(25) NOT NULL,
  `processor_message` text NOT NULL,
  `ammount` float NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`paymentApprovalID`),
  UNIQUE KEY `payments_approvals_paymentApprovalID_uindex` (`paymentApprovalID`),
  KEY `payments_approvals_payments_paymentID_fk` (`paymentID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `payments_approvals`
--

INSERT INTO `payments_approvals` (`paymentApprovalID`, `paymentID`, `status`, `processor_message`, `ammount`, `created_at`, `updated_at`) VALUES
('haze_payment_approval_22809136c7224d26b4ff9abb543efa65', 'haze_payment_e9cd3cd0da7a4c04899148f0aa15b6fe', 'SUCCEEDED', 'PAY-45D75406NN955633SLHZEQWY', 66.34, '2017-10-26 20:41:00', '2017-10-26 20:43:06'),
('haze_payment_approval_53ce40d4d6ea46d1858195a3c5adbd32', 'haze_payment_57b6bb0f3db24c38a5a0fb4673c65b7b', 'SUCCEEDED', 'Payment OnDelivery Approved internally by haze.gr platform.', 36.58, '2017-10-25 22:25:29', '2017-10-25 22:25:29'),
('haze_payment_approval_714d1c03debb4036b57c0429c0f2604d', 'haze_payment_5fa21f9da3024463ae6059dcaee3987c', 'SUCCEEDED', 'PAY-4G052361M6419235JLHZCPOA', 37.2, '2017-10-26 18:21:47', NULL),
('haze_payment_approval_e7b78ecd91d54f22887f5a22dc46d3dc', 'haze_payment_0a3bb1f3f97e4c2caca0c12cdfebd77f', 'SUCCEEDED', 'PAY-4VH44743H7628241GLHYQ5NQ', 37.2, '2017-10-25 22:22:48', '2017-10-25 22:24:08');

-- --------------------------------------------------------

--
-- Table structure for table `payments_deposits`
--

CREATE TABLE IF NOT EXISTS `payments_deposits` (
  `paymentDepositID` varchar(255) NOT NULL,
  `paymentApprovalID` varchar(255) DEFAULT NULL,
  `status` varchar(25) NOT NULL,
  `processor_message` text NOT NULL,
  `ammount` float NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`paymentDepositID`),
  UNIQUE KEY `payments_desposits_paymentDepositID_uindex` (`paymentDepositID`),
  KEY `payments_desposits_payments_approvals_paymentApprovalID_fk` (`paymentApprovalID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `payments_deposits`
--

INSERT INTO `payments_deposits` (`paymentDepositID`, `paymentApprovalID`, `status`, `processor_message`, `ammount`, `created_at`, `updated_at`) VALUES
('haze_payment_deposit_8998e2f47d04463fb491a5d21cfb4c97', 'haze_payment_approval_53ce40d4d6ea46d1858195a3c5adbd32', 'SUCCEEDED', 'Payment OnDelivery Deposit internally by haze.gr platform.', 36.58, '2017-10-25 22:25:29', NULL),
('haze_payment_deposit_a411e73f95ee46e1b2bd095a84a0dffd', 'haze_payment_approval_e7b78ecd91d54f22887f5a22dc46d3dc', 'SUCCEEDED', '4B072051SJ118592U', 37.2, '2017-10-25 22:24:08', NULL),
('haze_payment_deposit_d385ee603d7847bd8f8798298996f23f', 'haze_payment_approval_22809136c7224d26b4ff9abb543efa65', 'SUCCEEDED', '3RM26070M1424074K', 66.34, '2017-10-26 20:43:06', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `payments_onDelivery`
--

CREATE TABLE IF NOT EXISTS `payments_onDelivery` (
  `paymentID` varchar(255) NOT NULL,
  `is_delivery_paid` tinyint(1) NOT NULL,
  `delivery_paid_at` datetime DEFAULT NULL,
  PRIMARY KEY (`paymentID`),
  UNIQUE KEY `payments_onDelivery_alt_paymentID_uindex` (`paymentID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `payments_onDelivery`
--

INSERT INTO `payments_onDelivery` (`paymentID`, `is_delivery_paid`, `delivery_paid_at`) VALUES
('haze_payment_57b6bb0f3db24c38a5a0fb4673c65b7b', 1, '2017-10-25 22:25:29');

-- --------------------------------------------------------

--
-- Table structure for table `payments_paypal`
--

CREATE TABLE IF NOT EXISTS `payments_paypal` (
  `paymentID` varchar(255) NOT NULL,
  `paypal_completed_payment_transaction_no` varchar(255) DEFAULT NULL,
  `paypal_approval_paymentID` varchar(255) NOT NULL,
  PRIMARY KEY (`paymentID`),
  UNIQUE KEY `payments_paypal_alt_paymentID_uindex` (`paymentID`),
  UNIQUE KEY `payments_paypal_paypal_approval_paymentID_uindex` (`paypal_approval_paymentID`),
  UNIQUE KEY `payments_paypal_paypal_completed_payment_transaction_no_uindex` (`paypal_completed_payment_transaction_no`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `payments_paypal`
--

INSERT INTO `payments_paypal` (`paymentID`, `paypal_completed_payment_transaction_no`, `paypal_approval_paymentID`) VALUES
('haze_payment_0a3bb1f3f97e4c2caca0c12cdfebd77f', '4B072051SJ118592U', 'PAY-4VH44743H7628241GLHYQ5NQ'),
('haze_payment_5fa21f9da3024463ae6059dcaee3987c', NULL, 'PAY-4G052361M6419235JLHZCPOA'),
('haze_payment_e9cd3cd0da7a4c04899148f0aa15b6fe', '3RM26070M1424074K', 'PAY-45D75406NN955633SLHZEQWY');

-- --------------------------------------------------------

--
-- Table structure for table `payments_refunds`
--

CREATE TABLE IF NOT EXISTS `payments_refunds` (
  `paymentRefundID` varchar(255) NOT NULL,
  `paymentDepositID` varchar(255) DEFAULT NULL,
  `status` varchar(25) NOT NULL,
  `processor_message` text NOT NULL,
  `ammount` float NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`paymentRefundID`),
  UNIQUE KEY `payments_refunds_paymentRefundID_uindex` (`paymentRefundID`),
  KEY `payments_refunds_payments_desposits_paymentDepositID_fk` (`paymentDepositID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `payment_methods`
--

CREATE TABLE IF NOT EXISTS `payment_methods` (
  `paymentMethodID` varchar(255) NOT NULL,
  `paymentMethodName` varchar(100) NOT NULL,
  `paymentMethodDescription` text,
  PRIMARY KEY (`paymentMethodID`),
  UNIQUE KEY `payment_methods_paymentMethodID_uindex` (`paymentMethodID`),
  UNIQUE KEY `payment_methods_paymentMethodName_uindex` (`paymentMethodName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `payment_methods`
--

INSERT INTO `payment_methods` (`paymentMethodID`, `paymentMethodName`, `paymentMethodDescription`) VALUES
('onDelivery', 'OnDelivery', 'For OnDelivery Payment Methods'),
('paypal', 'Paypal', 'For Paypal Payment Methods'),
('visa', 'Visa', 'For Visa Payment Methods');

-- --------------------------------------------------------

--
-- Table structure for table `producers_accounts`
--

CREATE TABLE IF NOT EXISTS `producers_accounts` (
  `shoppingCartID` int(11) NOT NULL AUTO_INCREMENT,
  `producerID` varchar(100) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `isEnabled` tinyint(1) NOT NULL DEFAULT '0',
  `last_password_reset_date` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`shoppingCartID`),
  UNIQUE KEY `userID` (`producerID`),
  UNIQUE KEY `producers_accounts_email_uindex` (`email`),
  UNIQUE KEY `producers_accounts_password_uindex` (`password`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=14 ;

--
-- Dumping data for table `producers_accounts`
--

INSERT INTO `producers_accounts` (`shoppingCartID`, `producerID`, `email`, `password`, `created_at`, `updated_at`, `isEnabled`, `last_password_reset_date`) VALUES
(11, 'haze_gr_producer_UUID_608e950d31bf4a9db07cfdeb57cabec5', 'zorzis@gmail.com', '94wj7fBdCYFkFX3wlIYwAXP2qhACIy1uV4nQhp/m5o2I0yGzTmfYZOYotf2z0JkDAViEB4ZsXq1ZaBJfCWgsuvn4Xk/rz4RZ1h2WN0UgMuY+lEWsUgAtN6PJ35YwSRA451gCZY2lOBY2ZE411R6FdxYuJpXlIIL9BsU+M/R1YrY=', '2017-07-05 19:54:11', NULL, 1, NULL),
(12, 'haze_gr_producer_UUID_92c8e9fdcde94632b728d0e2492364f4', 'tsampos@gmail.com', 'tyPp9cba93NahmGlMuOyhBg2qO1gV9mREviJ7UHdZgKpfQ7A0ckL0gmgP1i7eY8pSzHXdbwARG0TyMIQZBIUjkU8Y8F1AA464y/shxZ6+5qdPqmxuk1rv5eGAknYnYt7+KnPhyRy/zC6CToGyT65d+FPW5Vgoqgub+hx8okX64w=', '2017-07-18 11:52:46', '2017-10-07 16:10:41', 1, NULL),
(13, 'haze_gr_producer_UUID_beeb52dbf4fc433996b7fe065710c324', 'rimikis@gmail.com', 'RBmwSHgD1KotBMZfQjsW62nEUWQG+xV5HHS4yaiuCv77DAXOxS2aBdz6LXcYK6pCYpZQAwmR55mGK6V2f1NMYbRJXUXp4K7aiKwOxAehU66R1ayoUXrk2puv5Dnw2dxzjgZqvCAN0pnHUt3ZVzBNH63atjqAaqS2KuaMZikM3bM=', '2017-07-18 11:56:06', '2017-07-18 11:56:41', 1, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `producers_addresses`
--

CREATE TABLE IF NOT EXISTS `producers_addresses` (
  `shoppingCartID` int(11) NOT NULL AUTO_INCREMENT,
  `producerID` varchar(100) NOT NULL,
  `street` varchar(255) DEFAULT NULL,
  `street_number` varchar(25) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `postal_code` varchar(100) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `latitude` varchar(255) DEFAULT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  `floor` varchar(100) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`shoppingCartID`),
  UNIQUE KEY `producers_addresses_id_uindex` (`shoppingCartID`),
  UNIQUE KEY `producers_addresses_producers_profile_producerID_fk` (`producerID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `producers_addresses`
--

INSERT INTO `producers_addresses` (`shoppingCartID`, `producerID`, `street`, `street_number`, `city`, `postal_code`, `state`, `country`, `latitude`, `longitude`, `floor`, `created_at`, `updated_at`) VALUES
(1, 'haze_gr_producer_UUID_608e950d31bf4a9db07cfdeb57cabec5', 'Unnamed Road', '', '', '821 02', 'Chios', 'Greece', '38.2948112', '25.963610399999993', '', '2017-07-10 17:09:55', '2017-09-12 19:33:49'),
(2, 'haze_gr_producer_UUID_92c8e9fdcde94632b728d0e2492364f4', 'Unnamed Road', '', 'Katarraktis', '821 02', 'Chios', 'Greece', '38.2662805', '26.10051450000003', '', '2017-07-18 14:53:55', '2017-10-07 16:11:26'),
(3, 'haze_gr_producer_UUID_beeb52dbf4fc433996b7fe065710c324', 'Eparchiaki Odos Volissou-Parparias', '', 'Agia Markella', '821 03', 'Chios', 'Greece', '38.493692', '25.87350330000004', '', '2017-07-18 14:57:43', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `producers_have_authorities`
--

CREATE TABLE IF NOT EXISTS `producers_have_authorities` (
  `shoppingCartID` int(11) NOT NULL AUTO_INCREMENT,
  `producerID` varchar(100) NOT NULL,
  `authorityID` int(20) NOT NULL,
  PRIMARY KEY (`shoppingCartID`),
  UNIQUE KEY `producers_have_authorities_id_uindex` (`shoppingCartID`),
  KEY `producers_have_authorities_producers_accounts_producerID_fk` (`producerID`),
  KEY `producers_have_authorities_users_authorities_id_fk` (`authorityID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=17 ;

--
-- Dumping data for table `producers_have_authorities`
--

INSERT INTO `producers_have_authorities` (`shoppingCartID`, `producerID`, `authorityID`) VALUES
(14, 'haze_gr_producer_UUID_608e950d31bf4a9db07cfdeb57cabec5', 3),
(15, 'haze_gr_producer_UUID_92c8e9fdcde94632b728d0e2492364f4', 3),
(16, 'haze_gr_producer_UUID_beeb52dbf4fc433996b7fe065710c324', 3);

-- --------------------------------------------------------

--
-- Table structure for table `producers_have_payment_methods`
--

CREATE TABLE IF NOT EXISTS `producers_have_payment_methods` (
  `shoppingCartID` int(11) NOT NULL AUTO_INCREMENT,
  `paymentMethodID` varchar(255) NOT NULL,
  `producerID` varchar(255) NOT NULL,
  `isDeactivated` tinyint(1) NOT NULL DEFAULT '1',
  `deactivated_at` datetime DEFAULT NULL,
  `isTerminated` tinyint(1) NOT NULL DEFAULT '0',
  `terminated_at` datetime DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`shoppingCartID`),
  UNIQUE KEY `producers_have_payment_methods_id_uindex` (`shoppingCartID`),
  KEY `producers_have_payment_methods_producers_accounts_producerID_fk` (`producerID`),
  KEY `producers_have_payment_methods_FK_payment_methods_FK_producers` (`paymentMethodID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=13 ;

--
-- Dumping data for table `producers_have_payment_methods`
--

INSERT INTO `producers_have_payment_methods` (`shoppingCartID`, `paymentMethodID`, `producerID`, `isDeactivated`, `deactivated_at`, `isTerminated`, `terminated_at`, `created_at`, `updated_at`) VALUES
(10, 'paypal', 'haze_gr_producer_UUID_92c8e9fdcde94632b728d0e2492364f4', 0, NULL, 0, NULL, '2017-09-29 19:02:29', NULL),
(11, 'onDelivery', 'haze_gr_producer_UUID_92c8e9fdcde94632b728d0e2492364f4', 0, NULL, 0, NULL, '2017-09-29 19:10:48', NULL),
(12, 'visa', 'haze_gr_producer_UUID_92c8e9fdcde94632b728d0e2492364f4', 0, NULL, 0, NULL, '2017-10-07 16:11:02', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `producers_profile`
--

CREATE TABLE IF NOT EXISTS `producers_profile` (
  `producerID` varchar(100) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`producerID`),
  UNIQUE KEY `producers_profile_producerID_uindex` (`producerID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `producers_profile`
--

INSERT INTO `producers_profile` (`producerID`, `first_name`, `last_name`, `birth_date`, `gender`) VALUES
('haze_gr_producer_UUID_608e950d31bf4a9db07cfdeb57cabec5', 'Zorzis', 'Varkaris', '1986-11-21', 'MALE'),
('haze_gr_producer_UUID_92c8e9fdcde94632b728d0e2492364f4', 'Panagiotis', 'Tsampos', '1987-01-16', 'MALE'),
('haze_gr_producer_UUID_beeb52dbf4fc433996b7fe065710c324', 'Giorgos', 'Rimikis', '1992-04-05', 'MALE');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE IF NOT EXISTS `products` (
  `productID` varchar(100) NOT NULL,
  `product_name` varchar(255) NOT NULL,
  `categoryID` varchar(100) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`productID`),
  UNIQUE KEY `products_productID_uindex` (`productID`),
  KEY `products_products_categories_categoryID_fk` (`categoryID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`productID`, `product_name`, `categoryID`, `created_at`, `updated_at`) VALUES
('haze_gr_Spirit__UID_05185b0c71a54510b3b5ffe15d29ef7a', 'hazeTsamposSample2', 'haze_gr_haze_123', '2017-07-18 14:54:52', NULL),
('haze_gr_Spirit__UID_33541a10439e454dad897d16715041b7', 'hazeRimikisSample1', 'haze_gr_haze_123', '2017-07-18 14:58:14', NULL),
('haze_gr_Spirit__UID_7cd37c91b2294e329027b889c7dc291a', 'hazeTsamposSample1', 'haze_gr_haze_123', '2017-07-18 14:54:26', NULL),
('haze_gr_Spirit__UID_7d4ff57b4d514d468ef956801b00d42a', 'haze Chiotiki me Glukaniso', 'haze_gr_haze_123', '2017-09-13 17:06:41', '2017-10-07 16:11:54'),
('haze_gr_Spirit__UID_8a95f82aa54045e896f25b5ceb4a3530', 'hazeTsamposSample3', 'haze_gr_haze_123', '2017-07-18 14:55:16', NULL),
('haze_gr_Spirit__UID_a5fc6230b56a4e8caf1cd696b67893db', 'hazeRimikisSample2', 'haze_gr_haze_123', '2017-07-18 14:58:46', NULL),
('haze_gr_Spirit__UID_bc874ff9a20a43eda80f762dec8be08c', 'hazeZorzisSample1', 'haze_gr_haze_123', '2017-07-08 14:50:41', NULL),
('haze_gr_Spirit__UID_dfce88d1fdfa427188dab6c64937c283', 'hazeZorzisSample2', 'haze_gr_haze_123', '2017-07-18 14:51:07', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `products_categories`
--

CREATE TABLE IF NOT EXISTS `products_categories` (
  `categoryID` varchar(100) NOT NULL,
  `category_name` varchar(255) NOT NULL,
  `category_description` text,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`categoryID`),
  UNIQUE KEY `products_categories_categoryID_uindex` (`categoryID`),
  UNIQUE KEY `products_categories_category_name_uindex` (`category_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `products_categories`
--

INSERT INTO `products_categories` (`categoryID`, `category_name`, `category_description`, `created_at`, `updated_at`) VALUES
('haze_gr_haze_123', 'haze', 'haze Apostagma', '2017-07-06 22:03:31', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `products_details`
--

CREATE TABLE IF NOT EXISTS `products_details` (
  `product_details_ID` int(11) NOT NULL AUTO_INCREMENT,
  `productID` varchar(255) NOT NULL,
  `product_description` text,
  `sell_price` float DEFAULT NULL,
  PRIMARY KEY (`product_details_ID`),
  UNIQUE KEY `products_details_products_details_ID_uindex` (`product_details_ID`),
  UNIQUE KEY `products_details_productID_uindex` (`productID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=24 ;

--
-- Dumping data for table `products_details`
--

INSERT INTO `products_details` (`product_details_ID`, `productID`, `product_description`, `sell_price`) VALUES
(16, 'haze_gr_Spirit__UID_bc874ff9a20a43eda80f762dec8be08c', 'hazeZorzisSample1 Description', 15),
(17, 'haze_gr_Spirit__UID_dfce88d1fdfa427188dab6c64937c283', 'hazeZorzisSample2 Description', 18),
(18, 'haze_gr_Spirit__UID_7cd37c91b2294e329027b889c7dc291a', 'hazeTsamposSample1Description', 13),
(19, 'haze_gr_Spirit__UID_05185b0c71a54510b3b5ffe15d29ef7a', 'hazeTsamposSample2Description', 15),
(20, 'haze_gr_Spirit__UID_8a95f82aa54045e896f25b5ceb4a3530', 'hazeTsamposSample3Description', 17),
(21, 'haze_gr_Spirit__UID_33541a10439e454dad897d16715041b7', 'hazeRimikisSample1Description', 11),
(22, 'haze_gr_Spirit__UID_a5fc6230b56a4e8caf1cd696b67893db', 'hazeRimikisSample2Description', 16),
(23, 'haze_gr_Spirit__UID_7d4ff57b4d514d468ef956801b00d42a', 'haze chiotiki me glukaniso', 14);

-- --------------------------------------------------------

--
-- Table structure for table `products_stock`
--

CREATE TABLE IF NOT EXISTS `products_stock` (
  `product_stock_ID` int(11) NOT NULL AUTO_INCREMENT,
  `producerID` varchar(100) NOT NULL,
  `productID` varchar(255) NOT NULL,
  `quantity` float NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`product_stock_ID`),
  UNIQUE KEY `producers_stock_producer_stock_ID_uindex` (`product_stock_ID`),
  UNIQUE KEY `producers_stock_productID_uindex` (`productID`),
  KEY `products_stock_producers_profile_producerID_fk` (`producerID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=24 ;

--
-- Dumping data for table `products_stock`
--

INSERT INTO `products_stock` (`product_stock_ID`, `producerID`, `productID`, `quantity`, `created_at`, `updated_at`) VALUES
(16, 'haze_gr_producer_UUID_608e950d31bf4a9db07cfdeb57cabec5', 'haze_gr_Spirit__UID_bc874ff9a20a43eda80f762dec8be08c', 90, '2017-07-08 14:50:41', NULL),
(17, 'haze_gr_producer_UUID_608e950d31bf4a9db07cfdeb57cabec5', 'haze_gr_Spirit__UID_dfce88d1fdfa427188dab6c64937c283', 90, '2017-07-18 14:51:08', NULL),
(18, 'haze_gr_producer_UUID_92c8e9fdcde94632b728d0e2492364f4', 'haze_gr_Spirit__UID_7cd37c91b2294e329027b889c7dc291a', 67, '2017-07-18 14:54:26', NULL),
(19, 'haze_gr_producer_UUID_92c8e9fdcde94632b728d0e2492364f4', 'haze_gr_Spirit__UID_05185b0c71a54510b3b5ffe15d29ef7a', 94, '2017-07-18 14:54:52', NULL),
(20, 'haze_gr_producer_UUID_92c8e9fdcde94632b728d0e2492364f4', 'haze_gr_Spirit__UID_8a95f82aa54045e896f25b5ceb4a3530', 45, '2017-07-18 14:55:16', NULL),
(21, 'haze_gr_producer_UUID_beeb52dbf4fc433996b7fe065710c324', 'haze_gr_Spirit__UID_33541a10439e454dad897d16715041b7', 125, '2017-07-18 14:58:14', NULL),
(22, 'haze_gr_producer_UUID_beeb52dbf4fc433996b7fe065710c324', 'haze_gr_Spirit__UID_a5fc6230b56a4e8caf1cd696b67893db', 90, '2017-07-18 14:58:46', NULL),
(23, 'haze_gr_producer_UUID_92c8e9fdcde94632b728d0e2492364f4', 'haze_gr_Spirit__UID_7d4ff57b4d514d468ef956801b00d42a', 67, '2017-09-13 17:06:41', '2017-10-07 16:11:54');

-- --------------------------------------------------------

--
-- Table structure for table `users_authorities`
--

CREATE TABLE IF NOT EXISTS `users_authorities` (
  `shoppingCartID` int(11) NOT NULL AUTO_INCREMENT,
  `authority_name` varchar(100) NOT NULL,
  PRIMARY KEY (`shoppingCartID`),
  UNIQUE KEY `users_authorities_authority_name_uindex` (`authority_name`),
  UNIQUE KEY `users_authorities_id_uindex` (`shoppingCartID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `users_authorities`
--

INSERT INTO `users_authorities` (`shoppingCartID`, `authority_name`) VALUES
(1, 'ROLE_ADMIN'),
(4, 'ROLE_CLIENT'),
(2, 'ROLE_DB'),
(3, 'ROLE_PRODUCER');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `clients_accounts`
--
ALTER TABLE `clients_accounts`
  ADD CONSTRAINT `clients_accounts_clients_profile_clientID_fk` FOREIGN KEY (`clientID`) REFERENCES `clients_profile` (`clientID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `clients_have_addresses`
--
ALTER TABLE `clients_have_addresses`
  ADD CONSTRAINT `clients_have_addresses_clients_accounts_clientID_fk` FOREIGN KEY (`clientID`) REFERENCES `clients_accounts` (`clientID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `clients_have_addresses_clients_addresses_id_fk` FOREIGN KEY (`addressID`) REFERENCES `clients_addresses` (`shoppingCartID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `clients_have_authorities`
--
ALTER TABLE `clients_have_authorities`
  ADD CONSTRAINT `clients_have_authorities_clients_accounts_clientID_fk` FOREIGN KEY (`clientID`) REFERENCES `clients_accounts` (`clientID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `clients_have_authorities_users_authorities_id_fk` FOREIGN KEY (`authorityID`) REFERENCES `users_authorities` (`shoppingCartID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_orders_status_codes_ref_order_status_code_fk` FOREIGN KEY (`order_status_code`) REFERENCES `orders_status_codes_ref` (`order_status_code`);

--
-- Constraints for table `orders_have_clients_producers`
--
ALTER TABLE `orders_have_clients_producers`
  ADD CONSTRAINT `orders_have_clients_producers_clients_accounts_clientID_fk` FOREIGN KEY (`clientID`) REFERENCES `clients_accounts` (`clientID`),
  ADD CONSTRAINT `orders_have_clients_producers_orders_orderID_fk` FOREIGN KEY (`orderID`) REFERENCES `orders` (`orderID`),
  ADD CONSTRAINT `orders_have_clients_producers_producers_accounts_producerID_fk` FOREIGN KEY (`producerID`) REFERENCES `producers_accounts` (`producerID`);

--
-- Constraints for table `orders_have_products`
--
ALTER TABLE `orders_have_products`
  ADD CONSTRAINT `orders_have_products_order_products_order_product_id_fk` FOREIGN KEY (`order_product_ID`) REFERENCES `order_products` (`order_product_id`),
  ADD CONSTRAINT `orders_have_products_orders_orderID_fk` FOREIGN KEY (`orderID`) REFERENCES `orders` (`orderID`);

--
-- Constraints for table `payments_approvals`
--
ALTER TABLE `payments_approvals`
  ADD CONSTRAINT `payments_approvals_payments_paymentID_fk` FOREIGN KEY (`paymentID`) REFERENCES `payments` (`paymentID`);

--
-- Constraints for table `payments_deposits`
--
ALTER TABLE `payments_deposits`
  ADD CONSTRAINT `payments_desposits_payments_approvals_paymentApprovalID_fk` FOREIGN KEY (`paymentApprovalID`) REFERENCES `payments_approvals` (`paymentApprovalID`);

--
-- Constraints for table `payments_onDelivery`
--
ALTER TABLE `payments_onDelivery`
  ADD CONSTRAINT `payments_onDelivery_payments_paymentID_fk` FOREIGN KEY (`paymentID`) REFERENCES `payments` (`paymentID`);

--
-- Constraints for table `payments_paypal`
--
ALTER TABLE `payments_paypal`
  ADD CONSTRAINT `payments_paypal_alt_payments_paymentID_fk` FOREIGN KEY (`paymentID`) REFERENCES `payments` (`paymentID`);

--
-- Constraints for table `payments_refunds`
--
ALTER TABLE `payments_refunds`
  ADD CONSTRAINT `payments_refunds_payments_desposits_paymentDepositID_fk` FOREIGN KEY (`paymentDepositID`) REFERENCES `payments_deposits` (`paymentDepositID`);

--
-- Constraints for table `producers_accounts`
--
ALTER TABLE `producers_accounts`
  ADD CONSTRAINT `producers_accounts_producers_profile_producerID_fk` FOREIGN KEY (`producerID`) REFERENCES `producers_profile` (`producerID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `producers_addresses`
--
ALTER TABLE `producers_addresses`
  ADD CONSTRAINT `producers_addresses_producers_profile_producerID_fk` FOREIGN KEY (`producerID`) REFERENCES `producers_profile` (`producerID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `producers_have_authorities`
--
ALTER TABLE `producers_have_authorities`
  ADD CONSTRAINT `producers_have_authorities_producers_accounts_producerID_fk` FOREIGN KEY (`producerID`) REFERENCES `producers_accounts` (`producerID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `producers_have_authorities_users_authorities_id_fk` FOREIGN KEY (`authorityID`) REFERENCES `users_authorities` (`shoppingCartID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `producers_have_payment_methods`
--
ALTER TABLE `producers_have_payment_methods`
  ADD CONSTRAINT `producers_have_payment_methods_FK_payment_methods_FK_producers` FOREIGN KEY (`paymentMethodID`) REFERENCES `payment_methods` (`paymentMethodID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `producers_have_payment_methods_producers_accounts_producerID_fk` FOREIGN KEY (`producerID`) REFERENCES `producers_accounts` (`producerID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `products_products_categories_categoryID_fk` FOREIGN KEY (`categoryID`) REFERENCES `products_categories` (`categoryID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `products_details`
--
ALTER TABLE `products_details`
  ADD CONSTRAINT `products_details_products_productID_fk` FOREIGN KEY (`productID`) REFERENCES `products` (`productID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `products_stock`
--
ALTER TABLE `products_stock`
  ADD CONSTRAINT `products_stock_producers_profile_producerID_fk` FOREIGN KEY (`producerID`) REFERENCES `producers_profile` (`producerID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `products_stock_products_productID_fk` FOREIGN KEY (`productID`) REFERENCES `products` (`productID`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
