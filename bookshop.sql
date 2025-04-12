-- phpMyAdmin SQL Dump
-- version 3.2.4 (example; recommend upgrading to a modern version)
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 07, 2025 (updated to current date)
-- Server version: 5.1.41 (example; recommend upgrading)
-- PHP Version: 5.3.1 (example; recommend upgrading)

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `bookshop`
--



-- --------------------------------------------------------

--
-- Table structure for table `genre`
--

CREATE TABLE IF NOT EXISTS `genre` (
                                       `genre_name` varchar(20) NOT NULL,
                                       `genre_description` varchar(100) NOT NULL,
                                       `number_of_books` int NOT NULL DEFAULT 0,
                                       PRIMARY KEY (`genre_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `genre`
--

INSERT INTO `genre` (`genre_name`, `genre_description`, `number_of_books`) VALUES
                                                                               ('біографії', 'коли чуже життя цікавіше', 0),
                                                                               ('детективи', 'щоб напрягти мізки', 0),
                                                                               ('жахи', 'щоб життя медом не здавалося', 0),
                                                                               ('класика', 'завжди актуальна', 0),
                                                                               ('книги для дітей', 'про котиків і зайчиків', 0),
                                                                               ('науково-популярна література', 'протидія гниттю мозку', 0),
                                                                               ('поезія', 'для поціновувачів', 0),
                                                                               ('про саморозвиток', 'успішний успіх', 0),
                                                                               ('романи', 'для романтиків', 0),
                                                                               ('трилери', 'щоб серце в п\'яти пішло', 0),
                                                                               ('фантастика', 'бо буденний світ набрид', 0),
                                                                               ('фентезі', 'світ магії та пригод', 0);

-- --------------------------------------------------------

--
-- Table structure for table `book`
--

CREATE TABLE IF NOT EXISTS `book` (
                                      `ISBN` varchar(30) NOT NULL,
                                      `Book_name` varchar(50) NOT NULL,
                                      `Number_of_pages` int(11) NOT NULL,
                                      `Type_of_cover` varchar(15) NOT NULL,
                                      `Book_language` varchar(30) NOT NULL,
                                      `Year_of_publication` int(11) NOT NULL,
                                      `Weight` float NOT NULL,
                                      `Height` float NOT NULL,
                                      `Width` float NOT NULL,
                                      `Thickness` float NOT NULL,
                                      `Book_price` decimal(10,2) NOT NULL,
                                      `Number_of_instances` int(11) NOT NULL,
                                      `Adults_only_status` tinyint(1) NOT NULL,
                                      PRIMARY KEY (`ISBN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `book`
--

INSERT INTO `book` (`ISBN`, `Book_name`, `Number_of_pages`, `Type_of_cover`, `Book_language`, `Year_of_publication`, `Weight`, `Height`, `Width`, `Thickness`, `Book_price`, `Number_of_instances`, `Adults_only_status`) VALUES
                                                                                                                                                                                                                              ('978-0-7475-3269-9', 'Гаррі Поттер і філософський камінь', 223, 'М’яка', 'Українська', 1997, 0.35, 19.5, 12.5, 2.7, 250.00, 30, 0), -- Added to fix genre_book reference
                                                                                                                                                                                                                              ('978-0-7432-7356-5', 'Убити пересмішника', 281, 'Тверда', 'Англійська', 1960, 0.45, 21.5, 14.5, 3.5, 300.00, 12, 0),
                                                                                                                                                                                                                              ('978-1-4028-9462-6', 'Великий Гетсбі', 180, 'Тверда', 'Англійська', 1925, 0.4, 20.5, 13.5, 3.0, 280.00, 18, 0),
                                                                                                                                                                                                                              ('978-1-56619-909-4', '1984', 328, 'М’яка', 'Англійська', 1949, 0.4, 20.0, 13.0, 2.8, 200.00, 20, 0),
                                                                                                                                                                                                                              ('978-1-56619-909-5', 'Старий і море', 128, 'Тверда', 'Англійська', 1952, 0.3, 18.5, 11.5, 2.0, 150.00, 15, 0),
                                                                                                                                                                                                                              ('978-11-12-67-9-99', 'Об\'єктно-орієнтоване програмування', 400, 'Тверда', 'Українська', 2010, 0.5, 20.5, 13.5, 2.5, 200.00, 12, 1), -- Price corrected to 200.00
                                                                                                                                                                                                                              ('978-3-16-148410-1', 'Гаррі Поттер і філософський камінь', 223, 'М’яка', 'Англійська', 1997, 0.35, 19.5, 12.5, 2.7, 250.00, 30, 0),
                                                                                                                                                                                                                              ('978-3-16-148410-2', '451 градус за Фаренгейтом', 158, 'М’яка', 'Англійська', 1953, 0.25, 19.0, 12.0, 2.5, 220.00, 22, 1),
                                                                                                                                                                                                                              ('978-5-699-12345-6', 'Тіні забутих предків', 336, 'Тверда', 'Українська', 2020, 0.45, 20.5, 13.5, 2.5, 250.00, 15, 0),
                                                                                                                                                                                                                              ('978-966-14-8523-4', 'Код да Вінчі', 512, 'Тверда', 'Українська', 2015, 0.55, 21.0, 14.0, 3.2, 300.00, 10, 0);

-- --------------------------------------------------------

--
-- Table structure for table `worker`
--

CREATE TABLE IF NOT EXISTS `worker` (
                                        `Tab_number` varchar(30) NOT NULL,
                                        `Surname` varchar(30) NOT NULL,
                                        `First_name` varchar(30) NOT NULL,
                                        `Middle_name` varchar(30) NULL,
                                        `Occupation` varchar(32) NOT NULL,
                                        `Salary` decimal(10,2) NOT NULL,
                                        `Start_working_date` date NOT NULL,
                                        `Date_of_birth` date NOT NULL,
                                        `Age` int(11) NOT NULL,
                                        `City` varchar(30) NOT NULL,
                                        `Street` varchar(50) NOT NULL,
                                        `Building` varchar(5) NOT NULL,
                                        `Flat` int(11) NULL,
                                        `Index` int(11) NOT NULL,
                                        `Email_address` varchar(30) NOT NULL,
                                        `password` varchar(255) NOT NULL, -- Increased length for hashed passwords
                                        `Phone_number` varchar(13) NOT NULL,
                                        PRIMARY KEY (`Tab_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `worker`
--

INSERT INTO `worker` (`Tab_number`, `Surname`, `First_name`, `Middle_name`, `Occupation`, `Salary`, `Start_working_date`, `Date_of_birth`, `Age`, `City`, `Street`, `Building`, `Flat`, `Index`, `Email_address`, `password`, `Phone_number`) VALUES
                                                                                                                                                                                                                                                  ('T001', 'Іванов', 'Петро', 'Олександрович', 'Менеджер', 15000.00, '2020-01-15', '1990-05-20', 34, 'Київ', 'Хрещатик', '25', 15, 01001, 'ivanov@gmail.com', '$2y$10$examplehash1...', '+380671234567'), -- Example bcrypt hash
                                                                                                                                                                                                                                                  ('T002', 'Петрова', 'Ольга', NULL, 'Касир', 12000.00, '2021-03-01', '1995-08-15', 29, 'Львів', 'Свободи', '10', NULL, 79000, 'petrova@gmail.com', '$2y$10$examplehash2...', '+380661234567'),
                                                                                                                                                                                                                                                  ('T003', 'Сидоренко', 'Іван', 'Миколайович', 'Менеджер', 15500.00, '2019-11-20', '1988-03-10', 35, 'Одеса', 'Дерибасівська', '1', 5, 65000, 'sydorenko@gmail.com', '$2y$10$examplehash3...', '+380501234567'),
                                                                                                                                                                                                                                                  ('T004', 'Коваль', 'Марія', 'Іванівна', 'Касир', 10000.00, '2022-05-10', '1992-07-25', 31, 'Харків', 'Сумська', '12', 8, 61000, 'koval@gmail.com', '$2y$10$examplehash4...', '+380931234567'),
                                                                                                                                                                                                                                                  ('T005', 'Гончар', 'Олексій', 'Петрович', 'Касир', 9500.00, '2021-09-15', '1993-11-30', 30, 'Дніпро', 'Карла Маркса', '20', 10, 49000, 'gonchar@gmail.com', '$2y$10$examplehash5...', '+380671234568'),
                                                                                                                                                                                                                                                  ('T006', 'Мельник', 'Олена', 'Василівна', 'Касир', 9800.00, '2020-02-20', '1991-04-15', 32, 'Львів', 'Городоцька', '5', 3, 79000, 'melnyk@gmail.com', '$2y$10$examplehash6...', '+380661234568');

-- --------------------------------------------------------

--
-- Table structure for table `receipt`
--

CREATE TABLE IF NOT EXISTS `receipt` (
                                         `ID_number_of_check` int(11) NOT NULL AUTO_INCREMENT,
                                         `Date_buy` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                         `sum_of_check` decimal(13,2) NOT NULL,
                                         `User_bonus_number` varchar(30) NULL,
                                         `ID_number_client` varchar(30) NULL,
                                         `Tab_number_worker` varchar(30) NOT NULL,
                                         PRIMARY KEY (`ID_number_of_check`),
                                         FOREIGN KEY (`ID_number_client`) REFERENCES `worker`(`Tab_number`) ON DELETE NO ACTION ON UPDATE CASCADE,
                                         FOREIGN KEY (`User_bonus_number`) REFERENCES `worker`(`Tab_number`) ON DELETE NO ACTION ON UPDATE CASCADE,
                                         FOREIGN KEY (`Tab_number_worker`) REFERENCES `worker`(`Tab_number`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=15;

--
-- Dumping data for table `receipt`
--

INSERT INTO `receipt` (`ID_number_of_check`, `Date_buy`, `sum_of_check`, `User_bonus_number`, `ID_number_client`, `Tab_number_worker`) VALUES
                                                                                                                                           (1, '2023-10-01 12:00:00', 150.00, NULL, 'T001', 'T001'),
                                                                                                                                           (2, '2023-10-02 14:30:00', 200.00, NULL, 'T002', 'T002'),
                                                                                                                                           (3, '2023-10-03 16:45:00', 100.00, NULL, NULL, 'T003');

-- --------------------------------------------------------

--
-- Table structure for table `instance`
--

CREATE TABLE IF NOT EXISTS `instance` (
                                          `instance_code` int(11) NOT NULL AUTO_INCREMENT,
                                          `ID_number_of_check` int NULL,
                                          `ISBN_book` varchar(30) NOT NULL,
                                          PRIMARY KEY (`instance_code`),
                                          FOREIGN KEY (`ISBN_book`) REFERENCES `book`(`ISBN`) ON DELETE NO ACTION ON UPDATE CASCADE,
                                          FOREIGN KEY (`ID_number_of_check`) REFERENCES `receipt`(`ID_number_of_check`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=6;

-- --------------------------------------------------------

--
-- Table structure for table `genre_book`
--

CREATE TABLE IF NOT EXISTS `genre_book` (
                                            `Book_ISBN` varchar(30) NOT NULL,
                                            `Genre_name` varchar(20) NOT NULL,
                                            PRIMARY KEY (`Book_ISBN`, `Genre_name`),
                                            FOREIGN KEY (`Book_ISBN`) REFERENCES `book`(`ISBN`) ON DELETE CASCADE ON UPDATE CASCADE,
                                            FOREIGN KEY (`Genre_name`) REFERENCES `genre`(`genre_name`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `genre_book`
--

INSERT INTO `genre_book` (`Book_ISBN`, `Genre_name`) VALUES
                                                         ('978-0-7475-3269-9', 'фентезі'),
                                                         ('978-0-7432-7356-5', 'романи'),
                                                         ('978-1-4028-9462-6', 'романи'),
                                                         ('978-1-56619-909-4', 'фантастика'), -- Changed from 'антиутопія' to existing genre
                                                         ('978-1-56619-909-5', 'романи'),
                                                         ('978-11-12-67-9-99', 'жахи'),
                                                         ('978-3-16-148410-1', 'фентезі'),
                                                         ('978-3-16-148410-2', 'фантастика'),
                                                         ('978-5-699-12345-6', 'романи'),
                                                         ('978-966-14-8523-4', 'детективи');

-- --------------------------------------------------------

--
-- Table structure for table `translator`
--

CREATE TABLE IF NOT EXISTS `translator` (
                                            `Id_translator` int(11) NOT NULL AUTO_INCREMENT,
                                            `Full_name` varchar(100) NOT NULL,
                                            PRIMARY KEY (`Id_translator`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=7;

--
-- Dumping data for table `translator`
--

INSERT INTO `translator` (`Id_translator`, `Full_name`) VALUES
                                                            (1, 'Іван Франко'),
                                                            (2, 'Микола Лукаш'),
                                                            (3, 'Олександр Коваленко'),
                                                            (4, 'Олександр Абліцов'),
                                                            (5, 'Олександр Костенко'),
                                                            (6, 'Сергій Адамович');

-- --------------------------------------------------------

--
-- Table structure for table `book_translator`
--

CREATE TABLE IF NOT EXISTS `book_translator` (
                                                 `ISBN` varchar(30) NOT NULL,
                                                 `Id_translator` int(11) NOT NULL,
                                                 PRIMARY KEY (`ISBN`, `Id_translator`),
                                                 FOREIGN KEY (`ISBN`) REFERENCES `book`(`ISBN`) ON DELETE CASCADE ON UPDATE CASCADE,
                                                 FOREIGN KEY (`Id_translator`) REFERENCES `translator`(`Id_translator`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `book_translator`
--

INSERT INTO `book_translator` (`ISBN`, `Id_translator`) VALUES
                                                            ('978-0-7432-7356-5', 4),
                                                            ('978-1-4028-9462-6', 6),
                                                            ('978-1-56619-909-4', 3),
                                                            ('978-1-56619-909-5', 2),
                                                            ('978-3-16-148410-1', 5),
                                                            ('978-3-16-148410-2', 1),
                                                            ('978-5-699-12345-6', 1),
                                                            ('978-966-14-8523-4', 2);

-- --------------------------------------------------------

--
-- Table structure for table `author`
--

CREATE TABLE IF NOT EXISTS `author` (
                                        `Id_author` int(11) NOT NULL AUTO_INCREMENT,
                                        `Full_name` varchar(100) NOT NULL,
                                        PRIMARY KEY (`Id_author`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=10;

--
-- Dumping data for table `author`
--

INSERT INTO `author` (`Id_author`, `Full_name`) VALUES
                                                    (1, 'Олена Білозір'),
                                                    (2, 'J.K. Rowling'),
                                                    (3, 'Dan Brown'),
                                                    (4, 'George Orwell'),
                                                    (5, 'Ernest Hemingway'),
                                                    (6, 'F. Scott Fitzgerald'),
                                                    (7, 'Ray Bradbury'),
                                                    (8, 'Harper Lee'),
                                                    (9, 'Володимир Бублик');

-- --------------------------------------------------------

--
-- Table structure for table `book_author`
--

CREATE TABLE IF NOT EXISTS `book_author` (
                                             `ISBN` varchar(30) NOT NULL,
                                             `Id_author` int(11) NOT NULL,
                                             PRIMARY KEY (`ISBN`, `Id_author`),
                                             FOREIGN KEY (`ISBN`) REFERENCES `book`(`ISBN`) ON DELETE CASCADE ON UPDATE CASCADE,
                                             FOREIGN KEY (`Id_author`) REFERENCES `author`(`Id_author`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `book_author`
--

INSERT INTO `book_author` (`ISBN`, `Id_author`) VALUES
                                                    ('978-0-7475-3269-9', 2),
                                                    ('978-0-7432-7356-5', 8),
                                                    ('978-1-4028-9462-6', 6),
                                                    ('978-1-56619-909-4', 4),
                                                    ('978-1-56619-909-5', 5),
                                                    ('978-11-12-67-9-99', 9),
                                                    ('978-3-16-148410-1', 2),
                                                    ('978-3-16-148410-2', 7),
                                                    ('978-5-699-12345-6', 1),
                                                    ('978-966-14-8523-4', 3);

-- --------------------------------------------------------

--
-- Table structure for table `review`
--

CREATE TABLE IF NOT EXISTS `review` (
                                        `ordered_number` int(11) NOT NULL AUTO_INCREMENT,
                                        `user_name` varchar(30) NOT NULL,
                                        `user_email` varchar(30) NOT NULL,
                                        `number_of_chars` int(11) NOT NULL,
                                        `grade` int(11) NOT NULL,
                                        `review_date` date NOT NULL,
                                        `review_status` varchar(20) NOT NULL,
                                        `ISBN_book` varchar(30) NOT NULL,
                                        `answered_number` int(11) NULL,
                                        `tab_number_of_worker` varchar(30) NULL,
                                        PRIMARY KEY (`ordered_number`),
                                        FOREIGN KEY (`ISBN_book`) REFERENCES `book`(`ISBN`) ON DELETE CASCADE ON UPDATE CASCADE,
                                        FOREIGN KEY (`answered_number`) REFERENCES `review`(`ordered_number`) ON DELETE CASCADE ON UPDATE CASCADE,
                                        FOREIGN KEY (`tab_number_of_worker`) REFERENCES `worker`(`Tab_number`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=6;

--
-- Dumping data for table `review`
--

INSERT INTO `review` (`ordered_number`, `user_name`, `user_email`, `number_of_chars`, `grade`, `review_date`, `review_status`, `ISBN_book`, `answered_number`, `tab_number_of_worker`) VALUES
                                                                                                                                                                                           (1, 'John Doe', 'john.doe@example.com', 150, 5, '2023-10-01', 'approved', '978-5-699-12345-6', NULL, 'T001'),
                                                                                                                                                                                           (2, 'Jane Smith', 'jane.smith@example.com', 200, 4, '2023-10-02', 'pending', '978-966-14-8523-4', 1, 'T002'),
                                                                                                                                                                                           (3, 'Alice Johnson', 'alice.johnson@example.com', 100, 3, '2023-10-03', 'rejected', '978-1-56619-909-4', NULL, NULL);

-- --------------------------------------------------------

--
-- Trigger to update `number_of_books` in `genre`
--

DELIMITER //
CREATE TRIGGER `update_genre_book_count`
    AFTER INSERT ON `genre_book`
    FOR EACH ROW
BEGIN
    UPDATE `genre`
    SET `number_of_books` = (
        SELECT COUNT(*)
        FROM `genre_book`
        WHERE `Genre_name` = NEW.`Genre_name`
    )
    WHERE `genre_name` = NEW.`Genre_name`;
END//
DELIMITER ;

DELIMITER //
CREATE TRIGGER `update_genre_book_count_delete`
    AFTER DELETE ON `genre_book`
    FOR EACH ROW
BEGIN
    UPDATE `genre`
    SET `number_of_books` = (
        SELECT COUNT(*)
        FROM `genre_book`
        WHERE `Genre_name` = OLD.`Genre_name`
    )
    WHERE `genre_name` = OLD.`Genre_name`;
END//
DELIMITER ;

-- Initial update for `number_of_books`
UPDATE `genre` g
SET `number_of_books` = (
    SELECT COUNT(*)
    FROM `genre_book` gb
    WHERE gb.`Genre_name` = g.`genre_name`
);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;