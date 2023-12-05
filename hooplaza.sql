-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 05, 2023 at 04:41 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hooplaza`
--

-- --------------------------------------------------------

--
-- Table structure for table `community`
--

CREATE TABLE `community` (
  `community_id` bigint(20) NOT NULL,
  `community_active` bit(1) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `location_id` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

--
-- Dumping data for table `community`
--

INSERT INTO `community` (`community_id`, `community_active`, `description`, `location_id`, `name`) VALUES
(1, b'1', 'This is an example description.', 'Greensboro', 'UNCG');

-- --------------------------------------------------------

--
-- Table structure for table `community_posts`
--

CREATE TABLE `community_posts` (
  `community_community_id` bigint(20) NOT NULL,
  `posts_post_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `community_request`
--

CREATE TABLE `community_request` (
  `request_id` bigint(20) NOT NULL,
  `body` varchar(255) DEFAULT NULL,
  `created` datetime(6) DEFAULT NULL,
  `location_id` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `request_accepted` bit(1) NOT NULL,
  `request_active` bit(1) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

--
-- Dumping data for table `community_request`
--

INSERT INTO `community_request` (`request_id`, `body`, `created`, `location_id`, `name`, `request_accepted`, `request_active`, `user_id`, `user_name`) VALUES
(1, NULL, '2023-12-05 08:19:38.000000', 'Test', 'Test', b'1', b'0', 1, 'mod');

-- --------------------------------------------------------

--
-- Table structure for table `community_request_seq`
--

CREATE TABLE `community_request_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

--
-- Dumping data for table `community_request_seq`
--

INSERT INTO `community_request_seq` (`next_val`) VALUES
(51);

-- --------------------------------------------------------

--
-- Table structure for table `community_seq`
--

CREATE TABLE `community_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

--
-- Dumping data for table `community_seq`
--

INSERT INTO `community_seq` (`next_val`) VALUES
(51);

-- --------------------------------------------------------

--
-- Table structure for table `post`
--

CREATE TABLE `post` (
  `post_id` bigint(20) NOT NULL,
  `community_id` bigint(20) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `imageurl` varchar(255) DEFAULT NULL,
  `post_date` datetime(6) DEFAULT NULL,
  `tag` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

--
-- Dumping data for table `post`
--

INSERT INTO `post` (`post_id`, `community_id`, `content`, `imageurl`, `post_date`, `tag`, `title`, `user_id`) VALUES
(1, 1, 'This is a test', '', '2023-12-05 08:47:48.000000', NULL, 'Hello World', 3);

-- --------------------------------------------------------

--
-- Table structure for table `post_seq`
--

CREATE TABLE `post_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

--
-- Dumping data for table `post_seq`
--

INSERT INTO `post_seq` (`next_val`) VALUES
(51);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL,
  `active` bit(1) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `last_active_community_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `active`, `email`, `last_active_community_id`, `name`, `password`, `role`) VALUES
(1, b'1', 'mod@mod.com', 1, 'Example Moderator', '$2a$10$DBYoqs2Y.uhH0sPHNRKo9O1rResILjrvdtzRNfT7ScOfmO77GXUhS', 'MOD'),
(2, b'1', 'admin@admin.com', 0, 'Example Admin', '$2a$10$joRrfkPOrFJSmDp9z0/48esy8BjkQoFqsshQmZh8QcYare1jRRn0S', 'ADMIN'),
(3, b'1', 'user@user.com', 1, 'User', '$2a$10$ho45Ozhu4aXnMKnXQRMBL.hzHWSU9WCBA6lT0qfZjdzFYWv0R591y', 'USER'),
(52, b'1', 'example@example.com', 0, 'Example User', '$2a$10$C.60Tnp3G0cz.KPT7I1lDuB9I6sI3ZQ6RtXO3noTDExIPF9CSpZH.', 'USER'),
(53, b'1', 'test@test.com', 1, 'Test User', '$2a$10$rmTcm.HOFwvYI6ys3V7wde889v10.pORllWrqV7E..uNKB57LuADO', 'USER');

-- --------------------------------------------------------

--
-- Table structure for table `user_bookmarks`
--

CREATE TABLE `user_bookmarks` (
  `user_user_id` bigint(20) NOT NULL,
  `bookmarks_post_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

--
-- Dumping data for table `user_bookmarks`
--

INSERT INTO `user_bookmarks` (`user_user_id`, `bookmarks_post_id`) VALUES
(53, 1);

-- --------------------------------------------------------

--
-- Table structure for table `user_communities`
--

CREATE TABLE `user_communities` (
  `user_id` bigint(20) NOT NULL,
  `community_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

--
-- Dumping data for table `user_communities`
--

INSERT INTO `user_communities` (`user_id`, `community_id`) VALUES
(1, 1),
(3, 1),
(53, 1),
(52, 1);

-- --------------------------------------------------------

--
-- Table structure for table `user_moderated_communtities`
--

CREATE TABLE `user_moderated_communtities` (
  `user_id` bigint(20) NOT NULL,
  `community_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

--
-- Dumping data for table `user_moderated_communtities`
--

INSERT INTO `user_moderated_communtities` (`user_id`, `community_id`) VALUES
(1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `user_seq`
--

CREATE TABLE `user_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

--
-- Dumping data for table `user_seq`
--

INSERT INTO `user_seq` (`next_val`) VALUES
(151);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `community`
--
ALTER TABLE `community`
  ADD PRIMARY KEY (`community_id`);

--
-- Indexes for table `community_posts`
--
ALTER TABLE `community_posts`
  ADD UNIQUE KEY `UK_8ov8i6i2a9t0a4xs8gdhrnngq` (`posts_post_id`),
  ADD KEY `FKbypqxi80atdhpatib0mo8nkhd` (`community_community_id`);

--
-- Indexes for table `community_request`
--
ALTER TABLE `community_request`
  ADD PRIMARY KEY (`request_id`);

--
-- Indexes for table `post`
--
ALTER TABLE `post`
  ADD PRIMARY KEY (`post_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `user_bookmarks`
--
ALTER TABLE `user_bookmarks`
  ADD UNIQUE KEY `UK_op4warp131o3k1m4tvv2d1l4g` (`bookmarks_post_id`),
  ADD KEY `FK9g9absyhfjtatdshgupx96two` (`user_user_id`);

--
-- Indexes for table `user_communities`
--
ALTER TABLE `user_communities`
  ADD KEY `FKe8o6w5reahsm6k29hoelas1cm` (`community_id`),
  ADD KEY `FKi1y3i4rud4h7yryekoe58gc64` (`user_id`);

--
-- Indexes for table `user_moderated_communtities`
--
ALTER TABLE `user_moderated_communtities`
  ADD KEY `FKpo58n9mfgia1oh783e1h8qch8` (`community_id`),
  ADD KEY `FKp3c1v14ick1vfe9s56q05ru2f` (`user_id`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `community_posts`
--
ALTER TABLE `community_posts`
  ADD CONSTRAINT `FKbypqxi80atdhpatib0mo8nkhd` FOREIGN KEY (`community_community_id`) REFERENCES `community` (`community_id`),
  ADD CONSTRAINT `FKknbj0aag8x2jj2e2uh3355bqo` FOREIGN KEY (`posts_post_id`) REFERENCES `post` (`post_id`);

--
-- Constraints for table `user_bookmarks`
--
ALTER TABLE `user_bookmarks`
  ADD CONSTRAINT `FK9g9absyhfjtatdshgupx96two` FOREIGN KEY (`user_user_id`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `FKdk0v0cngchya3pjn97k8ghg3i` FOREIGN KEY (`bookmarks_post_id`) REFERENCES `post` (`post_id`);

--
-- Constraints for table `user_communities`
--
ALTER TABLE `user_communities`
  ADD CONSTRAINT `FKe8o6w5reahsm6k29hoelas1cm` FOREIGN KEY (`community_id`) REFERENCES `community` (`community_id`),
  ADD CONSTRAINT `FKi1y3i4rud4h7yryekoe58gc64` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

--
-- Constraints for table `user_moderated_communtities`
--
ALTER TABLE `user_moderated_communtities`
  ADD CONSTRAINT `FKp3c1v14ick1vfe9s56q05ru2f` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `FKpo58n9mfgia1oh783e1h8qch8` FOREIGN KEY (`community_id`) REFERENCES `community` (`community_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
