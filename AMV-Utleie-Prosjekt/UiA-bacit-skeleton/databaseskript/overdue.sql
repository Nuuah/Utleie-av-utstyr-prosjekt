select Reservasjon_ID, Utstyr_Navn, Fornavn, Etternavn, Telefon
from Reservasjon
join Ansatt on Reservasjon.Ansatt_ID = Ansatt.Ansatt_ID
join Utstyr_Enhet on Reservasjon.Enhet_ID = Utstyr_Enhet.Enhet_ID
join Utstyr_Type on Utstyr_Enhet.Utstyr_Type_ID = Utstyr_Type.Utstyr_Type_ID
where Reservasjon.Dato_Levert is null
  and DATEDIFF(CURRENT_DATE,
      (select Reservasjon.Dato_Laant + interval Reservasjon.Dager_Reservert day)
      ) > 0;