-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 27 Maj 2022, 21:58
-- Wersja serwera: 10.4.17-MariaDB
-- Wersja PHP: 8.0.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `sklepwhisky`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `klient`
--

CREATE TABLE `klient` (
  `idKlienta` int(11) NOT NULL,
  `imie` varchar(100) NOT NULL,
  `nazwisko` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `login` varchar(100) NOT NULL,
  `haslo` varchar(100) NOT NULL,
  `dataUr` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `klient`
--

INSERT INTO `klient` (`idKlienta`, `imie`, `nazwisko`, `email`, `login`, `haslo`, `dataUr`) VALUES
(1, 'klient', 'klient', 'klient', 'k', 'k', '2022-05-04'),
(2, 'ola', 'ola', 'ola', 'ola', 'ola', '2022-04-27'),
(3, 'ola', 'kola', 'jarek', 'jarek', 'jarek', '2022-04-05'),
(4, 'fr', 'fry', 'ui', 'io', 'io', '2022-04-29'),
(5, 'ju', 'ju', 'ju', 'ju', 'ju', '2022-05-16'),
(6, '12', '12', '12', '12', '12', '2000-05-02');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `koszyk`
--

CREATE TABLE `koszyk` (
  `idKoszyk` int(11) NOT NULL,
  `idProduktu` int(11) DEFAULT NULL,
  `cenaCalosc` float DEFAULT NULL,
  `idKlienta` int(11) DEFAULT NULL,
  `ilosc` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `koszyk`
--

INSERT INTO `koszyk` (`idKoszyk`, `idProduktu`, `cenaCalosc`, `idKlienta`, `ilosc`) VALUES
(1, 3, 232, 1, 1),
(2, 1, 123.22, 1, 1),
(3, 3, 232, 1, 1),
(4, 2, 232, 1, 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `pracownik`
--

CREATE TABLE `pracownik` (
  `idPracownika` int(10) NOT NULL,
  `imie` varchar(100) DEFAULT NULL,
  `nazwisko` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `login` varchar(100) DEFAULT NULL,
  `haslo` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `pracownik`
--

INSERT INTO `pracownik` (`idPracownika`, `imie`, `nazwisko`, `email`, `login`, `haslo`) VALUES
(1, 'ola', 'kwiatek', 'ola@wp.pl', 'p', 'p'),
(2, 'jarek', 'jarek', 'jarek@wp.pl', 'jarek', 'jarek');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `produkty`
--

CREATE TABLE `produkty` (
  `idProduktu` int(11) NOT NULL,
  `nazwa` varchar(100) NOT NULL,
  `cena` float NOT NULL,
  `opis` varchar(200) NOT NULL,
  `obraz` varchar(100) NOT NULL,
  `typ` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `produkty`
--

INSERT INTO `produkty` (`idProduktu`, `nazwa`, `cena`, `opis`, `obraz`, `typ`) VALUES
(1, 'whisky1', 123.22, 'dobra', '1481207121.054.jpg', 'alko'),
(2, 'whiksu2', 232, 'wewe', '1481209209.2814.jpg', 'alko'),
(3, 'whisky3', 232, 'rerere', '3585_wo_dka-de_bowa-p3olska-beczka-czarne-obr.1_0_40_.jpg', 'beczka');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `wiadomosci`
--

CREATE TABLE `wiadomosci` (
  `idWiadomosci` int(10) NOT NULL,
  `temat` varchar(100) DEFAULT NULL,
  `adresat` varchar(100) DEFAULT NULL,
  `tresc` varchar(300) DEFAULT NULL,
  `data` date NOT NULL,
  `idPracownika` int(10) DEFAULT NULL,
  `idKlienta` int(10) DEFAULT NULL,
  `stan` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `wiadomosci`
--

INSERT INTO `wiadomosci` (`idWiadomosci`, `temat`, `adresat`, `tresc`, `data`, `idPracownika`, `idKlienta`, `stan`) VALUES
(1, 'temat1', 'biuroObslugi@whiskyMadness.com', 'trtrtrtr', '2022-05-03', 1, 1, 'odebranaK'),
(2, 'temat2', 'biuroObslugi@whiskyMadness.com', 't2222rtrtrtr', '2022-10-03', 1, 1, 'odebranaK'),
(3, 'kupowanie', 'biuroObslugi@whiskyMadness.com', 'czemu od 18 ?!', '2022-05-25', 1, 1, 'odebranaK'),
(4, 'tgtrg', 'biuroObslugi@whiskyMadness.com', '66u7uujj', '2022-05-25', 1, 1, 'odebranaK'),
(5, 'yhh', 'biuroObslugi@whiskyMadness.com', '665656yjuygvf', '2022-05-25', 1, 1, 'odebranaP'),
(6, 'yhh', 'biuroObslugi@whiskyMadness.com', '665656yjuygvf', '2022-05-25', 2, 1, 'odebranaP'),
(7, '45tgr', 'biuroObslugi@whiskyMadness.com', 'yrgtfrdswdfghjyuhgere', '2022-05-25', 1, 1, 'odebranaP'),
(8, '45tgr', 'biuroObslugi@whiskyMadness.com', 'yrgtfrdswdfghjyuhgere', '2022-05-25', 2, 1, 'odebranaK'),
(9, 'RE: 45tgr', 'biuroObslugi@whiskyMadness.com', 'yrgtfrdswdfghjyuhgere', '2022-05-25', 1, 1, 'odebranaK'),
(10, 'RE: 45tgr', 'biuroObslugi@whiskyMadness.com', 'yrgtfrdswdfghjyuhgere', '2022-05-25', 2, 1, 'odebranaK'),
(11, 'RE: yhh', 'biuroObslugi@whiskyMadness.com', 'blablabla', '2022-05-25', 1, 1, 'odebranaK'),
(12, 'RE: yhh', 'biuroObslugi@whiskyMadness.com', 'blablabla', '2022-05-25', 2, 1, 'odebranaK'),
(13, 'RE: yhh', 'biuroObslugi@whiskyMadness.com', 'tgerrgthyujoiluyjhgtrews', '2022-05-25', 1, 1, 'odebranaK'),
(14, 'RE: yhh', 'biuroObslugi@whiskyMadness.com', 'tgerrgthyujoiluyjhgtrews', '2022-05-25', 2, 1, 'odebranaK');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `zalogowany`
--

CREATE TABLE `zalogowany` (
  `idZalogowanego` int(10) NOT NULL,
  `idKlienta` int(10) DEFAULT NULL,
  `idPracownika` int(10) DEFAULT NULL,
  `login` varchar(100) DEFAULT NULL,
  `haslo` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `zalogowany`
--

INSERT INTO `zalogowany` (`idZalogowanego`, `idKlienta`, `idPracownika`, `login`, `haslo`) VALUES
(1, 1, NULL, 'k', 'k');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `klient`
--
ALTER TABLE `klient`
  ADD PRIMARY KEY (`idKlienta`);

--
-- Indeksy dla tabeli `koszyk`
--
ALTER TABLE `koszyk`
  ADD PRIMARY KEY (`idKoszyk`),
  ADD KEY `kosz_pro` (`idProduktu`),
  ADD KEY `kosz_kl` (`idKlienta`);

--
-- Indeksy dla tabeli `pracownik`
--
ALTER TABLE `pracownik`
  ADD PRIMARY KEY (`idPracownika`);

--
-- Indeksy dla tabeli `produkty`
--
ALTER TABLE `produkty`
  ADD PRIMARY KEY (`idProduktu`);

--
-- Indeksy dla tabeli `wiadomosci`
--
ALTER TABLE `wiadomosci`
  ADD PRIMARY KEY (`idWiadomosci`),
  ADD KEY `fk_kl_wiad` (`idKlienta`),
  ADD KEY `fk_prac_wiad` (`idPracownika`);

--
-- Indeksy dla tabeli `zalogowany`
--
ALTER TABLE `zalogowany`
  ADD PRIMARY KEY (`idZalogowanego`),
  ADD KEY `fk_prac_zal` (`idPracownika`),
  ADD KEY `fk_kl_zal` (`idKlienta`);

--
-- AUTO_INCREMENT dla zrzuconych tabel
--

--
-- AUTO_INCREMENT dla tabeli `klient`
--
ALTER TABLE `klient`
  MODIFY `idKlienta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT dla tabeli `koszyk`
--
ALTER TABLE `koszyk`
  MODIFY `idKoszyk` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT dla tabeli `pracownik`
--
ALTER TABLE `pracownik`
  MODIFY `idPracownika` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT dla tabeli `produkty`
--
ALTER TABLE `produkty`
  MODIFY `idProduktu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT dla tabeli `wiadomosci`
--
ALTER TABLE `wiadomosci`
  MODIFY `idWiadomosci` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT dla tabeli `zalogowany`
--
ALTER TABLE `zalogowany`
  MODIFY `idZalogowanego` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `koszyk`
--
ALTER TABLE `koszyk`
  ADD CONSTRAINT `kosz_kl` FOREIGN KEY (`idKlienta`) REFERENCES `klient` (`idKlienta`),
  ADD CONSTRAINT `kosz_pro` FOREIGN KEY (`idProduktu`) REFERENCES `produkty` (`idProduktu`);

--
-- Ograniczenia dla tabeli `wiadomosci`
--
ALTER TABLE `wiadomosci`
  ADD CONSTRAINT `fk_kl_wiad` FOREIGN KEY (`idKlienta`) REFERENCES `klient` (`idKlienta`),
  ADD CONSTRAINT `fk_prac_wiad` FOREIGN KEY (`idPracownika`) REFERENCES `pracownik` (`idPracownika`);

--
-- Ograniczenia dla tabeli `zalogowany`
--
ALTER TABLE `zalogowany`
  ADD CONSTRAINT `fk_kl_zal` FOREIGN KEY (`idKlienta`) REFERENCES `klient` (`idKlienta`),
  ADD CONSTRAINT `fk_prac_zal` FOREIGN KEY (`idPracownika`) REFERENCES `pracownik` (`idPracownika`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
