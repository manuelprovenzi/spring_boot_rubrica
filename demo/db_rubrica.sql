-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Mar 23, 2024 alle 10:41
-- Versione del server: 10.4.32-MariaDB
-- Versione PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_rubrica`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `contatti`
--

CREATE TABLE `contatti` (
  `id` int(11) NOT NULL,
  `nome` varchar(32) NOT NULL,
  `cognome` varchar(32) NOT NULL,
  `numero` varchar(10) NOT NULL,
  `gruppo` varchar(32) NOT NULL,
  `usernameUtente` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dump dei dati per la tabella `contatti`
--

INSERT INTO `contatti` (`id`, `nome`, `cognome`, `numero`, `gruppo`, `usernameUtente`) VALUES
(2, 'arianna', 'sebellin', '123456', 'ciao', 'manup');

-- --------------------------------------------------------

--
-- Struttura della tabella `utenti`
--

CREATE TABLE `utenti` (
  `username` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `token` varchar(128) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dump dei dati per la tabella `utenti`
--

INSERT INTO `utenti` (`username`, `password`, `token`) VALUES
('manup', '47bce5c74f589f4867dbd57e9ca9f808', 'b74a21e9-db6d-4764-8805-37870b908219');

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `contatti`
--
ALTER TABLE `contatti`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_utente` (`usernameUtente`);

--
-- Indici per le tabelle `utenti`
--
ALTER TABLE `utenti`
  ADD PRIMARY KEY (`username`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `contatti`
--
ALTER TABLE `contatti`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `contatti`
--
ALTER TABLE `contatti`
  ADD CONSTRAINT `user_utente` FOREIGN KEY (`usernameUtente`) REFERENCES `utenti` (`username`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
