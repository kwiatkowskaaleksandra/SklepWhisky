-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 01 Cze 2022, 12:27
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
-- Struktura tabeli dla tabeli `faktury`
--

CREATE TABLE `faktury` (
  `idFaktury` int(10) NOT NULL,
  `idKlienta` int(10) NOT NULL,
  `kwota` float NOT NULL,
  `data` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `faktury`
--

INSERT INTO `faktury` (`idFaktury`, `idKlienta`, `kwota`, `data`) VALUES
(1, 1, 1048, '2022-06-01'),
(2, 2, 1000, '2022-06-01');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `historia`
--

CREATE TABLE `historia` (
  `idHistoria` int(10) NOT NULL,
  `idKlienta` int(10) NOT NULL,
  `idProduktu` int(11) NOT NULL,
  `ilosc` int(11) NOT NULL,
  `idFaktury` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `historia`
--

INSERT INTO `historia` (`idHistoria`, `idKlienta`, `idProduktu`, `ilosc`, `idFaktury`) VALUES
(1, 1, 1, 1, 1),
(2, 1, 10, 1, 1),
(3, 1, 2, 2, 1),
(4, 2, 1, 5, 2);

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
(1, 'Jaroslaw', 'Kot', 'jarek@kot.pl', 'jarek', 'jarek', '1999-04-14'),
(2, 'Marysia', 'Kwiatek', 'marysia@wp.pl', 'marysia', 'marysia', '1999-06-07');

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

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `ogloszenia`
--

CREATE TABLE `ogloszenia` (
  `idOgloszenia` int(10) NOT NULL,
  `data` date NOT NULL,
  `tresc` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `ogloszenia`
--

INSERT INTO `ogloszenia` (`idOgloszenia`, `data`, `tresc`) VALUES
(1, '2022-06-07', 'Zebranie pracownikow'),
(2, '2022-07-19', 'Aktualizacja produktow'),
(3, '2022-06-17', 'Prace serwisowe'),
(4, '2022-06-01', 'Dzien dziecka - dzien wolny');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `opinie`
--

CREATE TABLE `opinie` (
  `idOpinia` int(10) NOT NULL,
  `tresc` varchar(200) DEFAULT NULL,
  `data` date DEFAULT NULL,
  `idKlienta` int(10) DEFAULT NULL,
  `idProduktu` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `opinie`
--

INSERT INTO `opinie` (`idOpinia`, `tresc`, `data`, `idKlienta`, `idProduktu`) VALUES
(1, 'Porzadny produkt', '2022-06-01', 1, 10),
(2, 'Bardzo dobra whisky', '2022-06-01', 1, 1),
(3, 'POLEEECAAAM !', '2022-06-01', 1, 2);

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
(1, 'Jan', 'Kowalski', 'janek@kowal.pl', 'p', 'p');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `produkty`
--

CREATE TABLE `produkty` (
  `idProduktu` int(11) NOT NULL,
  `nazwa` varchar(100) NOT NULL,
  `cena` float NOT NULL,
  `opis` varchar(900) NOT NULL,
  `obraz` varchar(900) NOT NULL,
  `typ` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `produkty`
--

INSERT INTO `produkty` (`idProduktu`, `nazwa`, `cena`, `opis`, `obraz`, `typ`) VALUES
(1, 'Clynelish | 0,7L', 200, 'Clynelish 14 yo to zdecydowanie warty uwagi single malt ze znanego szkockiego regionu Highlands, który został zabutelkowany w mocy 46% ABV. Przedstawia połączenie nut cytrusowych z pikantnym dębem i słodkim akcentem miodowym oraz dymnością. Oferuje także satysfakcjonujący finisz z nutami miodu.', 'clynelish-14-yo.jpg', 'alko'),
(2, 'Glenkinchie | 0,7L', 199, 'Glenkinchie 12 yo to bardzo ciekawy i ceniony single malt pochodzący ze znanego szkockiego regionu Lowlands. Producenci postawili na zabutelkowanie go w mocy 43% ABV. Przede wszystkim, przedstawia świetnie skomponowaną mieszankę nut owocowych, miodowych oraz lekko pikantnego dębu.', 'glenkinchie-12-yo.jpg', 'alko'),
(3, 'Bunnahabhain 0,7L', 256, 'Bunnahabhain whisky 12 yo to bardzo ciekawa edycja dostępna na rynku od 2010 roku. Przede wszystkim, wielu miłośników whisky szanuje ją z uwagi na zawartość alkoholu podniesioną do 46,3% ABV oraz brak dodatku karmelu i filtracji na zimno.', 'Bunnahabhain-12.png', 'alko'),
(4, 'Jack Daniel’s Black Label | 0.7L', 95.88, 'Wersja Black Label niesie ze sobą dużo więcej smakowych doznań niż jej młodszy “czerwony” brat. Przy próbowaniu tej whisky pierwsze, co rzuca się w oczy to kolor, ten jest bursztynowy i przypomina barwą klasyczną przepalankę.', 'jack-daniels-jack-daniels-black-label-gift-box.jpg', 'alko'),
(5, 'Super Nikka Whisky Rare Old | 0,7L', 239.99, 'Ta bardzo ciekawa japońska whisky to blend whisky słodowych z zakładów Yoichi oraz Miyagikyo. Zapach ma kwiatowy, skórzany, dębowy, wyczuwalne są również przyprawy, szałwia oraz owoce. Na języku jest miękka, słodkawa. Słodycz następnie przechodzi w cytrusowy smak, w tle grają cynamon, goździki i słone orzeszki. W finiszu obecne są nuty dębowe, tytoniowe, ale również karmelowe. ', '000271_51062_03.jpg', 'alko'),
(6, 'BECZKA DREWNIANA 3L', 349.99, 'Postarzaj swoje własne alkohole w ekstrawaganckiej 3 litrowej dębowej beczce. W zestawie dodatkowo kranik wraz ze stojakiem i instrukcja dotycząca sezonowania, korzystania z beczki tak, aby służyła długie lata.', 'beczka.PNG', 'beczka'),
(7, 'Beczka dębowa', 421.12, 'Beczka wykonana jest ze starodrzewia dębu (200-letnie dęby) o wysokiej jakości.', 'beczka-do-whisky.png', 'beczka'),
(8, 'DĘBOWE BECZKI DO WHISKY 3L', 210, 'Korpus beczki składa się z zewnętrznej powłoki z drewna sosnowego i podszewki z folii aluminiowej klasy spożywczej, która jest trwalsza i może być używana przez długi czas.', '0_0_productGfx_a6b427f1759c73dad35d92e1e99a9d7e.jpg', 'beczka'),
(9, 'Beczka na whisky drewno sosnowe 3L', 320, 'Drewniana beczka na alkohol , która swym wyglądem przeniesie Cię do Hiszpańskich winnic lub szkockich destylarni whisky. ', 'whisky-timberman-6y-beczka-czarna-ze-zlotymi-obreczami-poj-1-l.jpg', 'beczka'),
(10, 'Szklana beczułka 1L + szklanki', 450, 'Przepiękna, ekskluzywna beczka szklana o pojemności 1L, wykonana z grubego szkła w komplecie z plastikowym kranikiem, korkiem i drewnianą podstawką na 6 kieliszków oraz kieliszkami.', 'beczka1.PNG', 'beczka');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `rabat`
--

CREATE TABLE `rabat` (
  `idRabat` int(11) NOT NULL,
  `kod` varchar(10) NOT NULL,
  `wartosc` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `rabat`
--

INSERT INTO `rabat` (`idRabat`, `kod`, `wartosc`) VALUES
(1, 'RABAT29', 34),
(2, 'RABAT88', 47);

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
(1, 'Zamowienie', 'biuroObslugi@whiskyMadness.com', 'Dzien dobry!\nTwoje zamowienie zostalo przyjete do realizacji. Calkowity koszt to: \'1048.0\'zl. Kod rabatowy na nastepne zakupy: \'RABAT29\'.\nDziekujemy za skorzystanie z naszego sklepu. Zapraszamy ponownie!\nPozdrawiamy WHISKY MADNESS.', '2022-06-01', 1, 1, 'odebranaP'),
(2, 'Zamowienie', 'biuroObslugi@whiskyMadness.com', 'Dzien dobry!\nTwoje zamowienie zostalo przyjete do realizacji. Calkowity koszt to: \'1000.0\'zl. Kod rabatowy na nastepne zakupy: \'RABAT88\'.\nDziekujemy za skorzystanie z naszego sklepu. Zapraszamy ponownie!\nPozdrawiamy WHISKY MADNESS.', '2022-06-01', 1, 2, 'odebranaP'),
(3, 'Zwrot', 'marysia@wp.pl', 'Dzien dobry,\njak mozna zwrocic produkt ?', '2022-06-01', 1, 2, 'odebranaK');

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
(1, NULL, 1, 'p', 'p');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `faktury`
--
ALTER TABLE `faktury`
  ADD PRIMARY KEY (`idFaktury`),
  ADD KEY `fk_fakt_kl` (`idKlienta`);

--
-- Indeksy dla tabeli `historia`
--
ALTER TABLE `historia`
  ADD PRIMARY KEY (`idHistoria`),
  ADD KEY `fk_kl_fakt` (`idKlienta`),
  ADD KEY `his_prod_fk` (`idProduktu`),
  ADD KEY `fk_fakt_hist` (`idFaktury`);

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
-- Indeksy dla tabeli `opinie`
--
ALTER TABLE `opinie`
  ADD PRIMARY KEY (`idOpinia`),
  ADD KEY `op_kl_fk` (`idKlienta`),
  ADD KEY `op_pr_fk` (`idProduktu`);

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
-- Indeksy dla tabeli `rabat`
--
ALTER TABLE `rabat`
  ADD PRIMARY KEY (`idRabat`);

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
  MODIFY `idKlienta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT dla tabeli `koszyk`
--
ALTER TABLE `koszyk`
  MODIFY `idKoszyk` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT dla tabeli `pracownik`
--
ALTER TABLE `pracownik`
  MODIFY `idPracownika` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT dla tabeli `produkty`
--
ALTER TABLE `produkty`
  MODIFY `idProduktu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT dla tabeli `rabat`
--
ALTER TABLE `rabat`
  MODIFY `idRabat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT dla tabeli `wiadomosci`
--
ALTER TABLE `wiadomosci`
  MODIFY `idWiadomosci` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT dla tabeli `zalogowany`
--
ALTER TABLE `zalogowany`
  MODIFY `idZalogowanego` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `faktury`
--
ALTER TABLE `faktury`
  ADD CONSTRAINT `fk_fakt_kl` FOREIGN KEY (`idKlienta`) REFERENCES `klient` (`idKlienta`);

--
-- Ograniczenia dla tabeli `historia`
--
ALTER TABLE `historia`
  ADD CONSTRAINT `fk_fakt_hist` FOREIGN KEY (`idFaktury`) REFERENCES `faktury` (`idFaktury`),
  ADD CONSTRAINT `fk_kl_fakt` FOREIGN KEY (`idKlienta`) REFERENCES `klient` (`idKlienta`),
  ADD CONSTRAINT `his_prod_fk` FOREIGN KEY (`idProduktu`) REFERENCES `produkty` (`idProduktu`);

--
-- Ograniczenia dla tabeli `koszyk`
--
ALTER TABLE `koszyk`
  ADD CONSTRAINT `kosz_kl` FOREIGN KEY (`idKlienta`) REFERENCES `klient` (`idKlienta`),
  ADD CONSTRAINT `kosz_pro` FOREIGN KEY (`idProduktu`) REFERENCES `produkty` (`idProduktu`);

--
-- Ograniczenia dla tabeli `opinie`
--
ALTER TABLE `opinie`
  ADD CONSTRAINT `op_kl_fk` FOREIGN KEY (`idKlienta`) REFERENCES `klient` (`idKlienta`),
  ADD CONSTRAINT `op_pr_fk` FOREIGN KEY (`idProduktu`) REFERENCES `produkty` (`idProduktu`);

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
