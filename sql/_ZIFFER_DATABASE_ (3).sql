-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jul 04, 2016 at 04:40 PM
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
-- Table structure for table `event`
--

CREATE TABLE IF NOT EXISTS `event` (
  `event_id` int(100) NOT NULL AUTO_INCREMENT,
  `user_name_host` varchar(100) NOT NULL,
  `german_level_event` varchar(100) NOT NULL,
  `title` varchar(100) NOT NULL,
  `location` varchar(100) NOT NULL,
  `date` varchar(100) NOT NULL,
  `start_time` varchar(100) NOT NULL,
  `end_time` varchar(100) NOT NULL,
  `min_attendees` varchar(100) NOT NULL,
  `max_attendees` varchar(100) NOT NULL,
  `description` varchar(500) NOT NULL,
  PRIMARY KEY (`event_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=14 ;

--
-- Dumping data for table `event`
--

INSERT INTO `event` (`event_id`, `user_name_host`, `german_level_event`, `title`, `location`, `date`, `start_time`, `end_time`, `min_attendees`, `max_attendees`, `description`) VALUES
(1, 'jasimwhd', 'German Level', 'BBQ boys', 'Aachen 52072', 'Jun 29', '13:00', '14:00', '9', '20', 'please come '),
(2, 'jasimwhd', 'B1 - Intermediate', 'as', 'as', 'Jun 30', '10:00', '11:00', '1', '1', ''),
(3, 'jasimwhd', 'A1 - Beginner', 'German classes for Arabs', 'Heusallee Bonn', 'Jun 30', '10:00', '11:00', '9', '20', ''),
(4, 'jasimwhd', 'B2 - Upper Intermediate', 'asa', 'sdd', 'Jun 30', '10:00', '11:00', '1', '1', ''),
(5, 'jasimwhd', 'A2 - Elementary', 'German classes for syrian', 'heusalle', 'Jun 30', '11:00', '12:00', '4', '8', ''),
(6, 'jasimwhd', 'German Level', '', '', 'Jun 30', '12:00', '13:00', '1', '1', ''),
(7, 'jasimwhd', 'German Level', '', '', 'Jun 30', '12:00', '13:00', '1', '1', ''),
(8, 'jasimwhd', 'German Level', 'asasw21212121', '23323', 'Jun 30', '13:00', '14:00', '1', '1', 'dekke'),
(9, 'jasimwhd', 'A2 - Elementary', 'asa', 'hello', 'Jun 30', '13:00', '14:00', '1', '1', ''),
(10, '', '', '', '', '', '', '', '', '', ''),
(11, 'jasimwhd', 'German Level', '', '', 'Jul 2', '10:00', '11:00', '1', '1', ''),
(12, 'jasimwhd', 'German Level', '', '', 'Jun 5', '10:00', '11:00', '1', '1', ''),
(13, 'jasimwhd', 'German Level', '', '', 'Jul 2', '11:00', '12:00', '1', '1', '');

-- --------------------------------------------------------

--
-- Table structure for table `event_guests`
--

CREATE TABLE IF NOT EXISTS `event_guests` (
  `event_guests_id` int(11) NOT NULL AUTO_INCREMENT,
  `event_id` int(100) NOT NULL,
  `user_name_guests` varchar(100) NOT NULL,
  PRIMARY KEY (`event_guests_id`),
  UNIQUE KEY `event_id_3` (`event_id`),
  UNIQUE KEY `event_id_4` (`event_id`),
  KEY `event_id` (`event_id`),
  KEY `event_id_2` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `notifications`
--

CREATE TABLE IF NOT EXISTS `notifications` (
  `notifications_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name_host` varchar(100) NOT NULL,
  `user_name_guests` varchar(100) NOT NULL,
  `event_id` int(100) NOT NULL,
  `message_type` varchar(100) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `read_status` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`notifications_id`),
  KEY `event_id` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `profile`
--

CREATE TABLE IF NOT EXISTS `profile` (
  `user_name` varchar(100) NOT NULL,
  `profile_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_pass` varchar(100) NOT NULL,
  PRIMARY KEY (`profile_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `profile_attributes`
--

CREATE TABLE IF NOT EXISTS `profile_attributes` (
  `profile_attributes_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) NOT NULL,
  `profile_name` varchar(100) NOT NULL,
  `age` varchar(100) NOT NULL,
  `german_level` varchar(100) NOT NULL,
  PRIMARY KEY (`profile_attributes_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `profile_data`
--

CREATE TABLE IF NOT EXISTS `profile_data` (
  `profile_data_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(200) NOT NULL,
  `firstName` varchar(200) NOT NULL,
  `lastName` varchar(200) NOT NULL,
  `dob` varchar(200) NOT NULL,
  `german_level` varchar(200) NOT NULL,
  `description` varchar(500) NOT NULL,
  PRIMARY KEY (`profile_data_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `profile_data`
--

INSERT INTO `profile_data` (`profile_data_id`, `user_name`, `firstName`, `lastName`, `dob`, `german_level`, `description`) VALUES
(1, 'ioannis', 'Johnny', 'Agalli', 'December 6, 1994', 'A1 - Beginner', 'From Greece with Love!');

-- --------------------------------------------------------

--
-- Table structure for table `user_info`
--

CREATE TABLE IF NOT EXISTS `user_info` (
  `user_info_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) NOT NULL,
  `user_pass` varchar(100) NOT NULL,
  PRIMARY KEY (`user_info_id`),
  UNIQUE KEY `user_name` (`user_name`),
  UNIQUE KEY `user_info_id` (`user_info_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `user_info`
--

INSERT INTO `user_info` (`user_info_id`, `user_name`, `user_pass`) VALUES
(1, 'jasimwhd', 'hello'),
(2, 'farhan', 'qwerty'),
(3, '', ''),
(4, 'hellobaby', 'boyzone'),
(5, 'jasim', 'waheed'),
(6, 'jasimwaheed', 'chaman'),
(7, 'jassi', 'dillegayi'),
(8, 'tariqw', 'america'),
(9, 'tariqwaheed', 'seattle'),
(10, 'ioannis', 'mcplab');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `event_guests`
--
ALTER TABLE `event_guests`
  ADD CONSTRAINT `event_guests_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `event` (`event_id`);

--
-- Constraints for table `notifications`
--
ALTER TABLE `notifications`
  ADD CONSTRAINT `notifications_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `event` (`event_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
