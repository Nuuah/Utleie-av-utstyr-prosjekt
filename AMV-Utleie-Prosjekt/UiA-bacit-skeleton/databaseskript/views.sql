create or replace view UtstyrForRetur_V as
select Fornavn, Etternavn, Dato_Laant, Utstyr_Navn
from Reservasjon
         inner join Utstyr_Enhet on Utstyr_Enhet.Enhet_ID=Reservasjon.Enhet_ID
         inner join Utstyr_Type on Utstyr_Type.Utstyr_Type_ID=Utstyr_Enhet.Utstyr_Type_ID
         inner join Ansatt on Ansatt.Ansatt_ID=Reservasjon.Ansatt_ID
where Reservasjon.Dato_Levert is null;

create or replace view Admin_v as select * from ansatt where admin =1;

use AMV_Velferden;
insert into Bilde (Bilde_Blob, Beskrivelse) values (load_file('bilder/Drill.png'), 'Drill');