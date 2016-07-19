-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jul 19, 2016 at 09:40 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `webappdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `event_guests`
--

DROP TABLE IF EXISTS `event_guests`;
CREATE TABLE IF NOT EXISTS `event_guests` (
  `event_guests_id` int(11) NOT NULL AUTO_INCREMENT,
  `event_id` int(100) NOT NULL,
  `user_name_guests` varchar(100) NOT NULL,
  PRIMARY KEY (`event_guests_id`),
  UNIQUE KEY `event_guests_id` (`event_guests_id`),
  UNIQUE KEY `event_guests_id_2` (`event_guests_id`),
  KEY `event_id_2` (`event_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=15 ;

--
-- Dumping data for table `event_guests`
--

INSERT INTO `event_guests` (`event_guests_id`, `event_id`, `user_name_guests`) VALUES
(11, 1, 'daniel.reyes'),
(12, 1, 'roy.oliver'),
(13, 1, 'clarence.carpenter'),
(14, 1, 'patrick.adams');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `event_guests`
--
ALTER TABLE `event_guests`
  ADD CONSTRAINT `event_guests_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `event` (`event_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
