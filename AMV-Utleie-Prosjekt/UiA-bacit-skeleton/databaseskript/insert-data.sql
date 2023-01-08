/* Passordene er krypterte. Passordene er i denne rekkefølgen:
// Kunde1
// Kunde2
// Kunde3
// Kunde4
// Kunde5
// Kunde6
*/

insert into Ansatt values (1,'Daniel','Revne','23456789','daniel@uia.no','F22B3031E7F45852C4182E7932FF9C22','1','0','1','1'),
                          (2,'Joar','Belsnes','12345678','joar@uia.no','DF9816E44B2BA4EFCFF65E73DB26EE11','1','1','0','1'),
                          (3,'Daniel','Noah','34567890','noah@uia.no','E7A3BEA5491C885A46F7667EE94CD875','0','0','0','0'),
                          (4,'Sebastian','Sebastian','98765432','sebastian@uia.no','496CAC1080BCA7D8EDA5B289A33ADD47 ','0','0','1','0'),
                          (5,'Svetlana','Rudez','91757463','Svetlana@uia.no','33E771F1E70EB603F139782565B596A2','0','0','1','0'),
                          (6,'Andrei','Ranete','917365847','Andrei@uia.no','EE97430256C739A337E37D2230560B16','0','0','1','0'),
                          (7,'Tobias','Tobiassen','93746583','tobias@uia.no','E44A7681FC6B19B0CD9365A13BF2AB98','0','0','1','0'),
                          (8,'Janis','Janisson','98746501','Janis@uia.no','D50DF9DC38FB517849315FC7F2B49AC8 ','0','0','1','0'),
                          (9,'Gandhi','Gandhison','87463987','ghandi@uia.no','06E5614A90E0458A5647708890C1F391','0','0','1','0'),
                          (10,'Bjarte','Bjartilsen','96405738','Bjarte@uia.no','A0311DF7ED210F34EE35E2AA314EFA66 ','0','0','1','0');



insert into Bilde (Bilde_Blob, Beskrivelse) values (load_file('bilder/Drill.png'), 'Drill'),
                         ( load_file('bilder/Skruemaskin.png'),'Skruautomat'),
                         ( load_file('bilder/Hoppetusse bensin.png'),'Hoppetusse'),
                         ( load_file('bilder/No image.jpg'),'No image'),
                         ( load_file('bilder/Spikerpistol liten luft.PNG'),'Spikerpistol - liten, luft'),
                         ( load_file('bilder/Spikerpistol Milwaukee krampe mellom.png'),'Krampepistol'),
                         ( load_file('bilder/Spikerpistol Milwaukee spiker stor.png'),'Spikerpistol - stor, Batteri'),
                         ( load_file('bilder/Kantklipper Bensin.png'),'Kantklipper'),
                         ( load_file('bilder/Spikerpistol Milwaukee Dykk liten.png'),'Spikerpistol - Dykk liten'),
                         ( load_file('bilder/Personløft Niftylift.png'),'Personløfter');

insert into Utstyr_Kategori values  (1, 'Diverse småutstyr'),
                                    (2, 'Spikerpistoler'),
                                    (3, 'For vedhogst'),
                                    (4, 'Tilhengere'),
                                    (5, 'Større ting'),
                                    (6, 'Ingen kategori');

insert into Utstyr_Type values (1, 1, 1, 'Drill','Brukes til boring av hull og skruing', 1, 0, 50,'1','1',4),
                               (2, 5, 10,'Personløfter', 'Brukes for å løfte folk til en posisjon og deretter returnere til startnivået', 1,100,100, '1','1',1),
                               (3, 1, 3, 'Hoppetusse','Platevibrator for komprimeringsarbeid',1,100, 100, '0','1',4),
                               (4, 2, 4, 'Spikerpistol','Liten, luft',1, 0, 50,'0','1',4),
                               (5, 1, 2, 'Skruautomat','Liten, Batteri',1, 0,50,'0','1',4),
                               (6, 2, 9, 'Spikerpistol','Stor, luft',1, 0, 50,'0','1',4),
                               (7, 2, 7, 'Spikerpistol','Stor, Batteri',1, 0, 50,'0','1',4),
                               (8, 2, 6, 'Krampepistol','Pistol med krampe',1, 0, 100,'0','1',4),
                               (9, 2, 9, 'Spikerpistol','Dykk liten',1,0, 50,'0','1',4),
                               (10,1, 8, 'Kantklipper','Liten, Bensin',1, 0, 100,'0','1',4);


insert into Utstyr_Enhet values (1,2,'1'),
                                (2,1,'1'),
                                (3,3,'1'),
                                (4,4,'1'),
                                (5,8,'1'),
                                (6,6,'1'),
                                (7,10,'1'),
                                (8,9,'1'),
                                (9,7,'1'),
                                (10,5,'1');

insert into Reservasjon values (   1,1,1,'2021-10-14',null,'0',0,'','Kontant',4),
                               (   2,2,2,'2021-02-01','2021-02-03','1',50,'Ingen skade','Kontant',2),
                               (   3,4,3,'2021-03-01','2021-03-04','0',300,'Skadet hoppetusse','Kontant',3),
                               (   4,7,10,'2021-01-03','2021-01-04','0',0,'','Kontant',1),
                               (   5,5,5,'2021-01-01','2021-01-02','0',0,'','Kontant',1),
                               (   6,3,10,'2021-01-01','2021-01-02','0',0,'','Kontant',1),
                               (   7,3,8,'2021-01-01','2021-01-03','1',50,'','Kontant',2),
                               (   8,1,9,'2021-01-01',null,'0',0,'','Kontant',1),
                               (   9,3,4,'2021-11-01','2021-11-03','0',50,'ødela luftkoblingen','Kontant',2),
                               (   10,7,6,'2021-12-01',null,'0',0,'','Kontant',1),
                               (   11,5,5,'2022-04-01',null,'0',0,'','Kontant',1),
                               (   12,9,2,'2021-10-01','2021-10-02','0',0,'','Kontant',1);

