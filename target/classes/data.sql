INSERT INTO `plaats` (`id`, `latitude`, `longitude`, `plaatsnaam`) 
VALUES 	(1, '52.090737', '5.121420', 'Utrecht'), 
		(2, '51.924420', '4.477733', 'Rotterdam'), 
		(3, '53.219383', '6.566502', 'Groningen'), 
		(4, '52.370216', '4.895168', 'Amsterdam'),
		(5, '52.387388', '4.646219', 'Haarlem'),
		(6, '52.109272', '5.180968', 'De Bilt'),
		(7, '51.571915', '4.768323', 'Breda'),
		(8, '52.221537', '6.893662', 'Enschede'),
		(9, '51.969187', '5.665395', 'Wageningen'),
		(10, '52.266075', '6.155217', 'Deventer');
INSERT INTO `categorie` (`id`, `categorienaam`) VALUES(1, 'DANSEN'), (2, 'POLITIEK'), (3, 'ETEN_EN_DRINKEN'), (4, 'FILM'), (5, 'SPELLEN'), (6, 'CREATIEF'), (7, 'MUSEUM'), (8, 'WETENSCHAP'), (9, 'CONCERT'), (10, 'LITERAIR'), (11, 'THEATER'), (12, 'SPORTIEF'), (13, 'ONTSPANNING'), (14, 'FEEST');
INSERT INTO `persoon` (`id`, `achternaam`, `is_active`, `email`, `verjaardag`, `voornaam`, `wachtwoord`, `woonplaats`, `persoon_plaats_id`) VALUES(1, 'van der Vliet', b'1', 'heikevandervliet@hotmail.com', '1985-11-11', 'Heike', 'test', 'De Bilt', 6),
(2, 'van der Vliet', b'1', 'liske@mail.nl', '1988-06-22', 'Liske', 'zus', 'Utrecht', 1),
(3, 'Odenkirchen', b'1', 'danny@mail.com', '1989-03-24', 'Danny', 'geheim', 'Rotterdam', 2),
(4, 'Verstegen', b'1', 'jan@mail.com', '1991-03-18', 'Jan', 'welkom', 'Breda', 7),
(5, 'Buse', b'1', 'siem@hotmail.nl', '1997-06-11', 'Siem', 'broertje', 'Enschede', 8),
(6, 'Buse', b'1', 'peter@mail.com', '1995-08-02', 'Peter', 'broer', 'Wageningen', 9),
(7, 'Ekkers', b'1', 'ella@hotmail.com', '1984-05-02', 'Ella', 'verloskundige', 'Groningen', 3),
(8, 'Bijlstra', b'1', 'dore@mail.com', '1986-11-10', 'Dore', 'nichtje', 'Enschede', 8),
(9, 'Wubbolt', b'1', 'annika@mail.com', '1979-02-21', 'Annika', 'movies', 'Haarlem', 5),
(10, 'Schreiber', b'1', 'roxy@hotmail.nl', '1986-03-18', 'Roxane', 'vriendin', 'Utrecht', 1),
(11, 'Schmidt', b'1', 'dirk@mail.com', '1985-03-14', 'Dirk', 'lief', 'De Bilt', 6),
(12, 'Martens', b'1', 'vera@mail.com', '1986-09-05', 'Vera', 'boek', 'Utrecht', 1);
INSERT INTO `evenement` (`id`, `aanvangs_datum_tijd`, `is_active`, `adres`, `eind_datum_tijd`, `evenement_naam`, `plaats`, `uitnodigingstekst`, `categorie_id`, `evenement_plaats_id`, `organisator_id`) VALUES
(1, '2018-02-16 20:30:00', b'1', 'Neude 1', '2018-02-16 23:00:00', 'Spelletjesavond', 'Utrecht', 'Exploding kittens spelen!', 5, 1, 1),
(2, '2018-03-05 12:30:00', b'1', 'Dorpsstraat 5', '2018-03-05 17:30:00', 'Sauna', 'Deventer', 'Dagje sauna!', 13, 10, 2),
(3, '2018-04-01 19:30:00', b'1', 'Biltstraat 10', '2018-04-01 22:00:00', 'The Shape of Water!', 'Utrecht', 'Film kijken in de bios.', 4, 1, 3),
(4, '2018-02-26 11:00:00', b'1', 'Blaak 15', '2018-02-26 11:45:00', 'Yoga', 'Rotterdam', 'speciaal voor IT-ers!', 12, 2, 4),
(5, '2018-03-18 18:30:00', b'1', 'Vismarkt 20', '2018-03-18 21:00:00', 'Etentje', 'Groningen', 'Uit eten bij de Mexicaan', 3, 3, 5),
(6, '2018-03-18 20:30:00', b'1', 'De Witte Swaen 11', '2018-03-18 22:00:00', 'Boekenclub', 'De Bilt', 'Bespreken van het boek Homo Deus', 10, 6, 6),
(7, '2018-04-08 21:00:00', b'1', 'Groenburgwal 8', '2018-04-08 22:00:00', 'Lezing', 'Amsterdam', 'Lezing over de democratie', 2, 4, 7),
(8, '2018-04-11 20:30:00', b'1', 'Vredenburg 1', '2018-04-11 23:00:00', 'Concert', 'Utrecht', 'Rock en roll bandje', 9, 1, 8),
(9, '2018-03-23 19:45:00', b'1', 'Brink 14', '2018-03-23 22:30:00', 'Gameavond', 'Breda', 'Computergamesavond!', 5, 7, 9),
(10, '2018-03-29 20:30:00', b'1', 'Coolsingel 19', '2018-03-29 23:00:00', 'Feestje', 'Rotterdam', 'Kom je ook?', 14, 2, 10),
(11, '2018-03-22 14:30:00', b'1', 'Beatrixlaan 6', '2018-03-22 17:30:00', 'Schildercursus', 'Haarlem', 'Met elkaar creatief bezig zijn', 6, 5, 11),
(12, '2018-04-04 11:30:00', b'1', 'Janskerkhof 1', '2018-04-04 14:00:00', 'Museumbezoek', 'Utrecht', 'Naar het nijnte museum', 7, 1, 12),
(13, '2018-03-05 11:30:00', b'1', 'Boslaan 5', '2018-03-07 23:00:00', 'Festivalletje', 'Amsterdam', 'meerdaags festival', 14, 4, 10),
(14, '2018-02-05 20:30:00', b'1', 'Verledenlaan 1', '2018-02-05 22:00:00', 'Spelletjesavond', 'Utrecht', 'Mysterium tijd', 5, 1, 1);
INSERT INTO `persoon_voorkeuren` (`persoon_id`, `voorkeuren_id`) VALUES ('8', '13'), ('6', '1'), ('5', '12'), ('7', '4'), ('12', '10'), ('3', '9'), ('11', '2'), ('10', '4'), ('2', '6'), ('1', '4'), ('4', '5'), ('9', '4'), ('8', '3'), ('6', '4'), ('5', '8'), ('7', '8'), ('12', '11'), ('3', '14'), ('11', '3'), ('10', '7'), ('2', '9'), ('1', '5'), ('4', '6'), ('9', '13');
INSERT INTO `evenement_aanwezigen` (`evenement_id`, `aanwezigen_id`) VALUES ('11', '8'), ('3', '6'), ('4', '5'), ('9', '7'), ('10', '12'), ('6', '3'), ('2', '11'), ('7', '10'), ('12', '2'), ('1', '1'), ('5', '4'), ('8', '9'), ('1', '2'), ('1', '3'), ('2', '2'), ('2', '4'),('3', '3'), ('3', '8'), ('4', '4'), ('4', '10'), ('5', '5'), ('5', '12'), ('6', '6'), ('6', '1'), ('7', '7'), ('7', '3'), ('8', '8'), ('8', '5'), ('9', '9'), ('9', '11'), ('10', '10'), ('10', '7'), ('11', '9'), ('12', '12'), ('12', '6'), ('13', '10'), ('13', '7'), ('14', '3'), ('14', '9'), ('14', '1');


