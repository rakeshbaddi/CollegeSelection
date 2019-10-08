-- phpMyAdmin SQL Dump
-- version 3.3.9
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Aug 02, 2019 at 01:09 PM
-- Server version: 5.5.8
-- PHP Version: 5.3.5

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `selection`
--

-- --------------------------------------------------------

--
-- Table structure for table `clgdetails`
--

CREATE TABLE IF NOT EXISTS `clgdetails` (
  `clgid` int(5) NOT NULL,
  `clgname` varchar(20) NOT NULL,
  PRIMARY KEY (`clgid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `clgdetails`
--

INSERT INTO `clgdetails` (`clgid`, `clgname`) VALUES
(1, 'R.V. College'),
(2, 'PES University'),
(3, 'Andhra University'),
(4, 'GIT');

-- --------------------------------------------------------

--
-- Table structure for table `coursedetails`
--

CREATE TABLE IF NOT EXISTS `coursedetails` (
  `courseid` int(5) NOT NULL DEFAULT '0',
  `clgid` int(5) DEFAULT NULL,
  `course` varchar(30) DEFAULT NULL,
  `status` int(1) DEFAULT '0',
  PRIMARY KEY (`courseid`),
  KEY `clgid` (`clgid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `coursedetails`
--

INSERT INTO `coursedetails` (`courseid`, `clgid`, `course`, `status`) VALUES
(11, 1, 'Computer Science', 0),
(12, 1, 'Electronics & Communication', 0),
(13, 1, 'Civil Engineering', 0),
(14, 1, 'Electronics & Communication', 0),
(15, 1, 'Electrical Engineering', 0),
(21, 2, 'Computer Science', 0),
(22, 2, 'Electronics & Communication', 0),
(31, 3, 'Computer Science', 0),
(32, 3, 'Mechanical Engineering', 0),
(33, 3, 'Electrical Engineering', 0),
(41, 4, 'Computer Science', 0),
(42, 4, 'Chemical Engineering', 0);

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE IF NOT EXISTS `login` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `rank` int(5) NOT NULL,
  `status` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`username`),
  UNIQUE KEY `rank` (`rank`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`username`, `password`, `rank`, `status`) VALUES
('rank1', '12345', 1, 0),
('rank2', '12345', 2, 0),
('rank3', '12345', 3, 0),
('rank4', '12345', 4, 0);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `coursedetails`
--
ALTER TABLE `coursedetails`
  ADD CONSTRAINT `coursedetails_ibfk_1` FOREIGN KEY (`clgid`) REFERENCES `clgdetails` (`clgid`);
