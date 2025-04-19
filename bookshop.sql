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
                                       `Id_genre` int(11) NOT NULL AUTO_INCREMENT,
                                       `Genre_name` varchar(20) NOT NULL,
                                       `Genre_description` varchar(100) NOT NULL,
                                       `Number_of_books` int NOT NULL DEFAULT 0,
                                       PRIMARY KEY (`Id_genre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=13;

--
-- Dumping data for table `genre`
--

INSERT INTO `genre` (`Id_genre`,`Genre_name`, `Genre_description`, `Number_of_books`) VALUES
                                                                                          (1, 'біографії', 'коли чуже життя цікавіше', 0),
                                                                                          (2, 'детективи', 'щоб напрягти мізки', 0),
                                                                                          (3, 'жахи', 'щоб життя медом не здавалося', 0),
                                                                                          (4, 'класика', 'завжди актуальна', 0),
                                                                                          (5, 'книги для дітей', 'про котиків і зайчиків', 0),
                                                                                          (6, 'науково-популярна література', 'протидія гниттю мозку', 0),
                                                                                          (7, 'фентезі', 'світ магії та пригод', 0),
                                                                                          (8, 'про саморозвиток', 'успішний успіх', 0),
                                                                                          (9, 'романи', 'для романтиків', 0),
                                                                                          (10, 'трилери', 'щоб серце в п\'яти пішло', 0);

-- --------------------------------------------------------

--
-- Table structure for table `book`
--

CREATE TABLE IF NOT EXISTS `book` (
                                      `ISBN` varchar(30) NOT NULL,
                                       `Image` varchar(255) NOT NULL,
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

INSERT INTO `book` (`ISBN`, `Image`,`Book_name`, `Number_of_pages`, `Type_of_cover`, `Book_language`, `Year_of_publication`, `Weight`, `Height`, `Width`, `Thickness`, `Book_price`, `Number_of_instances`, `Adults_only_status`) VALUES
                                                                                                                                                                                                                              ('978-0-7475-3269-9', 'https://upload.wikimedia.org/wikipedia/uk/c/c5/%D0%93%D0%9F%D0%A4%D0%9A%D0%9F%D0%BE%D1%81%D1%82%D0%B5%D1%80.jpg', 'Гаррі Поттер і філософський камінь', 223, 'М’яка', 'Українська', 1997, 0.35, 19.5, 12.5, 2.7, 250.00, 30, 0), -- Added to fix genre_book reference
                                                                                                                                                                                                                              ('978-0-7432-7356-5', 'https://static.yakaboo.ua/media/cloudflare/product/webp/600x840/w/b/wbfmksgwaj4.jpg','Убити пересмішника', 281, 'Тверда', 'Англійська', 1960, 0.45, 21.5, 14.5, 3.5, 300.00, 12, 0),
                                                                                                                                                                                                                              ('978-1-4028-9462-6', 'https://content1.rozetka.com.ua/goods/images/big/422876821.jpg','Великий Гетсбі', 180, 'Тверда', 'Англійська', 1925, 0.4, 20.5, 13.5, 3.0, 280.00, 18, 0),
                                                                                                                                                                                                                              ('978-1-56619-909-4', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRjaG-BkRQ3W6fNlmYNchCdHUNaeNowg_L_1A&s',  '1984', 328, 'М’яка', 'Англійська', 1949, 0.4, 20.0, 13.0, 2.8, 200.00, 20, 0),
                                                                                                                                                                                                                              ('978-1-56619-909-5', 'https://upload.wikimedia.org/wikipedia/uk/6/66/Staryy_i_more_00.png','Старий і море', 128, 'Тверда', 'Англійська', 1952, 0.3, 18.5, 11.5, 2.0, 150.00, 15, 0),
                                                                                                                                                                                                                              ('978-11-12-67-9-99', 'https://op.ua/images/common/7/8/6/3/61951519dcaae.png','Об\'єктно-орієнтоване програмування', 400, 'Тверда', 'Українська', 2010, 0.5, 20.5, 13.5, 2.5, 200.00, 12, 1), -- Price corrected to 200.00
                                                                                                                                                                                                                              ('978-3-16-148410-1', 'https://readeat.com/storage/app/uploads/public/670/3bb/bf3/6703bbbf352d7552251412.jpg','Гаррі Поттер і філософський камінь', 223, 'М’яка', 'Англійська', 1997, 0.35, 19.5, 12.5, 2.7, 250.00, 30, 0),
                                                                                                                                                                                                                              ('978-3-16-148410-2', 'https://bookchef.ua/upload/iblock/178/178b08674b5836bc01ce123570bb753d.jpg','451 градус за Фаренгейтом', 158, 'М’яка', 'Англійська', 1953, 0.25, 19.0, 12.0, 2.5, 220.00, 22, 1),
                                                                                                                                                                                                                              ('978-5-699-12345-6','https://nashformat.ua/files/products/929760.800x800.jpg', 'Тіні забутих предків', 336, 'Тверда', 'Українська', 2020, 0.45, 20.5, 13.5, 2.5, 250.00, 15, 0),
                                                                                                                                                                                                                              ('978-966-14-8523-4', 'https://bookclub.ua/images/db/goods/61489_122019.jpg','Код да Вінчі', 512, 'Тверда', 'Українська', 2015, 0.55, 21.0, 14.0, 3.2, 300.00, 10, 0);

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
                                                                                                                                                                                                                                                      ('T001', 'Іванов', 'Петро', 'Олександрович', 'Менеджер', 15000.00, '2020-01-15', '1990-05-20', 34, 'Київ', 'Хрещатик', '25', 15, 01001, 'ivanov@gmail.com', 'c8794263eef45f362bd72bab9ded0a9230caa50224eac22fb9d9030cb2cfa85c', '+380671234567'), -- Example bcrypt hash
                                                                                                                                                                                                                                                  ('T002', 'Петрова', 'Ольга', NULL, 'Касир', 12000.00, '2021-03-01', '1995-08-15', 29, 'Львів', 'Свободи', '10', NULL, 79000, 'petrova@gmail.com', 'afe337e9201052dacc735238ff11d9995d11b8d2d720a9cca986ce223e1937d2', '+380661234567'),
                                                                                                                                                                                                                                                  ('T003', 'Сидоренко', 'Іван', 'Миколайович', 'Менеджер', 15500.00, '2019-11-20', '1988-03-10', 35, 'Одеса', 'Дерибасівська', '1', 5, 65000, 'sydorenko@gmail.com', '6103561a86a23c7056ce39f79a0abd115cd30f874ac69a7341fb087b8d2315c8', '+380501234567'),
                                                                                                                                                                                                                                                  ('T004', 'Коваль', 'Марія', 'Іванівна', 'Касир', 10000.00, '2022-05-10', '1992-07-25', 31, 'Харків', 'Сумська', '12', 8, 61000, 'koval@gmail.com', '40a10e420d6ca45275ee74152821cb7b4092ba4024c6f9fb8e9bfc7ea60c274f', '+380931234567'),
                                                                                                                                                                                                                                                  ('T005', 'Гончар', 'Олексій', 'Петрович', 'Касир', 9500.00, '2021-09-15', '1993-11-30', 30, 'Дніпро', 'Карла Маркса', '20', 10, 49000, 'gonchar@gmail.com', '234f8b329494d8511855e16b37b66a6e72aadff431991f126b4dc94f548fc304', '+380671234568'),
                                                                                                                                                                                                                                                  ('T006', 'Мельник', 'Олена', 'Василівна', 'Касир', 9800.00, '2020-02-20', '1991-04-15', 32, 'Львів', 'Городоцька', '5', 3, 79000, 'melnyk@gmail.com', '8c3aee79436e772cde24f941cd1ad670ee2b4adbc30e7f9519fc9bba26e8c5a5', '+380661234568');

-- --------------------------------------------------------

--
-- Table structure for table `receipt`
--




CREATE TABLE IF NOT EXISTS `client_card` (
                                             `ID_number` varchar(32) NOT NULL,
                                             `Surname` varchar(30) NOT NULL,
                                             `First_name` varchar(30) NOT NULL,
                                             `Middle_name` varchar(30) NULL,
                                             `Phone_number` varchar(13) NOT NULL,
                                             `Registration_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                             `Date_of_birth` date NOT NULL,
                                             `Age` int NOT NULL,
                                             `Email_addres` varchar(30) NOT NULL,
                                             `Bonus_number` int NOT NULL,
                                             PRIMARY KEY (`ID_number`),
                                             UNIQUE KEY (`Phone_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `client_card` (`ID_number`, `Surname`, `First_name`, `Middle_name`, `Phone_number`, `Registration_date`, `Date_of_birth`, `Age`, `Email_addres`, `Bonus_number`) VALUES
                                                                                                                                                                                 ('CL001', 'Тралалейло' , 'Тралала', 'Тралалейлович', '+380501234567', '2023-01-15', '1990-05-20', 33, 'tralaleylo@gmail.com', 100),
                                                                                                                                                                                 ('CL002', 'Балеріна', 'Капучіна', 'Сергіївна', '+380671234568', '2023-02-20', '1985-08-12', 38, 'capuchina@ukma.edu.ua', 150),
                                                                                                                                                                                 ('CL003', 'Тунг', 'Тунг', NULL, '+380931234569', '2023-03-10', '1995-11-30', 28, 'mctung@gmail,com', 75);

CREATE TABLE IF NOT EXISTS `receipt` (
                                         `ID_number_of_check` int(11) NOT NULL AUTO_INCREMENT,
                                         `Date_buy` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                         `Sum_of_check` decimal(13,2) NOT NULL,
                                         `User_bonus_number` int(30) NULL,
                                         `ID_number_client` varchar(30) NULL,
                                         `Tab_number_worker` varchar(30) NOT NULL,
                                         PRIMARY KEY (`ID_number_of_check`),
                                         FOREIGN KEY (`ID_number_client`) REFERENCES `client_card`(`ID_number`) ON DELETE NO ACTION ON UPDATE CASCADE,
                                         FOREIGN KEY (`Tab_number_worker`) REFERENCES `worker`(`Tab_number`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=15;

--
-- Dumping data for table `receipt`
--

INSERT INTO `receipt` (`ID_number_of_check`, `Date_buy`, `sum_of_check`, `User_bonus_number`, `ID_number_client`, `Tab_number_worker`) VALUES
                                                                                                                                           (1, '2023-10-01 12:00:00', 150.00, NULL, 'CL001', 'T001'),
                                                                                                                                           (2, '2023-10-02 14:30:00', 200.00, NULL, 'CL003', 'T002'),
                                                                                                                                           (3, '2023-10-03 16:45:00', 100.00, NULL, 'CL002', 'T003');

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


INSERT INTO `instance` (`instance_code`, `ID_number_of_check`, `ISBN_book`) VALUES
                                                                                                                             (1, 1, '978-0-7475-3269-9'),
                                                                                                                             (2, 2, '978-0-7432-7356-5'),
                                                                                                                             (3, 3, '978-1-4028-9462-6'),
                                                                                                                             (4, NULL, '978-1-56619-909-4'),
                                                                                                                             (5, NULL, '978-1-56619-909-5');

-- --------------------------------------------------------

--
-- Table structure for table `genre_book`
--

CREATE TABLE IF NOT EXISTS `genre_book` (
                                            `Book_ISBN` varchar(30) NOT NULL,
                                            `Id_genre` int(11) NOT NULL,
                                            PRIMARY KEY (`Book_ISBN`,`Id_genre`),
                                            FOREIGN KEY (`Book_ISBN`) REFERENCES `book`(`ISBN`) ON DELETE CASCADE ON UPDATE CASCADE,
                                            FOREIGN KEY (`Id_genre`) REFERENCES `genre`(`Id_genre`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `genre_book`
--
INSERT INTO `genre_book` (`Book_ISBN`, `Id_genre`) VALUES
                                                       ('978-0-7475-3269-9', 7),
                                                       ('978-0-7432-7356-5', 9),
                                                       ('978-1-4028-9462-6', 9),
                                                       ('978-1-56619-909-4', 3),
                                                       ('978-1-56619-909-5', 9),
                                                       ('978-11-12-67-9-99', 3),
                                                       ('978-3-16-148410-1', 7),
                                                       ('978-3-16-148410-2', 3),
                                                       ('978-5-699-12345-6', 9),
                                                       ('978-966-14-8523-4', 2);

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
                                        `text` varchar(500) NULL,
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

INSERT INTO `review` (`ordered_number`, `user_name`, `user_email`, `number_of_chars`, `text`, `grade`, `review_date`, `review_status`, `ISBN_book`, `answered_number`, `tab_number_of_worker`) VALUES
                                                                                                                                                                                           (1, 'John Doe', 'john.doe@example.com', 12, 'Крута книжка',5, '2023-10-01', 'approved', '978-5-699-12345-6', NULL, 'T001'),
                                                                                                                                                                                           (2, 'Jane Smith', 'jane.smith@example.com', 21, 'На один раз нормально', 4, '2023-10-02', 'pending', '978-966-14-8523-4', 1, NULL),
                                                                                                                                                                                           (3, 'Alice Johnson', 'alice.johnson@example.com', 100, 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 3, '2023-10-03', 'rejected', '978-1-56619-909-4', NULL, 'T002');

-- --------------------------------------------------------

--
-- Trigger to update `number_of_books` in `genre`
--
-- Drop existing triggers if they exist
DROP TRIGGER IF EXISTS `update_genre_book_count`;
DROP TRIGGER IF EXISTS `update_genre_book_count_delete`;
DROP TRIGGER IF EXISTS `after_book_delete`;

-- Set delimiter for trigger creation
DELIMITER //

-- Trigger to update genre count when a book-genre relationship is added
CREATE TRIGGER `update_genre_book_count`
    AFTER INSERT ON `genre_book`
    FOR EACH ROW
BEGIN
    UPDATE `genre`
    SET `Number_of_books` = (
        SELECT COUNT(*)
        FROM `genre_book`
        WHERE `Id_genre` = NEW.`Id_genre`
    )
    WHERE `Id_genre` = NEW.`Id_genre`;
END//

-- Trigger to update genre count when a book-genre relationship is removed
CREATE TRIGGER `update_genre_book_count_delete`
    AFTER DELETE ON `genre_book`
    FOR EACH ROW
BEGIN
    UPDATE `genre`
    SET `Number_of_books` = (
        SELECT COUNT(*)
        FROM `genre_book`
        WHERE `Id_genre` = OLD.`Id_genre`
    )
    WHERE `Id_genre` = OLD.`Id_genre`;
END//

-- Trigger to update genre count when a book is deleted
DELIMITER //

CREATE TRIGGER `after_book_delete`
    AFTER DELETE ON `book`
    FOR EACH ROW
BEGIN
    -- Update all genres that had this book
    UPDATE `genre` g
    SET g.`Number_of_books` = (
        SELECT COUNT(*)
        FROM `genre_book` gb
        WHERE gb.`Id_genre` = g.`Id_genre`
    );


END//

DELIMITER ;

-- Update initial counts (run once after data is loaded)
UPDATE `genre` g
SET `number_of_books` = (
    SELECT COUNT(*)
    FROM `genre_book` gb
    WHERE gb.`Id_genre` = g.`Id_genre`
);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;