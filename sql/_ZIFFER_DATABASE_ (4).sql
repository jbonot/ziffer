-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jul 05, 2016 at 09:14 PM
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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=53 ;

--
-- Dumping data for table `profile_data`
--

INSERT INTO `profile_data` (`profile_data_id`, `user_name`, `firstName`, `lastName`, `dob`, `german_level`, `description`) VALUES
(1, 'daniel.reyes', 'Daniel', 'Reyes', 'September 12, 1988', 'A2 - Elementary', 'Indian, currently moved to Aachen.\nThe Inquoriggible Optimist'),
(2, 'clarence.carpenter', 'Clarence', 'Carpenter', 'April 1, 1990', 'B1 - Intermediate', 'I am a part time German writer, full time software engineer. I touch people inappropriately. With my words.'),
(3, 'roy.oliver', 'Roy', 'Oliver', 'August 15, 1995', 'B2 - Upper Intermediate', 'Batman. Retired now. Passed on legacy to a kid named Bruce. '),
(4, 'brenda.fields', 'Brenda', 'Fields', 'January 26, 1994', 'A2 - Elementary', 'Student, stand-up comedian, producer and often . . . waiter. Ain''t that the way of things?'),
(5, 'amy.hawkins', 'Amy', 'Hawkins', 'July 3, 1990', 'A1 - Beginner', 'Doctor with patience. Language enthusiastic. Exploring places. Making new friends'),
(6, 'theresa.wheeler', 'Theresa', 'Wheeler', 'August 1, 1987', 'A2 - Elementary', 'Always up for learning new things in life.'),
(7, 'jean.sanchez', 'Jean', 'Sanchez', 'January 15, 1997', 'B1 - Intermediate', 'Nerd. Nature-lover. Philosphical. A human being overall.'),
(8, 'phyllis.cox', 'Phyllis', 'Cox', 'February 14, 1990', 'C1 - Advanced', 'Help me solve the mystery of culture from different places around the world.'),
(9, 'daniel.lawson', 'Daniel', 'Lawson', 'May 12, 1990', 'B1 - Intermediate', 'Filmmaker, think-too-mucher'),
(10, 'paul.burton', 'Paul', 'Burton', 'May 24, 1991', 'B2 - Upper Intermediate', 'Physicist by Birth.... Artist by Choice'),
(11, 'gary.myers', 'Gary', 'Myers', 'May 25, 1987', 'B2 - Upper Intermediate', 'Humour is my armour'),
(12, 'mary.patterson', 'Mary', 'Patterson', 'October 12, 1997', 'B2 - Upper Intermediate', 'If the truth is grey, I will find its color.'),
(13, 'kimberly.jacobs', 'Kimberly', 'Jacobs', 'January 26, 1994', 'A2 - Elementary', 'full time rat racer , techie by profession, musician by heart'),
(14, 'stephen.daniels', 'Stephen', 'Daniels', 'July 3, 1990', 'A2 - Elementary', 'Person. It''s the best thing I can be.'),
(15, 'anna.foster', 'Anna', 'Foster', 'August 1, 1987', 'B1 - Intermediate', 'The more I learn, the more I realize I know nothing/'),
(16, 'lori.sanders', 'Lori', 'Sanders', 'January 15, 1997', 'A2 - Elementary', 'Newbie in German. Looking forward to find partners to practice German.'),
(17, 'evelyn.murray', 'Evelyn', 'Murray', 'February 14, 1990', 'C1 - Advanced', 'Learn something about everything. And everything about something.'),
(18, 'timothy.reynolds', 'Timothy', 'Reynolds', 'May 12, 1990', 'A1 - Beginner', 'Ich wuerde gern mein Deutsch verbeseern,deswegen suche nach Deutschen und ich biete Arabisch'),
(19, 'william.perry', 'William', 'Perry', 'May 24, 1992', 'B1 - Intermediate', 'Hi I''m looking for a partner who can help me in German.'),
(20, 'eric.black', 'Eric', 'Black', 'May 25, 1988', 'C1 - Advanced', ' I m looking for someone to speak and discusss in german, I offer english... but it is ok also monodirectional (only in german), we can have some laughs.'),
(21, 'kenneth.myers', 'Kenneth', 'Myers', 'October 12, 1997', 'A1 - Beginner', 'Hi zusammen ich suche jemand der mir mit mein English hilft und biete Spanisch,Katalanisch und Deutsch'),
(22, 'janet.oliver', 'Janet', 'Oliver', 'January 26, 1994', 'C1 - Advanced', 'Ich komme aus s?dkorea und suche einen Tandempartner. Ich biete koreanisch und Deutsch.'),
(23, 'victor.black', 'Victor', 'Black', 'July 3, 1997', 'C1 - Advanced', 'Hallo zusammen, ich suche jemanden um mein Schwedisch zu verbessern. Ich biete Deutsch und Englisch!\nSchreibt mir doch einfach eine Nachricht.'),
(24, 'bobby.rivera', 'Bobby', 'Rivera', 'September 1, 1987', 'C1 - Advanced', ' Ich bin Bobby und ich m?chte mein Deutsch verbessern. Deshalb suche ich en/eine Tandempartner/in, der/die Deutsch spricht. Ich biete Spanisch und Englisch an.'),
(25, 'barbara.bryant', 'Barbara', 'Bryant', 'February 15, 1997', 'C1 - Advanced', 'ich bin Barbara und suche jemanden um mein English zu verbessern,biete Spanisch und Deutsch '),
(26, 'judith.day', 'Judith', 'Day', 'February 14, 1990', 'B1 - Intermediate', 'ich bin Judith.  Ich biete Spanisch und Portugiesisch an.'),
(27, 'patrick.adams', 'Patrick', 'Adams', 'May 12, 1990', 'B1 - Intermediate', 'ich bin Patrick. Ich biete Spanisch und Portugiesisch an.'),
(28, 'sandra.hayes', 'Sandra', 'Hayes', 'June 24, 1991', 'B2 - Upper Intermediate', 'I am Sandra. I am willing to offer German classes.'),
(29, 'dennis.patterson', 'Dennis', 'Patterson', 'July 25, 1987', 'A2 - Elementary', 'I am looking for set of volunteers who would like to improve their German language.'),
(30, 'rebecca.burns', 'Rebecca', 'Burns', 'March 12, 1997', 'B2 - Upper Intermediate', 'Let us have a fun German learning session over a cup of coffee.'),
(31, 'eric.harvey', 'Eric', 'Harvey', 'January 26, 1994', 'A1 - Beginner', 'Hi guys, I''m Eric. I am from Berlin and I''m a hiker, tripper, full time teacher.'),
(32, 'harold.warren', 'Harold', 'Warren', 'July 3, 1990', 'A2 - Elementary', 'Hello guys, I recently moved to Bonn and I live with some friends in one big apartment, just wanted to say that me and my flatmates accept German learners here, so If you need some lessons in this beautiful and amazing city feel free to get in contact!!'),
(33, 'sandra.willis', 'Sandra', 'Willis', 'August 1, 1987', 'A1 - Beginner', 'Part time actor, full time engineer. Willing to offer German classes.'),
(34, 'judith.garcia', 'Judith', 'Garcia', 'January 15, 1997', 'A2 - Elementary', 'I am a Berliner. Love this new city, looking forward to meet up new people over drinks and learning new language.'),
(35, 'philip.carter', 'Philip', 'Carter', 'February 14, 1990', 'B2 - Upper Intermediate', 'Hi guys, I''m Carter. I am from Berlin and I''m a full time chef at an oriental restaurant.'),
(36, 'walter.andrews', 'Walter', 'Andrews', 'May 12, 1990', 'A2 - Elementary', 'Hi guys, I''m Andrew. I am from Nurnberg and I''m business computer science student.'),
(37, 'jerry.palmer', 'Jerry', 'Palmer', 'May 24, 1991', 'C1 - Advanced', 'Hi all, I am Palmer, I love meeting new people.'),
(38, 'aaron.spencer', 'Aaron', 'Spencer', 'May 25, 1987', 'A2 - Elementary', 'Hi all, this is Aaron, I love playing football. My favorite team is Dortmund. '),
(39, 'christopher.dunn', 'Christopher', 'Dunn', 'October 12, 1997', 'A1 - Beginner', 'Hi, this is Dunn. I love drinking, partying and meeting new people.'),
(40, 'nicole.romero', 'Nicole', 'Romero', 'January 26, 1994', 'C1 - Advanced', 'I am Nicole. I am a consultant at EY.'),
(41, 'arthur.ross', 'Arthur', 'Ross', 'July 3, 1990', 'A2 - Elementary', 'I am Arthur. I am a passionate painter.'),
(42, 'andrea.allen', 'Andrea', 'Allen', 'August 1, 1987', 'A1 - Beginner', 'I am a polygot : I speak multiple European Languages fluently.'),
(43, 'kelly.armstrong', 'Kelly', 'Armstrong', 'February 15, 1997', 'A2 - Elementary', 'I am an avid traveller. Love exploring food, culture and language.'),
(44, 'jose.richards', 'Jose', 'Richards', 'February 14, 1990', 'B1 - Intermediate', 'I am a 9 to 5 IT guy from Spain. Moved to Germany 10 years ago. Willing to offer German classes.'),
(45, 'catherine.henderson', 'Catherine', 'Henderson', 'May 12, 1990', 'B1 - Intermediate', 'I am Catherine. I love dogs. I am an active NGO worker for refugees in Germany.'),
(46, 'roger.martinez', 'Roger', 'Martinez', 'July 22, 1991', 'B2 - Upper Intermediate', 'I am Roger. PhD in Humanity. Looking forward to meet you all over coffee and learn German.'),
(47, 'scott.davis', 'Scott', 'Davis', 'July 5, 1987', 'A1 - Beginner', 'I am Scott, British by birth. German by heart. Love meeting new people.'),
(48, 'anthony.lawson', 'Anthony', 'Lawson', 'October 12, 1997', 'B1 - Intermediate', 'I am Anthony. I currently work in a rock Band called RHCP. '),
(49, 'nicole.carroll', 'Nicole', 'Carroll', 'February 26, 1994', 'B1 - Intermediate', 'Hi all, an active member of NGO who helps children from Syria.'),
(50, 'janice.bryant', 'Janice', 'Bryant', 'July 3, 1990', 'C1 - Advanced', 'Hi, my name is Janice. I am a basket ball lover. Love reading novels. Always happy to offer German sessions.'),
(51, 'jasimwhd', 'Jasim', 'Waheed ', 'May 12, 1989', 'B1 - Intermediate', 'Hi people, I am Jasim from India. I would like to meet new people, learn and exchange cultures.'),
(52, 'jeanine', 'Jeanine', 'Bonot', 'June 11, 1994', 'B2 - Upper Intermediate', 'Hi guys, I am a Canadian girl living in Germany. Looking to offer German language sessions. ');

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=53 ;

--
-- Dumping data for table `user_info`
--

INSERT INTO `user_info` (`user_info_id`, `user_name`, `user_pass`) VALUES
(1, 'daniel.reyes', 'daniel.reyes'),
(2, 'clarence.carpenter', 'clarence.carpenter'),
(3, 'roy.oliver', 'roy.oliver'),
(4, 'brenda.fields', 'brenda.fields'),
(5, 'amy.hawkins', 'amy.hawkins'),
(6, 'theresa.wheeler', 'theresa.wheeler'),
(7, 'jean.sanchez', 'jean.sanchez'),
(8, 'phyllis.cox', 'phyllis.cox'),
(9, 'daniel.lawson', 'daniel.lawson'),
(10, 'paul.burton', 'paul.burton'),
(11, 'gary.myers', 'gary.myers'),
(12, 'mary.patterson', 'mary.patterson'),
(13, 'kimberly.jacobs', 'kimberly.jacobs'),
(14, 'stephen.daniels', 'stephen.daniels'),
(15, 'anna.foster', 'anna.foster'),
(16, 'lori.sanders', 'lori.sanders'),
(17, 'evelyn.murray', 'evelyn.murray'),
(18, 'timothy.reynolds', 'timothy.reynolds'),
(19, 'william.perry', 'william.perry'),
(20, 'eric.black', 'eric.black'),
(21, 'kenneth.myers', 'kenneth.myers'),
(22, 'janet.oliver', 'janet.oliver'),
(23, 'victor.black', 'victor.black'),
(24, 'bobby.rivera', 'bobby.rivera'),
(25, 'barbara.bryant', 'barbara.bryant'),
(26, 'judith.day', 'judith.day'),
(27, 'patrick.adams', 'patrick.adams'),
(28, 'sandra.hayes', 'sandra.hayes'),
(29, 'dennis.patterson', 'dennis.patterson'),
(30, 'rebecca.burns', 'rebecca.burns'),
(31, 'eric.harvey', 'eric.harvey'),
(32, 'harold.warren', 'harold.warren'),
(33, 'sandra.willis', 'sandra.willis'),
(34, 'judith.garcia', 'judith.garcia'),
(35, 'philip.carter', 'philip.carter'),
(36, 'walter.andrews', 'walter.andrews'),
(37, 'jerry.palmer', 'jerry.palmer'),
(38, 'aaron.spencer', 'aaron.spencer'),
(39, 'christopher.dunn', 'christopher.dunn'),
(40, 'nicole.romero', 'nicole.romero'),
(41, 'arthur.ross', 'arthur.ross'),
(42, 'andrea.allen', 'andrea.allen'),
(43, 'kelly.armstrong', 'kelly.armstrong'),
(44, 'jose.richards', 'jose.richards'),
(45, 'catherine.henderson', 'catherine.henderson'),
(46, 'roger.martinez', 'roger.martinez'),
(47, 'scott.davis', 'scott.davis'),
(48, 'anthony.lawson', 'anthony.lawson'),
(49, 'nicole.carroll', 'nicole.carroll'),
(50, 'janice.bryant', 'janice.bryant'),
(51, 'jasimwhd', 'jasimwhd'),
(52, 'jeanine.bonot', 'jeanine.bonot');

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
