-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 25-Maio-2019 às 20:28
-- Versão do servidor: 10.1.37-MariaDB
-- versão do PHP: 7.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `grupo15_culture_management`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `create_administrator_role` ()  BEGIN

        CREATE ROLE IF NOT EXISTS administrator_role;

        GRANT ALL PRIVILEGES ON *.* TO administrator_role WITH GRANT OPTION;

        GRANT EXECUTE ON PROCEDURE grupo15_culture_management.create_researcher to administrator_role WITH GRANT OPTION;
        
        GRANT EXECUTE ON PROCEDURE grupo15_culture_management.show_all_cultures to administrator_role WITH GRANT OPTION;
        
        GRANT EXECUTE ON PROCEDURE grupo15_culture_management.show_all_variables to administrator_role WITH GRANT OPTION;
        
        GRANT administrator_role TO admin@localhost IDENTIFIED by 'admin';
        
        
        SET DEFAULT ROLE administrator_role FOR admin@localhost;

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `create_researcher` (IN `email` VARCHAR(50), IN `password` VARCHAR(32), IN `name` VARCHAR(100), IN `username_db` VARCHAR(80), IN `title` VARCHAR(100))  BEGIN
		declare researcher_id int;
        
		set @exec_querry = concat('CREATE USER IF NOT EXISTS ''', username_db, '''@''localhost'' ', 'IDENTIFIED BY  ''', password, '''');
        prepare query from @exec_querry;
        execute query;
        
        CREATE ROLE IF NOT EXISTS researcher_role;
        
        GRANT SELECT, INSERT, UPDATE, DELETE ON grupo15_culture_management.measurements to researcher_role;
        
        GRANT SELECT, INSERT, UPDATE, DELETE ON grupo15_culture_management.variable_boundaries to researcher_role;
        
         GRANT SELECT ON grupo15_culture_management.system to researcher_role;
         
         GRANT SELECT ON grupo15_culture_management.light_measurements to researcher_role;
         
         GRANT SELECT ON grupo15_culture_management.temperature_measurements to researcher_role;
         
         GRANT EXECUTE ON PROCEDURE grupo15_culture_management.get_cultures to researcher_role;
         
         GRANT EXECUTE ON PROCEDURE grupo15_culture_management.load_researcher_data to researcher_role;
         
         GRANT EXECUTE ON PROCEDURE grupo15_culture_management.show_all_variables to researcher_role;
         
         GRANT EXECUTE ON PROCEDURE grupo15_culture_management.show_all_cultures to researcher_role;
         
          GRANT EXECUTE ON PROCEDURE grupo15_culture_management.get_researcher_bio to researcher_role;
          
          GRANT EXECUTE ON PROCEDURE grupo15_culture_management.get_researcher_variable_boundaries to researcher_role;
          
          GRANT EXECUTE ON PROCEDURE grupo15_culture_management.get_culture_by_id to researcher_role;
          
          GRANT EXECUTE ON PROCEDURE grupo15_culture_management.update_researcher to researcher_role;
        
        set @exec_querry = concat('GRANT researcher_role TO ''', username_db, '''@''localhost'' ');
        prepare query from @exec_querry;
        execute query;
        
		set @exec_querry = concat('SET DEFAULT ROLE researcher_role FOR ''', username_db, '''@''localhost''');
        prepare query from @exec_querry;
        execute query;
        
        SELECT COALESCE(MAX(employee_id), 0)+1 into researcher_id FROM researcher;
        
        insert into researcher(employee_id, name, email, username_db, title) 
                values(researcher_id, name, email, username_db, title);

END$$

CREATE DEFINER=`admin`@`localhost` PROCEDURE `get_cultures` ()  BEGIN
		select * from culture where culture.researcher = TRIM(TRAILING '@localhost' from USER());

END$$

CREATE DEFINER=`admin`@`localhost` PROCEDURE `get_culture_by_id` (IN `cultureId` INT)  NO SQL
SELECT * FROM CULTURE WHERE culture.culture_id = cultureId$$

CREATE DEFINER=`admin`@`localhost` PROCEDURE `get_researcher_bio` ()  NO SQL
BEGIN
		select * from researcher where researcher.username_db = TRIM(TRAILING '@localhost' from USER());

END$$

CREATE DEFINER=`admin`@`localhost` PROCEDURE `get_researcher_variable_boundaries` ()  NO SQL
select * 
from variable_boundaries vb, culture c, variable v
where vb.variable_id = v.variable_id
AND vb.culture_id = c.culture_id
AND c.researcher = TRIM(TRAILING '@localhost' from USER())$$

CREATE DEFINER=`admin`@`localhost` PROCEDURE `load_researcher_data` ()  BEGIN
		select c.culture_id, c.culture_name, c.culture_description, c.researcher, v.variable_id, v.variable_name, vb.lower_bound, vb.upper_bound, m.measurement_id, m.value, m.timestamp, r.employee_id, r.name, r.email, r.username_db, r.title
        FROM measurements m
        JOIN variable_boundaries vb
        ON vb.variable_id = m.variable_id
        JOIN variable v
        ON v.variable_id = vb.variable_id
        JOIN culture c
        ON c.culture_id = vb.culture_id
        JOIN researcher r
        ON c.researcher = r.username_db
		AND c.culture_id = m.culture_id
        AND c.researcher = TRIM(TRAILING '@localhost' from USER());

END$$

CREATE DEFINER=`admin`@`localhost` PROCEDURE `save_measurement` (IN `id_cultura` INT, IN `id_variavel` INT, IN `valor_medido` DECIMAL(8,2))  BEGIN
		insert into measurements(culture_id, variable_id, timestamp, value) values (id_cultura, id_variavel, NOW(), valor_medido);
	END$$

CREATE DEFINER=`admin`@`localhost` PROCEDURE `show_all_cultures` ()  NO SQL
SELECT * FROM culture$$

CREATE DEFINER=`admin`@`localhost` PROCEDURE `show_all_variables` ()  NO SQL
SELECT *
FROM variable$$

CREATE DEFINER=`admin`@`localhost` PROCEDURE `update_researcher` (IN `researcher_name` VARCHAR(100), IN `researcher_email` VARCHAR(50), IN `researcher_password` VARCHAR(32))  NO SQL
begin
	UPDATE researcher SET name=researcher_name, email=researcher_email WHERE researcher.username_db=TRIM(TRAILING '@localhost' from USER());
   
    END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estrutura da tabela `culture`
--

CREATE TABLE `culture` (
  `culture_id` int(11) NOT NULL,
  `culture_name` varchar(100) NOT NULL,
  `culture_description` text,
  `researcher` varchar(80) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `culture`
--

INSERT INTO `culture` (`culture_id`, `culture_name`, `culture_description`, `researcher`) VALUES
(1, 'tomates', 'tomates cherry', 'kama'),
(2, 'batatas', 'batata doce', 'kama'),
(3, 'feijao', 'feijao verde', 'tt'),
(6, 'tomate', 'tomates cherry', 'tt'),
(7, 'abobora', 'abobora menina', 'bfaias'),
(10, 'cenouras', 'cenouras bebes', 'bfaias');

-- --------------------------------------------------------

--
-- Estrutura da tabela `light_measurements`
--

CREATE TABLE `light_measurements` (
  `measurement_id` int(11) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `value` decimal(8,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `light_measurements`
--

INSERT INTO `light_measurements` (`measurement_id`, `timestamp`, `value`) VALUES
(1, '2019-05-12 23:00:00', '1500.00'),
(2, '2019-05-12 23:00:03', '1600.00'),
(3, '2019-05-13 03:18:50', '2200.00'),
(4, '2019-05-13 03:18:57', '1510.00');

-- --------------------------------------------------------

--
-- Estrutura da tabela `measurements`
--

CREATE TABLE `measurements` (
  `measurement_id` int(11) NOT NULL,
  `culture_id` int(11) NOT NULL,
  `variable_id` int(11) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `value` decimal(8,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `measurements`
--

INSERT INTO `measurements` (`measurement_id`, `culture_id`, `variable_id`, `timestamp`, `value`) VALUES
(1, 1, 2, '2019-05-12 14:09:02', '15.30'),
(4, 1, 2, '2019-05-13 04:58:44', '15.30'),
(5, 3, 2, '2019-05-13 04:58:44', '15.10'),
(6, 3, 2, '2018-05-13 04:05:00', '5.90'),
(7, 6, 2, '2019-05-13 19:50:35', '3.00'),
(8, 3, 2, '2019-05-13 19:20:20', '4.00'),
(9, 3, 2, '2019-05-13 20:21:08', '10.00'),
(10, 3, 2, '2019-05-13 22:42:19', '888.80'),
(11, 3, 2, '2019-05-13 22:54:42', '10.00'),
(12, 3, 3, '2019-05-16 08:55:40', '40.00'),
(13, 7, 3, '2019-05-16 20:08:03', '1.00'),
(14, 7, 3, '2019-05-16 20:10:54', '1.00'),
(15, 7, 3, '2019-05-16 20:13:13', '1.00'),
(16, 7, 3, '2019-05-16 20:17:23', '1.00'),
(17, 7, 3, '2019-05-16 20:20:31', '1.00'),
(18, 7, 3, '2019-05-15 23:00:00', '20.00'),
(19, 7, 3, '2019-05-16 20:45:35', '6.00'),
(20, 10, 4, '2019-05-16 21:23:31', '5.00'),
(21, 10, 4, '2019-05-16 21:23:59', '0.00');

--
-- Acionadores `measurements`
--
DELIMITER $$
CREATE TRIGGER `measurements_before_delete` BEFORE DELETE ON `measurements` FOR EACH ROW begin

declare researcher_t varchar(80);

select distinct researcher into researcher_t from culture
join variable_boundaries using(culture_id)
where culture.culture_id = old.culture_id;


if ((researcher_t <> TRIM(TRAILING '@localhost' from USER())) and (TRIM(TRAILING '@localhost' from USER()) <> 'admin')) then
	signal sqlstate '45000' -- "unhandled user-defined exception"
	-- Here comes your custom error message that will be returned by MySQL
	set message_text = 'Não é possível apagar registos relativos a esta cultura pois não é o responsável';
end if;

end
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `measurements_before_insert` BEFORE INSERT ON `measurements` FOR EACH ROW begin

declare researcher_t varchar(80);

select distinct researcher into researcher_t from culture
join variable_boundaries using(culture_id)
where culture.culture_id = new.culture_id;


if ((researcher_t <> TRIM(TRAILING '@localhost' from USER())) and (TRIM(TRAILING '@localhost' from USER()) <> 'admin')) then
	signal sqlstate '45000' -- "unhandled user-defined exception"
	-- Here comes your custom error message that will be returned by MySQL
	set message_text = 'Não é possível inserir registos relativos a esta cultura pois não é o responsável';
end if;

end
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `measurements_before_update` BEFORE UPDATE ON `measurements` FOR EACH ROW begin

declare researcher_t varchar(80);

select distinct researcher into researcher_t from culture
join variable_boundaries using(culture_id)
where culture.culture_id = old.culture_id;


if ((researcher_t <> TRIM(TRAILING '@localhost' from USER())) and (TRIM(TRAILING '@localhost' from USER()) <> 'admin')) then
	signal sqlstate '45000' -- "unhandled user-defined exception"
	-- Here comes your custom error message that will be returned by MySQL
	set message_text = 'Não é possível atualizar registos relativos a esta cultura pois não é o responsável';
end if;

end
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estrutura da tabela `researcher`
--

CREATE TABLE `researcher` (
  `employee_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `email` varchar(50) NOT NULL,
  `username_db` varchar(80) NOT NULL,
  `title` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `researcher`
--

INSERT INTO `researcher` (`employee_id`, `name`, `email`, `username_db`, `title`) VALUES
(8, 'alfredo', 'alfredo', 'alfredo', 'alfredo'),
(10, 'asfd', 'adsf', 'asdf', 'adsf'),
(13, 'Vasco Faia', 'investigadorSIDES@gmail.com', 'bfaias', 'Investigadora'),
(7, 'hugo', 'hugo', 'hugo', 'hugo'),
(6, 'josefina', 'josefina', 'josefina', 'josefina'),
(5, 'josefino', 'josefino', 'josefino', 'josefino'),
(1, 'kama', 'kama', 'kama', 'kama'),
(4, 'leti', 'leti@iscte.pt', 'leti', 'investigadora'),
(12, 'mar', 'mar', 'mar', 'mar'),
(3, 'tomane', 'tomane', 'tomane', 'tomane'),
(2, 'marco alex', 'mav@iscte-iul.pt', 'tt', 'tt'),
(14, 'zcx', 'zxc', 'zcxc', 'zxc');

-- --------------------------------------------------------

--
-- Estrutura da tabela `system`
--

CREATE TABLE `system` (
  `temperature_lower_bound` decimal(8,2) NOT NULL,
  `temperature_upper_bound` decimal(8,2) NOT NULL,
  `light_lower_bound` decimal(8,2) NOT NULL,
  `light_upper_bound` decimal(8,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `system`
--

INSERT INTO `system` (`temperature_lower_bound`, `temperature_upper_bound`, `light_lower_bound`, `light_upper_bound`) VALUES
('1800.00', '3000.00', '9.00', '40.00');

-- --------------------------------------------------------

--
-- Estrutura da tabela `temperature_measurements`
--

CREATE TABLE `temperature_measurements` (
  `measurement_id` int(11) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `value` decimal(8,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `temperature_measurements`
--

INSERT INTO `temperature_measurements` (`measurement_id`, `timestamp`, `value`) VALUES
(1, '2019-05-12 23:00:00', '24.10'),
(2, '2019-05-12 23:00:03', '24.20'),
(3, '2019-05-19 19:52:43', '15.30'),
(4, '2019-05-19 20:00:42', '15.00'),
(5, '2019-05-19 20:00:55', '16.00'),
(6, '2019-05-19 20:01:06', '15.00'),
(7, '2019-05-19 20:01:17', '14.00'),
(8, '2019-05-19 20:01:26', '15.00'),
(9, '2019-05-19 20:01:39', '16.00'),
(10, '2019-05-19 20:09:41', '15.00'),
(11, '2019-05-19 20:09:52', '15.00'),
(12, '2019-05-19 20:09:59', '16.00'),
(13, '2019-05-19 20:10:06', '13.00'),
(14, '2019-05-19 20:10:12', '17.00'),
(15, '2019-05-19 20:10:18', '10.00'),
(16, '2019-05-19 20:15:40', '15.00'),
(17, '2019-05-19 20:15:46', '16.00'),
(18, '2019-05-19 20:15:51', '17.00'),
(19, '2019-05-19 20:30:09', '15.00'),
(20, '2019-05-19 20:30:26', '16.00'),
(21, '2019-05-19 20:30:32', '17.00'),
(22, '2019-05-19 20:37:17', '15.00'),
(23, '2019-05-19 20:37:25', '16.00'),
(24, '2019-05-19 20:37:32', '17.00'),
(25, '2019-05-19 20:52:14', '15.00'),
(26, '2019-05-19 20:52:22', '16.00'),
(27, '2019-05-19 21:00:58', '15.00'),
(28, '2019-05-19 21:16:25', '15.00'),
(29, '2019-05-19 21:26:44', '15.00'),
(30, '2019-05-19 21:26:53', '16.00');

-- --------------------------------------------------------

--
-- Estrutura da tabela `variable`
--

CREATE TABLE `variable` (
  `variable_id` int(11) NOT NULL,
  `variable_name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `variable`
--

INSERT INTO `variable` (`variable_id`, `variable_name`) VALUES
(4, 'acidez'),
(3, 'mercurio'),
(1, 'ph'),
(2, 'salinidade');

-- --------------------------------------------------------

--
-- Estrutura da tabela `variable_boundaries`
--

CREATE TABLE `variable_boundaries` (
  `culture_id` int(11) NOT NULL,
  `variable_id` int(11) NOT NULL,
  `lower_bound` decimal(8,2) NOT NULL,
  `upper_bound` decimal(8,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `variable_boundaries`
--

INSERT INTO `variable_boundaries` (`culture_id`, `variable_id`, `lower_bound`, `upper_bound`) VALUES
(1, 2, '10.50', '20.50'),
(3, 2, '15.30', '25.40'),
(3, 3, '8.00', '9.00'),
(6, 2, '1.00', '10.00'),
(7, 3, '5.00', '10.00'),
(10, 4, '1.00', '10.00');

--
-- Acionadores `variable_boundaries`
--
DELIMITER $$
CREATE TRIGGER `variable_boundaries_before_delete` BEFORE DELETE ON `variable_boundaries` FOR EACH ROW begin

declare researcher_t varchar(80);

select distinct researcher into researcher_t from culture
join variable_boundaries using(culture_id)
where culture.culture_id = old.culture_id;


if ((researcher_t <> TRIM(TRAILING '@localhost' from USER())) and (TRIM(TRAILING '@localhost' from USER()) <> 'admin')) then
	signal sqlstate '45000' -- "unhandled user-defined exception"
	-- Here comes your custom error message that will be returned by MySQL
	set message_text = 'Não é possível apagar registos relativos a esta cultura pois não é o responsável';
end if;

end
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `variable_boundaries_before_insert` BEFORE INSERT ON `variable_boundaries` FOR EACH ROW begin

declare researcher_t varchar(80);

select distinct researcher into researcher_t from culture
join variable_boundaries using(culture_id)
where culture.culture_id = new.culture_id;


if ((researcher_t <> TRIM(TRAILING '@localhost' from USER())) and (TRIM(TRAILING '@localhost' from USER()) <> 'admin')) then
	signal sqlstate '45000' -- "unhandled user-defined exception"
	-- Here comes your custom error message that will be returned by MySQL
	set message_text = 'Não é possível apagar inserir relativos a esta cultura pois não é o responsável';
end if;

end
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `variable_boundaries_before_update` BEFORE UPDATE ON `variable_boundaries` FOR EACH ROW begin

declare researcher_t varchar(80);

select distinct researcher into researcher_t from culture
join variable_boundaries using(culture_id)
where culture.culture_id = old.culture_id;


if ((researcher_t <> TRIM(TRAILING '@localhost' from USER())) and (TRIM(TRAILING '@localhost' from USER()) <> 'admin')) then
	signal sqlstate '45000' -- "unhandled user-defined exception"
	-- Here comes your custom error message that will be returned by MySQL
	set message_text = 'Não é possível atualizar registos relativos a esta cultura pois não é o responsável';
end if;

end
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estrutura da tabela `warning`
--

CREATE TABLE `warning` (
  `id` int(11) NOT NULL,
  `datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `variable_name` varchar(100) NOT NULL,
  `lower_bound` decimal(8,2) NOT NULL,
  `upper_bound` decimal(8,2) NOT NULL,
  `measurement` decimal(8,2) NOT NULL,
  `description` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `culture`
--
ALTER TABLE `culture`
  ADD PRIMARY KEY (`culture_id`),
  ADD UNIQUE KEY `culture_name` (`culture_name`),
  ADD KEY `fk_r` (`researcher`);

--
-- Indexes for table `light_measurements`
--
ALTER TABLE `light_measurements`
  ADD PRIMARY KEY (`measurement_id`);

--
-- Indexes for table `measurements`
--
ALTER TABLE `measurements`
  ADD PRIMARY KEY (`measurement_id`),
  ADD KEY `fk_cv` (`culture_id`,`variable_id`);

--
-- Indexes for table `researcher`
--
ALTER TABLE `researcher`
  ADD PRIMARY KEY (`username_db`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `username_db` (`username_db`),
  ADD UNIQUE KEY `employee_id` (`employee_id`);

--
-- Indexes for table `temperature_measurements`
--
ALTER TABLE `temperature_measurements`
  ADD PRIMARY KEY (`measurement_id`);

--
-- Indexes for table `variable`
--
ALTER TABLE `variable`
  ADD PRIMARY KEY (`variable_id`),
  ADD UNIQUE KEY `variable_name` (`variable_name`);

--
-- Indexes for table `variable_boundaries`
--
ALTER TABLE `variable_boundaries`
  ADD PRIMARY KEY (`culture_id`,`variable_id`),
  ADD KEY `fk_v` (`variable_id`);

--
-- Indexes for table `warning`
--
ALTER TABLE `warning`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `culture`
--
ALTER TABLE `culture`
  MODIFY `culture_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `light_measurements`
--
ALTER TABLE `light_measurements`
  MODIFY `measurement_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `measurements`
--
ALTER TABLE `measurements`
  MODIFY `measurement_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `temperature_measurements`
--
ALTER TABLE `temperature_measurements`
  MODIFY `measurement_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `variable`
--
ALTER TABLE `variable`
  MODIFY `variable_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `warning`
--
ALTER TABLE `warning`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `culture`
--
ALTER TABLE `culture`
  ADD CONSTRAINT `fk_r` FOREIGN KEY (`researcher`) REFERENCES `researcher` (`username_db`) ON UPDATE CASCADE;

--
-- Limitadores para a tabela `measurements`
--
ALTER TABLE `measurements`
  ADD CONSTRAINT `fk_cv` FOREIGN KEY (`culture_id`,`variable_id`) REFERENCES `variable_boundaries` (`culture_id`, `variable_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limitadores para a tabela `variable_boundaries`
--
ALTER TABLE `variable_boundaries`
  ADD CONSTRAINT `fk_c` FOREIGN KEY (`culture_id`) REFERENCES `culture` (`culture_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_v` FOREIGN KEY (`variable_id`) REFERENCES `variable` (`variable_id`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
